package com.example.project_0.services;

import com.example.project_0.models.Role;
import com.example.project_0.models.User;
import com.example.project_0.repositories.RoleRepository;
import com.example.project_0.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void add(User user) {
        encodeUserPassword(user);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        userRepository.findById(user.getId()).map(existingUser -> {
            BeanUtils.copyProperties(user, existingUser, "id", "createdBy", "createdAt");
            encodeUserPassword(existingUser);
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with id '%s' not found", id)));
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(findByUsername(username))
                .map(user -> new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles())))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList());
    }

    public void encodeUserPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    @Transactional
    public void createAdmin() {
        if (userRepository.findByUsername("admin") == null) {
            Role roleAdmin = new Role("ADMIN");
            Role roleUser = new Role("USER");
            roleRepository.save(roleAdmin);
            roleRepository.save(roleUser);
            Collection<Role> adminRoles = new ArrayList<>();
            adminRoles.add(roleAdmin);

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setAge((byte) 33);
            admin.setEmail("admin@admin.com");
            admin.setRoles(adminRoles);
            userRepository.save(admin);
        }
    }
}
