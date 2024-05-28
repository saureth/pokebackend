package com.pokebackend.adapter.in.web;

import com.pokebackend.application.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(
            @RequestParam String email,
            @RequestParam String password) {
        String result = userService.loginUser(email, password);
        if (result.equals("Invalid email address.") ||
                result.equals("Email is not registered.") ||
                result.equals("Invalid password.") ||
                result.equals("Email and password do not match.")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }
}
