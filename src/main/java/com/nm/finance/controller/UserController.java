package com.nm.finance.controller;

import com.nm.finance.entity.Role;
import com.nm.finance.entity.Status;
import com.nm.finance.entity.User;
import com.nm.finance.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping
    public Page<User> getAll(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @Valid @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PatchMapping("/{id}/role")
    public User updateRole(@PathVariable Long id, @RequestParam Role role) {
        return userService.updateRole(id, role);
    }

    @PatchMapping("/{id}/status")
    public User updateStatus(@PathVariable Long id, @RequestParam Status status) {
        return userService.updateStatus(id, status);
    }
}