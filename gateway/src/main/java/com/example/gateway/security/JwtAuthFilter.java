package com.example.gateway.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final SecretKey key = Keys.hmacShaKeyFor("mojSuperTajniKljucZaJwtMoraImati32Bajta!".getBytes());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        String method = request.getMethod();

        // JAVNE rute: login i registracija (POST /api/users) - bez tokena
        if (path.equals("/api/users/login") || (path.equals("/api/users") && method.equals("POST"))) {
            filterChain.doFilter(request, response);
            return;
        }

        // sve ostalo - mora token
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Nedostaje token");
            return;
        }

        String token = authHeader.substring(7); // skini "Bearer " prefiks

        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Netacan token");
        }
    }
}
