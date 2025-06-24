package com.dashboard.be.config;

import java.io.IOException;
import java.util.Collections;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String contentType = request.getContentType();
        String method = request.getMethod();
        String uri = request.getRequestURI();

        System.out.println("🔎 REQUEST:");
        System.out.println("  → Method: " + method);
        System.out.println("  → URI: " + uri);
        System.out.println("  → Content-Type: " + contentType);

        Collections.list(request.getHeaderNames())
                .forEach(header -> {
                    String value = request.getHeader(header);
                    System.out.println("    → Header: " + header + " = " + value);
                });

        filterChain.doFilter(request, response);
    }
}
