package com.nm.finance.service.impl;

import com.nm.finance.entity.Role;
import com.nm.finance.entity.Status;
import com.nm.finance.entity.User;
import com.nm.finance.exception.BadRequestException;
import com.nm.finance.exception.ResourceNotFoundException;
import com.nm.finance.repository.UserRepository;
import com.nm.finance.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        log.info("Creating user with email: {}", user.getEmail());
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.info("Creating user with email: {}", user.getEmail());
            throw new BadRequestException("Email already exists");
        }
        validateUser(user);

        return userRepository.save(user);
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        log.info("Fetching users with pagination: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return userRepository.findAll(pageable);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        log.info("Updating user with id: {}", id);
        User existing = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.findByEmail(updatedUser.getEmail()).filter(user -> !user.getId().equals(id)).ifPresent(user -> {
            throw new BadRequestException("Email already exists");
        });
        validateUser(updatedUser);

        existing.setName(updatedUser.getName());
        existing.setEmail(updatedUser.getEmail());
        existing.setRole(updatedUser.getRole());
        existing.setStatus(updatedUser.getStatus());

        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        log.warn("Deleting user with id: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
    }

    @Override
    public User updateRole(Long id, Role role) {

        log.info("Updating role for user id: {} to {}", id, role);

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (role == null) {
            throw new BadRequestException("Role cannot be null");
        }

        user.setRole(role);

        return userRepository.save(user);
    }

    @Override
    public User updateStatus(Long id, Status status) {

        log.info("Updating status for user id: {} to {}", id, status);

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (status == null) {
            throw new BadRequestException("Status cannot be null");
        }

        user.setStatus(status);

        return userRepository.save(user);
    }

    private void validateUser(User user) {

        if (user.getName() == null || user.getName().isBlank()) {
            throw new BadRequestException("Name is required");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new BadRequestException("Email is required");
        }

        if (!user.getEmail().contains("@")) {
            throw new BadRequestException("Invalid email format");
        }

        if (user.getRole() == null) {
            throw new BadRequestException("Role is required");
        }

        if (user.getStatus() == null) {
            throw new BadRequestException("Status is required");
        }
    }
}