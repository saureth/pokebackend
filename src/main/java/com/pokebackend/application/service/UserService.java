package com.pokebackend.application.service;

import com.pokebackend.adapter.out.persistence.UserRepository;
import com.pokebackend.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#?\\]])(?=\\S+$).{10,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
    private final Map<String, LocalDateTime> validTokens = new HashMap<>();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registerUser(String email, String password, String firstName, String lastName) {
        if (!isValidEmail(email)) {
            return "Invalid email address.";
        }
        if (userRepository.findByEmail(email).isPresent()) {
            return "Email is already registered.";
        }
        if (!isValidPassword(password)) {
            return "Password must contain at least 10 characters, one lowercase letter, one uppercase letter and one of the following characters: !, @, #, ? or ].";
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password); // En un entorno real, deberías hash la contraseña antes de guardarla.
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userRepository.save(user);
        return "User registered successfully.";
    }

    public String loginUser(String email, String password) {
        if (!isValidEmail(email)) {
            return "Invalid email address.";
        }
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return "Email is not registered.";
        }
        User user = userOptional.get();
        if (!isValidPassword(password)) {
            return "Invalid password.";
        }
        if (!user.getPassword().equals(password)) {
            return "Email and password do not match.";
        }
        String token = generateToken();
        validTokens.put(token, LocalDateTime.now().plusMinutes(20));
        return token;
    }

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }

    private boolean isValidPassword(String password) {
        return pattern.matcher(password).matches();
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public boolean validateToken(String token) {
        LocalDateTime expiryTime = validTokens.get(token);
        if (expiryTime == null || LocalDateTime.now().isAfter(expiryTime)) {
            validTokens.remove(token);
            return false;
        }
        return true;
    }
}
