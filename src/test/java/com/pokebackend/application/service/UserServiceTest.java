package com.pokebackend.application.service;

import com.pokebackend.adapter.out.persistence.UserRepository;
import com.pokebackend.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_success() {
        String email = "test@example.com";
        String password = "ValidPassword1!";
        String firstName = "John";
        String lastName = "Doe";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        String result = userService.registerUser(email, password, firstName, lastName);
        assertEquals("User registered successfully.", result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_successWithNullNames() {
        String email = "test@example.com";
        String password = "ValidPassword1!";
        String firstName = null;
        String lastName = null;
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        String result = userService.registerUser(email, password, firstName, lastName);
        assertEquals("User registered successfully.", result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_invalidEmail() {
        String email = "invalidemail";
        String password = "ValidPassword1!";
        String firstName = "John";
        String lastName = "Doe";
        String result = userService.registerUser(email, password, firstName, lastName);
        assertEquals("Invalid email address.", result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUser_emailAlreadyRegistered() {
        String email = "test@example.com";
        String password = "ValidPassword1!";
        String firstName = "John";
        String lastName = "Doe";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));
        String result = userService.registerUser(email, password, firstName, lastName);
        assertEquals("Email is already registered.", result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUser_invalidPassword() {
        String email = "test@example.com";
        String password = "invalid";
        String firstName = "John";
        String lastName = "Doe";
        String result = userService.registerUser(email, password, firstName, lastName);
        assertEquals("Password must contain at least 10 characters, one lowercase letter, one uppercase letter and one of the following characters: !, @, #, ? or ].", result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void loginUser_success() {
        String email = "test@example.com";
        String password = "ValidPassword1!";
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        String token = userService.loginUser(email, password);
        assertNotNull(token);
        assertFalse(token.equals("Invalid email address."));
        assertFalse(token.equals("Email is not registered."));
        assertFalse(token.equals("Invalid password."));
        assertFalse(token.equals("Email and password do not match."));
    }

    @Test
    void loginUser_invalidEmail() {
        String email = "invalidemail";
        String password = "ValidPassword1!";
        String result = userService.loginUser(email, password);
        assertEquals("Invalid email address.", result);
    }

    @Test
    void loginUser_emailNotRegistered() {
        String email = "test@example.com";
        String password = "ValidPassword1!";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        String result = userService.loginUser(email, password);
        assertEquals("Email is not registered.", result);
    }

    @Test
    void loginUser_invalidPassword() {
        String email = "test@example.com";
        String password = "invalid";
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        String result = userService.loginUser(email, password);
        assertEquals("Invalid password.", result);
    }

    @Test
    void loginUser_passwordMismatch() {
        String email = "test@example.com";
        String password = "ValidPassword1!";
        User user = new User();
        user.setEmail(email);
        user.setPassword("DifferentPassword1!");
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        String result = userService.loginUser(email, password);
        assertEquals("Email and password do not match.", result);
    }

}
