package com.example.siwbooks.config;

import com.example.siwbooks.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)  // Habilita @PreAuthorize
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(auth -> auth
        // Archivos estáticos públicos
        .requestMatchers(
          "/", "/login.html", "/register.html", "/books.html", "/admin.html",
          "/bootstrap.min.css", "/js/**", "/login.js", "/register.js", "/books.js", "/admin.js",
          "/error", "/error/**"
        ).permitAll()

        // Endpoints de autenticación
        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()

        // CRUD protegido para ADMIN
        .requestMatchers(HttpMethod.POST, "/api/books/**").hasRole("ADMIN")
        .requestMatchers(HttpMethod.PUT, "/api/books/**").hasRole("ADMIN")
        .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN")
        .requestMatchers(HttpMethod.DELETE, "/api/reviews/**").hasRole("ADMIN")

        // Lectura de libros y escritura de reviews permitida a todos los autenticados
        .requestMatchers(HttpMethod.GET, "/api/books/**").authenticated()
        .requestMatchers(HttpMethod.GET, "/api/reviews").hasAnyRole("ADMIN", "USER")
        .requestMatchers(HttpMethod.POST, "/api/reviews").hasRole("USER")
        


        // Todo lo demás, denegado
        .anyRequest().denyAll()
      )
      .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
    return cfg.getAuthenticationManager();
  }
}
