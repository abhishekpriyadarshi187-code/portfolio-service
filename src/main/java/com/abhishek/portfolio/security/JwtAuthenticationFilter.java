package com.abhishek.portfolio.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);
            System.out.println("JWT Filter URI: " + request.getRequestURI());
            System.out.println("Authorization Header: " + request.getHeader("Authorization"));

            if (jwtUtil.isTokenValid(token)) {

                final String userId = jwtUtil.extractUserId(token);
                final String email = jwtUtil.extractEmail(token);
                final String role = jwtUtil.extractRole(token);
                System.out.println("Extracted userId: " + userId);
                System.out.println("Extracted email: " + email);
                System.out.println("Extracted role: " + role);

                CustomPrincipal principal = new CustomPrincipal(userId, email);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(principal, null, List.of(new SimpleGrantedAuthority(role)));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Authentication set: " +
                        SecurityContextHolder.getContext().getAuthentication());
            }
        }

        filterChain.doFilter(request, response);
    }
}