package com.nm.finance.service;

import com.nm.finance.dto.AuthRequest;
import com.nm.finance.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
}