package fr.asso.placeholder.data.sources.rss.api;

import fr.asso.placeholder.data.sources.rss.api.models.Source;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SourceRepository extends CrudRepository<Source, UUID> {

}