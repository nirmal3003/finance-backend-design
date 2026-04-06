package com.nm.finance.security;

import com.nm.finance.entity.Status;
import com.nm.finance.entity.User;
import com.nm.finance.repository.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        log.info("Incoming request: {}", request.getRequestURI());

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);
            log.debug("JWT Token: {}", token);

            try {
                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractRole(token);

                log.info("User extracted from token: {}", username);
                log.info("Role extracted from token: {}", role);

                User user = userRepository.findByEmail(username)
                        .orElse(null);


                if (user == null || user.getStatus() != Status.ACTIVE) {
                    log.warn("Inactive or invalid user trying to access: {}", username);
                    return;
                }
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                List.of(new SimpleGrantedAuthority(role))
                        );

                SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (Exception e) {
                log.error("JWT validation failed: {}", e.getMessage());
            }
        } else {
            log.warn("No Authorization header found");
        }

        filterChain.doFilter(request, response);
    }
}