package fr.asso.placeholder.data.sources.rss.redis;

import fr.asso.placeholder.data.sources.rss.models.FeedTrack;
import org.springframework.data.repository.CrudRepository;

public interface FeedTrackRepository extends CrudRepository<FeedTrack, String> {

}
