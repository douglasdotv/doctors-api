package br.com.dv.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var token = retrieveToken(request.getHeader("Authorization"));
        System.out.println("Token: " + token);
        filterChain.doFilter(request, response);
    }

    private String retrieveToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Token is missing");
        }
        return header.substring(7);
    }

}
