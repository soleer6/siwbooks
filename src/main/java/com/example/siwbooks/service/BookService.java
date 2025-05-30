package com.example.siwbooks.service;

import com.example.siwbooks.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findById(Long id);
    Book create(Book book);
    Book update(Long id, Book book);
    void delete(Long id);

    Page<Book> findPaginated(Pageable pageable, String title, String author);
}
