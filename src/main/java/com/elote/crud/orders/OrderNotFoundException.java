package com.elote.crud.orders;

public class OrderNotFoundException extends RuntimeException{
    OrderNotFoundException(Long id) {
        super("Order number : " + id + " is not yet placed.");
    }
}
