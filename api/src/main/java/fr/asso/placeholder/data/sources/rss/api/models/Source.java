package fr.asso.placeholder.data.sources.rss.api.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class Source {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private boolean activated = true;
    private String url;
    private String name;
}
