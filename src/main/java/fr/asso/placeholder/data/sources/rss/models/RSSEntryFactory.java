package fr.asso.placeholder.data.sources.rss.models;

import com.sun.syndication.feed.module.DCModule;
import com.sun.syndication.feed.synd.*;
import fr.asso.placeholder.data.models.Source;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RSSEntryFactory {

    private RSSContent getRssContent(final SyndContent syndContent) {
        return RSSContent
                .builder()
                .type(syndContent.getType())
                .value(syndContent.getValue())
                .build();
    }

    private List<RSSContent> getRssContents(final List<SyndContent> syndContents) {
        return syndContents.stream()
                .map(this::getRssContent)
                .collect(Collectors.toList());
    }

    private List<RSSCategory> getRssCategories(final List<SyndCategory> syndCategories) {
        return syndCategories.stream()
                .map(syndCategory -> {
                    return RSSCategory.builder().name(syndCategory.getName()).taxonomyUri(syndCategory.getTaxonomyUri()).build();
                })
                .collect(Collectors.toList());
    }

    private List<RSSEnclosure> getRssEnclosures(final List<SyndEnclosure> syndEnclosures) {
        return syndEnclosures.stream()
                .map(syndEnclosure -> {
                    return RSSEnclosure.builder()
                            .url(syndEnclosure.getUrl())
                            .type(syndEnclosure.getType())
                            .length(syndEnclosure.getLength())
                            .build();
                })
                .collect(Collectors.toList());
    }

    private List<DublinCoreModule> getRssModules(final List<DCModule> syndDCModules) {
        return syndDCModules.stream()
                .map(syndModule -> {
                    return DublinCoreModule.builder()
                            .date(syndModule.getDate())
                            .contributors(syndModule.getContributor())
                            .coverage(syndModule.getCoverage())
                            .creator(syndModule.getCreator())
                            .description(syndModule.getDescription())
                            .format(syndModule.getFormat())
                            .identifier(syndModule.getIdentifier())
                            .language(syndModule.getLanguage())
                            .publisher(syndModule.getPublisher())
                            .relation(syndModule.getRelation())
                            .rights(syndModule.getRights())
                            .source(syndModule.getSource())
//                            .subject(syndModule.getSubject())
                            .title(syndModule.getTitle())
                            .type(syndModule.getType())
                            .uri(syndModule.getUri())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public RSSEntry getRssEntry(final SyndEntry syndEntry, Source source) {
        return RSSEntry
                .builder()
                .uri(syndEntry.getUri())
                .link(syndEntry.getLink())
                .updatedDate(syndEntry.getUpdatedDate())
                .publishedDate(syndEntry.getPublishedDate())
                .title(syndEntry.getTitle())
                .description(getRssContent(syndEntry.getDescription()))
                .contents(getRssContents(syndEntry.getContents()))
                .modules(getRssModules(syndEntry.getModules()))
                .enclosures(getRssEnclosures(syndEntry.getEnclosures()))
                .authors(getRssContents(syndEntry.getAuthors()))
                .contributors(getRssContents(syndEntry.getContributors()))
                .categories(getRssCategories(syndEntry.getCategories()))
                .source(source)
                .build();
    }
}
