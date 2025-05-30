package com.example.siwbooks.repository;

import com.example.siwbooks.model.Review;
import com.example.siwbooks.model.Book;
import com.example.siwbooks.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBook(Book book);
    Optional<Review> findByBookAndUser(Book book, User user);
}
