package fr.asso.placeholder.data.sources.rss.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RSSCategory {
    private String name;
    private String taxonomyUri;
}
