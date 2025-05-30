package com.example.siwbooks.repository;

import com.example.siwbooks.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Busca títulos que contengan la cadena (case-insensitive)
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    // Busca autores que contengan la cadena (case-insensitive)
    Page<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);

    // Busca por título Y autor simultáneamente
    Page<Book> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(
        String title, String author, Pageable pageable);
}
