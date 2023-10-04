package com.example.project_0.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.example.project_0.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    void add(User user);

    void update(User user);

    void deleteById(Long id);

    User getUserById(Long id);

    List<User> listUsers();

    User findByUsername(String username);

    UserDetails loadUserByUsername(String username);
}
