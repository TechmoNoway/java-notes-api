package com.trikynguci.notes.service;

import com.trikynguci.notes.dto.UserDTO;
import com.trikynguci.notes.model.User;

import java.util.List;

public interface UserService {
    void updateUserRole(Long userId, String roleName);

    List<User> getAllUsers();

    UserDTO getUserById(Long id);
}
