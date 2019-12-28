package fr.asso.placeholder.data.sources.rss.repos;

import fr.asso.placeholder.data.sources.rss.redis.FeedTrack;
import org.springframework.data.repository.CrudRepository;

public interface FeedTrackRepository extends CrudRepository<FeedTrack, String> {

}
