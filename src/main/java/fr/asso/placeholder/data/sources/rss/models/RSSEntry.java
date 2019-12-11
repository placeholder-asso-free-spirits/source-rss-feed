package fr.asso.placeholder.data.sources.rss.models;

import lombok.*;

import java.util.Date;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RSSEntry {

    private String uri;
    private String link;
    private String title;
    private Date publishedDate;
    private RSSContent description;
    private List<RSSContent> contents;
    private List<RSSContent> authors;
    private List<RSSContent> contributors;
    private String source;
    private List<DublinCoreModule> modules;
    private List<RSSContent> enclosures;
    private List<RSSCategory> categories;
    private Date updatedDate;
}

