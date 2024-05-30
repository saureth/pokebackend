package com.pokebackend.adapter.in.web;

import com.pokebackend.adapter.in.web.dto.UserRegistrationRequest;
import com.pokebackend.application.service.UserService;
import com.pokebackend.domain.exception.EmailAlreadyRegisteredException;
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
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest request) {
        try {
            userService.registerUser(request.getEmail(), request.getPassword(), request.getFirstName(), request.getLastName());
            return ResponseEntity.ok("User registered successfully.");
        } catch (InvalidEmailException | EmailAlreadyRegisteredException | InvalidPasswordException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
