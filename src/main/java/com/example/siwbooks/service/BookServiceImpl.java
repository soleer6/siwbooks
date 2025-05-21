package com.example.siwbooks.service;

import com.example.siwbooks.exception.BookNotFoundException;
import com.example.siwbooks.model.Book;
import com.example.siwbooks.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public Book create(Book book) {
        return repository.save(book);
    }

    @Override
    public Book update(Long id, Book datos) {
        Book existente = findById(id);
        existente.setTitle(datos.getTitle());
        existente.setAuthor(datos.getAuthor());
        existente.setIsbn(datos.getIsbn());
        return repository.save(existente);
    }

    @Override
    public void delete(Long id) {
        Book existente = findById(id);
        repository.delete(existente);
    }
}
