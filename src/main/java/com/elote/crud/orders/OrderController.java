package com.elote.crud.orders;

import com.elote.crud.client.ClientNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderRepository orderRepository;
    private OrderModelAssembler orderAssembler;

    public OrderController(OrderRepository repo, OrderModelAssembler assembler) {
        orderRepository = repo;
        orderAssembler = assembler;
    }

    @GetMapping("/{id}")
    public EntityModel<Order> getOneOrder(@PathVariable Long id) {
        Order orderRequested = orderRepository
                .findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return orderAssembler.toModel(orderRequested);
    }

    @GetMapping
    public CollectionModel<EntityModel<Order>> getOrders() {
        List<EntityModel<Order>> ordersCollection = orderRepository
                .findAll()
                .stream()
                .map(order -> orderAssembler.toModel(order))
                .collect(Collectors.toList());

        return CollectionModel.of(
                ordersCollection,
                linkTo(methodOn(OrderController.class).getOrders()).withSelfRel()
        );
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        order.setState(State.WAITING);
        Order storedOrder = orderRepository.save(order);

        return ResponseEntity
                .created(linkTo(methodOn(OrderController.class)
                        .getOneOrder(storedOrder.getId()))
                        .toUri())
                .body(orderAssembler.toModel(storedOrder));
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
        Order requestedOrder = orderRepository
                .findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (requestedOrder.getState() == State.WAITING) {
            requestedOrder.setState(State.CANCELLED);
            return ResponseEntity.ok(orderAssembler
                    .toModel(orderRepository.save(requestedOrder)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("Cannot cancel an order with the : " +
                                requestedOrder.getState() +
                                " status.")
                );

    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> completeOrder(@PathVariable Long id) {
        Order requestedOrder = orderRepository
                .findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (requestedOrder.getState() == State.WAITING) {
            requestedOrder.setState(State.COMPLETED);
            return ResponseEntity.ok(orderAssembler.toModel(orderRepository.save(requestedOrder)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("Cannot complete an order with the : " +
                                requestedOrder.getState() +
                                " status."));
    }
}
