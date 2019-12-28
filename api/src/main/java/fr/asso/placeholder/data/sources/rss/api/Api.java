package fr.asso.placeholder.data.sources.rss.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Api {

    public static void main(String[] args) {
        SpringApplication.run(Api.class, args);
    }

}
