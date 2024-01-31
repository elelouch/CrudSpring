package com.elote.crud.orders;


import com.elote.crud.client.Client;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

    @Override
    public EntityModel<Order> toModel(Order order) {
        EntityModel<Order> baseModel = EntityModel.of(
                order,
                linkTo(methodOn(OrderController.class)
                        .getOneOrder(order.getId()))
                        .withSelfRel(),
                linkTo(methodOn(OrderController.class)
                        .getOrders())
                        .withRel("orders")
        );

        if (order.getState() == State.WAITING) {
            baseModel.add(
                    linkTo(methodOn(OrderController.class)
                            .cancelOrder(order.getId()))
                            .withRel("cancel"),
                    linkTo(methodOn(OrderController.class)
                            .completeOrder(order.getId()))
                            .withRel("complete")
            );
        }

        return baseModel;
    }
}