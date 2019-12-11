package fr.asso.placeholder.data.sources.rss.redis;

import fr.asso.placeholder.data.sources.rss.models.EntryTrack;
import org.springframework.data.repository.CrudRepository;

public interface EntryTrackRepository extends CrudRepository<EntryTrack, String> {

}
