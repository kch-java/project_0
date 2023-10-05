package com.example.project_0.configs;

import com.example.project_0.constants.SecurityConstants;
import com.example.project_0.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final SuccessUserHandler successUserHandler;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher(SecurityConstants.ROOT_PATH)).permitAll()
                        .requestMatchers(new AntPathRequestMatcher(SecurityConstants.ADMIN_PATH + "/**"),
                                new AntPathRequestMatcher("/api/users/**")).hasAnyAuthority(SecurityConstants.ADMIN_ROLE)
                        .requestMatchers(new AntPathRequestMatcher(SecurityConstants.USER_PATH + "/**"),
                                new AntPathRequestMatcher("/api/user/**")).hasAnyAuthority(SecurityConstants.USER_ROLE, SecurityConstants.ADMIN_ROLE)
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage(SecurityConstants.ROOT_PATH)
                        .loginProcessingUrl("/login")
                        .successHandler(successUserHandler))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl(SecurityConstants.ROOT_PATH))
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }
}
