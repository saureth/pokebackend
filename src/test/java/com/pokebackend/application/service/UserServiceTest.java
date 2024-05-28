package com.pokebackend.application.service;

import com.pokebackend.adapter.out.persistence.UserRepository;
import com.pokebackend.domain.User;
import com.pokebackend.domain.exception.EmailAlreadyRegisteredException;
import com.pokebackend.domain.exception.EmailPasswordMismatchException;
import com.pokebackend.domain.exception.InvalidEmailException;
import com.pokebackend.domain.exception.InvalidPasswordException;
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

        assertDoesNotThrow(() -> userService.registerUser(email, password, firstName, lastName));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_invalidEmail() {
        String email = "invalidemail";
        String password = "ValidPassword1!";
        String firstName = "John";
        String lastName = "Doe";

        InvalidEmailException exception = assertThrows(InvalidEmailException.class, () -> {
            userService.registerUser(email, password, firstName, lastName);
        });

        assertEquals("Invalid email address.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUser_emailAlreadyRegistered() {
        String email = "test@example.com";
        String password = "ValidPassword1!";
        String firstName = "John";
        String lastName = "Doe";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        EmailAlreadyRegisteredException exception = assertThrows(EmailAlreadyRegisteredException.class, () -> {
            userService.registerUser(email, password, firstName, lastName);
        });

        assertEquals("Email is already registered.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUser_invalidPassword() {
        String email = "test@example.com";
        String password = "invalid";
        String firstName = "John";
        String lastName = "Doe";

        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class, () -> {
            userService.registerUser(email, password, firstName, lastName);
        });

        assertEquals("Password must contain at least 10 characters, one lowercase letter, one uppercase letter and one of the following characters: !, @, #, ? or ].", exception.getMessage());
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

        String token = assertDoesNotThrow(() -> userService.loginUser(email, password));

        assertNotNull(token);
    }

    @Test
    void loginUser_invalidEmail() {
        String email = "invalidemail";
        String password = "ValidPassword1!";

        InvalidEmailException exception = assertThrows(InvalidEmailException.class, () -> {
            userService.loginUser(email, password);
        });

        assertEquals("Invalid email address.", exception.getMessage());
    }

    @Test
    void loginUser_emailNotRegistered() {
        String email = "test@example.com";
        String password = "ValidPassword1!";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        EmailPasswordMismatchException exception = assertThrows(EmailPasswordMismatchException.class, () -> {
            userService.loginUser(email, password);
        });

        assertEquals("Email and password do not match.", exception.getMessage());
    }

    @Test
    void loginUser_invalidPassword() {
        String email = "test@example.com";
        String password = "invalid";
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class, () -> {
            userService.loginUser(email, password);
        });

        assertEquals("Invalid password.", exception.getMessage());
    }

    @Test
    void loginUser_passwordMismatch() {
        String email = "test@example.com";
        String password = "ValidPassword1!";
        User user = new User();
        user.setEmail(email);
        user.setPassword("DifferentPassword1!");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        EmailPasswordMismatchException exception = assertThrows(EmailPasswordMismatchException.class, () -> {
            userService.loginUser(email, password);
        });

        assertEquals("Email and password do not match.", exception.getMessage());
    }
}
