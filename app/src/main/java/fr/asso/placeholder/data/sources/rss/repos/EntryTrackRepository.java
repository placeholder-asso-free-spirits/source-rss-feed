package fr.asso.placeholder.data.sources.rss.repos;

import fr.asso.placeholder.data.sources.rss.redis.EntryTrack;
import org.springframework.data.repository.CrudRepository;

public interface EntryTrackRepository extends CrudRepository<EntryTrack, String> {

}
