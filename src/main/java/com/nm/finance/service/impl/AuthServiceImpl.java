package com.nm.finance.service.impl;

import com.nm.finance.dto.AuthRequest;
import com.nm.finance.dto.AuthResponse;
import com.nm.finance.entity.Status;
import com.nm.finance.entity.User;
import com.nm.finance.exception.BadRequestException;
import com.nm.finance.repository.UserRepository;
import com.nm.finance.security.JwtUtil;
import com.nm.finance.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse login(AuthRequest request) {
        log.info("Login attempt for email: {}", request.getEmail());
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        if (user.getPassword() == null || !user.getPassword().equals(request.getPassword())) {
            log.error("Invalid login attempt for email: {}", request.getEmail());
            throw new BadRequestException("Invalid email or password");
        }
        if (user.getStatus() != Status.ACTIVE) {
            log.warn("Inactive user login attempt: {}", request.getEmail());
            throw new BadRequestException("User is inactive");
        }
        if (user.getRole() == null) {
            throw new BadRequestException("User role not assigned");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(token);
    }
}