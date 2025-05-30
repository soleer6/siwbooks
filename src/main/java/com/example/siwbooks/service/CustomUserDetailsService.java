// src/main/java/com/example/siwbooks/service/CustomUserDetailsService.java
package com.example.siwbooks.service;

import com.example.siwbooks.model.User;
import com.example.siwbooks.repository.UserRepository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {
  
    private final UserRepository users;
    
    public CustomUserDetailsService(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) 
            throws UsernameNotFoundException {
        User u = users.findByUsername(username)
                      .orElseThrow(() -> 
                          new UsernameNotFoundException(username));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole()));

        return new org.springframework.security.core.userdetails.User(
            u.getUsername(),
            u.getPassword(),
            authorities
        );
    }
}
