package com.pokebackend.application.service;

import com.pokebackend.adapter.out.persistence.UserRepository;
import com.pokebackend.domain.User;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#?\\]])(?=\\S+$).{10,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

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

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }

    private boolean isValidPassword(String password) {
        return pattern.matcher(password).matches();
    }
}
