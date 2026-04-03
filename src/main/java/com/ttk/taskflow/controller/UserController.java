package com.ttk.taskflow.controller;


import com.ttk.taskflow.dto.UserResponse;
import com.ttk.taskflow.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final static Logger logger = Logger.getLogger(UserController.class.getName());
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Principal principal) {
        return ResponseEntity.ok(userService.getUserProfile(principal.getName()));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/update-password")
    public ResponseEntity<Map<String, String>> updatePassword(
            @RequestBody Map<String, String> passwordRequest,
            Principal principal) {


        logger.info("Changing password for user: " + principal.getName() + "in progress...");
        userService.updatePassword(
                principal.getName(),
                passwordRequest.get("oldPassword"),
                passwordRequest.get("newPassword")
        );

        Map response = Map.of("msg", "Password updated successfully");
        return ResponseEntity.ok(response);
    }
}
