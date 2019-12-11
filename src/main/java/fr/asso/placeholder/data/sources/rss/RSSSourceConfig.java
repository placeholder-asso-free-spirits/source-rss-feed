package fr.asso.placeholder.data.sources.rss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

@Data
@Configuration
@ConfigurationProperties(prefix = "tracking.dbs")
public class RSSSourceConfig {

    private String feeds = "asso.placeholder.tracks.feeds";
    private String entries = "asso.placeholder.tracks.entries";

    @Bean
    public SimpleDateFormat simpleDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat( "EEE, dd MMM yyyy HH:mm:ss z", new Locale("en"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat;
    }
}
