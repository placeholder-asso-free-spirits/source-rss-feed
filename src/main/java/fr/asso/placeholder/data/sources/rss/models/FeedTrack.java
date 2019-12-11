package fr.asso.placeholder.data.sources.rss.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Date;

@Data
@Builder
@RedisHash
@AllArgsConstructor
public class FeedTrack {
    @Id
    private String url;
    private Date lastVisit;
}
