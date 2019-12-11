package fr.asso.placeholder.data.sources.rss.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RSSEnclosure {
    private String url;
    private String type;
    private long length;
}
