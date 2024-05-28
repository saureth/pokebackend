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
    void findUserByEmail_success() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userRepository.findByEmail(email);

        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
    }

    @Test
    void findUserByEmail_notFound() {
        String email = "test@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<User> foundUser = userRepository.findByEmail(email);

        assertFalse(foundUser.isPresent());
    }
}
