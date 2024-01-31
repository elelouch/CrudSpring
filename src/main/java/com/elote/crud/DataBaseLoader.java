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
            Client elo = new Client("elias", "rojas","rojas@rojas.com","341222333");

            Order redVelvet = new Order("Red velvet", State.WAITING, elo);
            Order matilda = new Order("Matilda", State.WAITING, elo);

            clientRepository.save(elo);
            orderRepository.save(redVelvet);
            orderRepository.save(matilda);
            logger.info("client " + elo + " added. With the following orders :");
            logger.info(redVelvet.toString());
            logger.info(matilda.toString());
        };
    };
}
