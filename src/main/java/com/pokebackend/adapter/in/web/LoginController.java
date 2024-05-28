package com.pokebackend.adapter.in.web;

import com.pokebackend.application.service.UserService;
import com.pokebackend.domain.exception.EmailPasswordMismatchException;
import com.pokebackend.domain.exception.InvalidEmailException;
import com.pokebackend.domain.exception.InvalidPasswordException;
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
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) {
        try {
            String token = userService.loginUser(email, password);
            return ResponseEntity.ok(token);
        } catch (InvalidEmailException | EmailPasswordMismatchException | InvalidPasswordException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
