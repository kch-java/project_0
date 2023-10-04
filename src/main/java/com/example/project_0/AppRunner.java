package com.example.project_0;

import com.example.project_0.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AppRunner implements CommandLineRunner {

    private final UserServiceImpl userService;

    @Override
    public void run(String... args) {
        userService.createAdmin();
    }
}
