package com.elote.crud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBaseLoader {
    private static final Logger logger = LoggerFactory.getLogger(DataBaseLoader.class);

    @Bean
    CommandLineRunner initDatabase(ClientRepository repo) {
        return args -> {
            logger.info(repo.save(new Client("elo","elo@gmail.com", "3416987234")) + " was stored");
            logger.info(repo.save(new Client("nana","nana@gmail.com", "341111222")) + " was stored");
        };
    }
}
