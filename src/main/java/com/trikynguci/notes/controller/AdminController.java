package com.trikynguci.notes.controller;

import com.trikynguci.notes.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getusers")
    ResponseEntity<?> doGetAllUsers() {
        Map<String, Object> response = new HashMap<>();

        try {
          response.put("success", true);
          response.put("message", "Users retrieved successfully");
            response.put("data", userService.getAllUsers());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "An error occurred while retrieving users");
            response.put("data", null);
            log.error("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("update-role")
    ResponseEntity<?> doUpdateUserRole(@RequestParam Long userId,@RequestParam String roleName) {
        Map<String, Object> response = new HashMap<>();
        try {
            userService.updateUserRole(userId, roleName);
            response.put("success", true);
            response.put("message", "User role updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "An error occurred while updating user role");
            response.put("data", null);
            log.error("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{id}")
    public ResponseEntity<?> doGetUser(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("success", true);
            response.put("message", "User retrieved successfully");
            response.put("data", userService.getUserById(id));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "An error occurred while retrieving user");
            response.put("data", null);
            log.error("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

}
