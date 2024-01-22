package com.elote.crud;

public class ClientNotFoundException extends RuntimeException{
    ClientNotFoundException(Long id) {
        super("Couldn't find id : " + id);
    }
}
