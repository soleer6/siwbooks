package com.example.siwbooks.controller;

import com.example.siwbooks.model.Book;
import com.example.siwbooks.repository.BookRepository;
import com.example.siwbooks.service.BookService;
import jakarta.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository repository;
    private final BookService service;

    public BookController(BookRepository repository, BookService service) {
        this.repository = repository;
        this.service = service;
    }

    // GET /api/books
    /*    @GetMapping               ------> Comentada por el error “Ambiguous mapping” al implementar pageable
    public List<Book> getAll() {
        return repository.findAll();
    } */

    // GET /api/books/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // POST /api/books
    @PostMapping
    public ResponseEntity<Book> create(@Valid @RequestBody Book book) {
        Book saved = repository.save(book);
        return ResponseEntity.ok(saved);
    }

    // PUT /api/books/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Book> update(
            @PathVariable Long id,
            @Valid @RequestBody Book book
    ) {
        return ResponseEntity.ok(service.update(id, book));
    }

    // DELETE /api/books/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<Book>> getAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String author
    )   {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> result = service.findPaginated(pageable, title, author);
        return ResponseEntity.ok(result);
    }

}
