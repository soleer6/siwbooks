package com.example.siwbooks.config;

import com.example.siwbooks.model.Book;
import com.example.siwbooks.model.Review;
import com.example.siwbooks.model.User;
import com.example.siwbooks.repository.BookRepository;
import com.example.siwbooks.repository.ReviewRepository;
import com.example.siwbooks.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UserRepository userRepo,
                               BookRepository bookRepo,
                               ReviewRepository reviewRepo,
                               PasswordEncoder encoder) {
        return args -> {

            reviewRepo.deleteAll();
            userRepo.deleteAll();
            bookRepo.deleteAll();
            

           
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(encoder.encode("admin"));
            admin.setRole("ADMIN");
            userRepo.save(admin);

            for (int i = 1; i <= 5; i++) {
                User u = new User();
                u.setUsername("usuario" + i);
                u.setEmail("usuario" + i + "@ejemplo.com");
                u.setPassword(encoder.encode("password"));
                u.setRole("USER");
                userRepo.save(u);
            }

            
            for (int i = 1; i <= 25; i++) {
                Book b = new Book(
                    "Libro " + i,
                    "Autor " + i,
                    String.format("ISBN-%010d", i)
                );
                bookRepo.save(b);
            }
            User reviewer = userRepo.findByUsername("usuario1").orElseThrow();

            for (int i = 1; i <= 5; i++) {
                Review r = new Review();
                r.setTitle("Reseña " + i);
                r.setText("Comentario de prueba para el libro " + i);
                r.setRating((i % 5) + 1);
                r.setBook(bookRepo.findAll().get(i - 1));
                r.setUser(reviewer);
                reviewRepo.save(r);
            }

        };
    }
}
