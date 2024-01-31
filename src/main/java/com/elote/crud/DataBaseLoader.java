package com.elote.crud;

import com.elote.crud.client.Client;
import com.elote.crud.client.ClientRepository;
import com.elote.crud.orders.Order;
import com.elote.crud.orders.OrderRepository;
import com.elote.crud.orders.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBaseLoader {
    private static final Logger logger = LoggerFactory.getLogger(DataBaseLoader.class);

    @Bean
    CommandLineRunner initDatabase(ClientRepository clientRepository, OrderRepository orderRepository) {
        return args -> {
            logger.info(clientRepository.save(new Client("elo","rojas","elo@gmail.com", "3416987234")) + " was stored");
            logger.info(clientRepository.save(new Client("nana","gonzalez","nana@gmail.com", "341111222")) + " was stored");

            logger.info(orderRepository.save(new Order("Rogel", State.WAITING)) + " was stored");
            logger.info(orderRepository.save(new Order("Matilda", State.CANCELLED)) + " was stored");
    };
}
}
