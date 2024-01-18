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
            User elo = new User();
            elo.setEmail("saracatunga@opa.com");
            elo.setUsername("rojas");
            elo.setPhone("341612345");

            User nana = new User();
            nana.setEmail("nanamiamol@gmail.com");
            nana.setUsername("nany");
            nana.setPhone("3410987455");

            logger.info(repo.save(elo) + " was stored");
            logger.info(repo.save(nana) + " was stored");
        };
    }
}
