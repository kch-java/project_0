package com.example.project_0.configs;

import com.example.project_0.constants.SecurityConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains(SecurityConstants.ADMIN_ROLE)) {
            httpServletResponse.sendRedirect(SecurityConstants.ADMIN_PATH);
        } else if (roles.contains(SecurityConstants.USER_ROLE)) {
            httpServletResponse.sendRedirect(SecurityConstants.USER_PATH);
        } else {
            httpServletResponse.sendRedirect(SecurityConstants.ROOT_PATH);
        }
    }
}
