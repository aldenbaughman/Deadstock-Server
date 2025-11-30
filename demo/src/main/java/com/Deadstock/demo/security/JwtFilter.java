package com.Deadstock.demo.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Deadstock.demo.repository.AccountRepository;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AccountRepository userRepo;

    public JwtFilter(JwtService jwtService, AccountRepository userRepo) {
        this.jwtService = jwtService;
        this.userRepo = userRepo;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                String username = jwtService.extractUsername(token);

                var user = userRepo.findByUsername(username);
                if (user.isPresent()) {
                    var authToken = new UsernamePasswordAuthenticationToken(
                            username, null, null
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            } catch (JwtException e) {
                // ignore invalid token
            }
        }

        chain.doFilter(request, response);
    }
}
