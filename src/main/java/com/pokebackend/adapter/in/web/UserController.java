package com.pokebackend.adapter.in.web;

import com.pokebackend.application.service.UserService;
import com.pokebackend.domain.exception.EmailAlreadyRegisteredException;
import com.pokebackend.domain.exception.EmailPasswordMismatchException;
import com.pokebackend.domain.exception.InvalidEmailException;
import com.pokebackend.domain.exception.InvalidPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam String email, @RequestParam String password, @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        try {
            userService.registerUser(email, password, firstName, lastName);
            return ResponseEntity.ok("User registered successfully.");
        } catch (InvalidEmailException | EmailAlreadyRegisteredException | InvalidPasswordException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
