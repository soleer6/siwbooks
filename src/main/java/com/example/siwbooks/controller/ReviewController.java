package com.example.siwbooks.controller;

import com.example.siwbooks.model.*;
import com.example.siwbooks.repository.*;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewRepository reviewRepo;
    private final BookRepository bookRepo;
    private final UserRepository userRepo;

    public ReviewController(ReviewRepository reviewRepo, BookRepository bookRepo, UserRepository userRepo) {
        this.reviewRepo = reviewRepo;
        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
    }

    @PostMapping("/reviews")
    public ResponseEntity<?> addReview(@Valid @RequestBody Review review, Authentication auth) {
        User user = userRepo.findByUsername(auth.getName()).orElseThrow();
        Book book = bookRepo.findById(review.getBook().getId()).orElseThrow();

        if (reviewRepo.findByBookAndUser(book, user).isPresent()) {
            return ResponseEntity.badRequest().body("Ya has reseñado este libro");
        }

        review.setUser(user);
        review.setBook(book);
        Review saved = reviewRepo.save(review);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/books/{id}/reviews")
    public ResponseEntity<List<Review>> getReviewsForBook(@PathVariable Long id) {
        Book book = bookRepo.findById(id).orElseThrow();
        return ResponseEntity.ok(reviewRepo.findByBook(book));
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener todas las reseñas (uso exclusivo del admin)
    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewRepo.findAll());
    }

}
