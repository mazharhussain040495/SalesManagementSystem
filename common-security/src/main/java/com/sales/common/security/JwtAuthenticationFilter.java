package com.sales.common.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // ✅ 1. Try internal token header
        String internalHeader = request.getHeader("X-INTERNAL-TOKEN");
        if (StringUtils.hasText(internalHeader)) {
            String username = jwtUtil.extractUsername(internalHeader, true);
            if ("internal-service".equals(username) &&
                    jwtUtil.isTokenValid(internalHeader, "internal-service", true)) {

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        "internal-service", null, List.of()
                );
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
                filterChain.doFilter(request, response);
                return;
            }
        }

        // ✅ 2. Try standard Authorization Bearer token
        String token = parseJwt(request);
        if (token != null) {
            try {
                String username = jwtUtil.extractUsername(token, false);
                if (jwtUtil.isTokenValid(token, username, false)) {
                    var auth = new UsernamePasswordAuthenticationToken(
                            username, null, List.of()
                    );
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception ignored) {
                // Invalid token
            }
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        return (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer "))
                ? headerAuth.substring(7)
                : null;
    }
}
