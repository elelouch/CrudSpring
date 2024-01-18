package com.elote.crud;

public class UserNotFoundException extends RuntimeException{
    UserNotFoundException(Long id) {
        super("Coludn't find id : " + id);
    }
}
