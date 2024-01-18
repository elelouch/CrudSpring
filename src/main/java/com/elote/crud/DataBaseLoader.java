package com.elote.crud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

public class DataBaseLoader {
    private static final Logger logger = LoggerFactory.getLogger(DataBaseLoader.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository repo) {
        return args -> {
            logger.info(repo.save(new User("elo","elo@gmail.com", "3416987234")) + " was stored");
            logger.info(repo.save(new User("nana","nana@gmail.com", "341111222")) + " was stored");
        };
    }
}
