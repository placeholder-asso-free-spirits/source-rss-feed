package fr.asso.placeholder.data.sources.rss.redis;

import fr.asso.placeholder.data.sources.rss.RSSSourceConfig;
import fr.asso.placeholder.data.sources.rss.repos.EntryTrackRepository;
import fr.asso.placeholder.data.sources.rss.repos.FeedTrackRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.convert.KeyspaceConfiguration;
import org.springframework.data.redis.core.convert.MappingConfiguration;
import org.springframework.data.redis.core.index.IndexConfiguration;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.ArrayList;

@Data
@Configuration
@EnableRedisRepositories
public class RedisConfig {

    private RSSSourceConfig rssSourceConfig;

    @Autowired
    public RedisConfig(RSSSourceConfig rssSourceConfig) {
        this.rssSourceConfig = rssSourceConfig;
    }

    @Bean
    public RedisMappingContext keyValueMappingContext() {
        return new RedisMappingContext(
                new MappingConfiguration(new IndexConfiguration(), new MyKeyspaceConfiguration()));
    }

    public class MyKeyspaceConfiguration extends KeyspaceConfiguration {

        @Override
        protected Iterable<KeyspaceSettings> initialConfiguration() {
            return new ArrayList<>() {{
                new KeyspaceSettings(FeedTrackRepository.class, rssSourceConfig.getFeeds());
                new KeyspaceSettings(EntryTrackRepository.class, rssSourceConfig.getEntries());
            }};
        }
    }
}
