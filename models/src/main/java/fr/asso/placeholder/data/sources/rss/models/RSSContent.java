package fr.asso.placeholder.data.sources.rss.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RSSContent {
    private String type;
    private String value;
}
