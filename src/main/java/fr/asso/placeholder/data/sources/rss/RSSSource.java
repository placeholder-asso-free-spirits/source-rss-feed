package fr.asso.placeholder.data.sources.rss;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.ParsingFeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import fr.asso.placeholder.data.models.*;
import fr.asso.placeholder.data.sources.rss.models.FeedTrack;
import fr.asso.placeholder.data.sources.rss.models.RSSEntry;
import fr.asso.placeholder.data.sources.rss.models.RSSEntryFactory;
import fr.asso.placeholder.data.sources.rss.models.EntryTrack;
import fr.asso.placeholder.data.sources.rss.redis.EntryTrackRepository;
import fr.asso.placeholder.data.sources.rss.redis.FeedTrackRepository;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@SpringBootApplication
@EnableBinding(Processor.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RSSSource {

    public static void main(String[] args) {
        SpringApplication.run(RSSSource.class, args);
    }

    private static Logger LOGGER = LoggerFactory.getLogger(RSSSource.class);

    private final RSSEntryFactory rssEntryFactory;

    private final FeedTrackRepository feedTrackRepository;

    private final MessageChannel output;

    private final EntryTrackRepository entryTrackRepository;

    private final SimpleDateFormat dateFormat;

    @StreamListener(Processor.INPUT)
    public void process(Source source) throws IOException, FeedException {
        LOGGER.info("Handling feed {}",source.getRss().getUrl());
        URL url = new URL(source.getRss().getUrl());
        Optional<FeedTrack> feedTrack = feedTrackRepository.findById(url.toString());

        HttpURLConnection con = feedTrack.isPresent() ? prepareHttpConnection(url, feedTrack.get().getLastVisit())
                : prepareHttpConnection(url);
        SyndFeedInput input = new SyndFeedInput();

        try {
            SyndFeed feed = input.build(new XmlReader(con));

            for (Object entry : feed.getEntries()) {
                SyndEntry syndEntry = (SyndEntryImpl) entry;
                RSSEntry rssEntry = rssEntryFactory.getRssEntry(syndEntry, source);
                LOGGER.info("Handling entry {}", rssEntry.getUri());

                Optional<EntryTrack> entryTrack = entryTrackRepository.findById(rssEntry.getUri());

                if (entryTrack.isPresent()) {
                    Date fetchedLastUpdateDate = getLastUpdateDate(rssEntry);
                    Date lastTrackedUpdateDate = entryTrack.get().getLastUpdateDate();

                    // if entry is newer
                    if (fetchedLastUpdateDate != null && fetchedLastUpdateDate.compareTo(lastTrackedUpdateDate) > 0) {
                        LOGGER.info("Entry has been updated {}", rssEntry.getUri());
                        entryTrack.get().setLastUpdateDate(fetchedLastUpdateDate);
                        entryTrack.get().setLastVisit(new Date());
                        entryTrackRepository.save(entryTrack.get());
                        output.send(MessageBuilder.withPayload(rssEntry).build());
                    }
                } else {
                    EntryTrack newTrack = EntryTrack.builder()
                            .lastUpdateDate(getLastUpdateDate(rssEntry))
                            .lastVisit(new Date())
                            .url(rssEntry.getUri())
                            .build();
                    entryTrackRepository.save(newTrack);
                    output.send(MessageBuilder.withPayload(rssEntry).build());
                }
            }
        } catch (ParsingFeedException parsingFeedException) {
            // The server returns 302 status code with empty response, ROME doesn't handle this correctly
            // and throws a parsingException
            if (feedTrack.isPresent()) {
                LOGGER.info("Nothing new for feed {}", url.toString());
            } else {
                throw parsingFeedException;
            }
        }

        if (feedTrack.isPresent()) {
            feedTrack.get().setLastVisit(new Date());
            feedTrackRepository.save(feedTrack.get());
        } else {
            feedTrackRepository.save(FeedTrack.builder().url(url.toString()).lastVisit(new Date()).build());
        }
    }

    private Date getLastUpdateDate(RSSEntry rssEntry) {
        if (rssEntry.getUpdatedDate() != null) {
            return rssEntry.getUpdatedDate();
        } else {
            return rssEntry.getPublishedDate();
        }
    }

    private HttpURLConnection prepareHttpConnection(URL url, Date lastVisit) throws IOException {
        LOGGER.debug("Last visit happened on {}, applying conditional GET", lastVisit);
        HttpURLConnection con = prepareHttpConnection(url);
        con.setRequestProperty("If-Modified-Since", dateFormat.format(lastVisit));
        return con;
    }

    private HttpURLConnection prepareHttpConnection(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        return con;
    }
}
