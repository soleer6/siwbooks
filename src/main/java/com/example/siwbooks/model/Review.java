package com.example.siwbooks.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Min(1) @Max(5)
    private int rating;

    @NotBlank
    private String text;

    @ManyToOne(optional = false)
    private Book book;

    @ManyToOne(optional = false)
    private User user;

    // Getters y setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public int getRating() { return rating; }

    public void setRating(int rating) { this.rating = rating; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public Book getBook() { return book; }

    public void setBook(Book book) { this.book = book; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}
