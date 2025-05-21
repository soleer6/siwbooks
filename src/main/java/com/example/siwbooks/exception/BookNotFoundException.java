package com.example.siwbooks.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("Libro con id " + id + "no encontrado");
    }
}
