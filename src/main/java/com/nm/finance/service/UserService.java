package com.nm.finance.service;

import com.nm.finance.entity.Role;
import com.nm.finance.entity.Status;
import com.nm.finance.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User createUser(User user);

    Page<User> getAllUsers(Pageable pageable);

    User updateUser(Long id, User updatedUser);

    void deleteUser(Long id);

    User updateRole(Long id, Role role);

    User updateStatus(Long id, Status status);
}
