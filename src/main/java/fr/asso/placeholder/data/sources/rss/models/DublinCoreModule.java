package fr.asso.placeholder.data.sources.rss.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * https://www.dublincore.org/specifications/dublin-core/dces/
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DublinCoreModule {
    private String creator;
    private String title;
    private String subject;
    private String description;
    private String publisher;
    private String contributors;
    private Date date;
    private String type;
    private String format;
    private String identifier;
    private String source;
    private String language;
    private String relation;
    private String coverage;
    private String rights;
    private String uri;
}
