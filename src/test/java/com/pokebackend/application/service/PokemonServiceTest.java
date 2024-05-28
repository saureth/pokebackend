package com.pokebackend.application.service;

import com.pokebackend.adapter.out.persistence.PokemonRepository;
import com.pokebackend.domain.Pokemon;
import com.pokebackend.domain.User;
import com.pokebackend.domain.exception.InvalidItemAccessException;
import com.pokebackend.domain.exception.ItemNotFoundException;
import com.pokebackend.domain.exception.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PokemonServiceTest {

    @Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private PokemonService pokemonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePokemon() {
        String token = "valid-token";
        String name = "Pikachu";
        String type = "Electric";
        int hp = 100;
        int attack = 50;
        int defense = 40;
        boolean isPublic = true;

        User user = new User();
        when(userService.getUserFromToken(token)).thenReturn(user);
        when(userService.validateToken(token)).thenReturn(true);

        Pokemon pokemon = new Pokemon();
        pokemon.setName(name);
        pokemon.setType(type);
        pokemon.setHp(hp);
        pokemon.setAttack(attack);
        pokemon.setDefense(defense);
        pokemon.setPublic(isPublic);
        pokemon.setUser(user);

        when(pokemonRepository.save(any(Pokemon.class))).thenReturn(pokemon);

        Pokemon createdPokemon = pokemonService.createPokemon(token, name, type, hp, attack, defense, isPublic);

        assertNotNull(createdPokemon);
        assertEquals(name, createdPokemon.getName());
        assertEquals(type, createdPokemon.getType());
        assertEquals(hp, createdPokemon.getHp());
        assertEquals(attack, createdPokemon.getAttack());
        assertEquals(defense, createdPokemon.getDefense());
        assertEquals(isPublic, createdPokemon.isPublic());
        assertEquals(user, createdPokemon.getUser());
    }

    @Test
    void testCreatePokemonInvalidToken() {
        String token = "invalid-token";

        when(userService.validateToken(token)).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> {
            pokemonService.createPokemon(token, "Pikachu", "Electric", 100, 50, 40, true);
        });
    }

    @Test
    void testGetAllPublicPokemons() {
        Pokemon pokemon = new Pokemon();
        pokemon.setPublic(true);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Pokemon> pokemonPage = new PageImpl<>(List.of(pokemon));

        when(pokemonRepository.findByIsPublic(true, pageable)).thenReturn(pokemonPage);

        Page<Pokemon> publicPokemons = pokemonService.getAllPublicPokemons(pageable);

        assertNotNull(publicPokemons);
        assertFalse(publicPokemons.isEmpty());
        assertTrue(publicPokemons.getContent().get(0).isPublic());
    }

    @Test
    void testGetUserPokemons() {
        String token = "valid-token";
        User user = new User();
        when(userService.getUserFromToken(token)).thenReturn(user);
        when(userService.validateToken(token)).thenReturn(true);

        Pokemon pokemon = new Pokemon();
        pokemon.setUser(user);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Pokemon> pokemonPage = new PageImpl<>(List.of(pokemon));

        when(pokemonRepository.findByUser(user, pageable)).thenReturn(pokemonPage);

        Page<Pokemon> userPokemons = pokemonService.getUserPokemons(token, pageable);

        assertNotNull(userPokemons);
        assertFalse(userPokemons.isEmpty());
        assertEquals(user, userPokemons.getContent().get(0).getUser());
    }

    @Test
    void testGetUserPokemonsInvalidToken() {
        String token = "invalid-token";

        when(userService.validateToken(token)).thenReturn(false);

        Pageable pageable = PageRequest.of(0, 10);

        assertThrows(UnauthorizedException.class, () -> {
            pokemonService.getUserPokemons(token, pageable);
        });
    }

    @Test
    void testUpdatePokemon() {
        String token = "valid-token";
        Long pokemonId = 1L;
        String name = "Pikachu";
        String type = "Electric";
        int hp = 100;
        int attack = 50;
        int defense = 40;

        User user = new User();
        Pokemon pokemon = new Pokemon();
        pokemon.setUser(user);

        when(userService.getUserFromToken(token)).thenReturn(user);
        when(userService.validateToken(token)).thenReturn(true);
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(pokemon));
        when(pokemonRepository.save(any(Pokemon.class))).thenReturn(pokemon);

        Pokemon updatedPokemon = pokemonService.updatePokemon(token, pokemonId, name, type, hp, attack, defense);

        assertNotNull(updatedPokemon);
        assertEquals(name, updatedPokemon.getName());
        assertEquals(type, updatedPokemon.getType());
        assertEquals(hp, updatedPokemon.getHp());
        assertEquals(attack, updatedPokemon.getAttack());
        assertEquals(defense, updatedPokemon.getDefense());
    }

    @Test
    void testUpdatePokemonNotFound() {
        String token = "valid-token";
        Long pokemonId = 1L;

        when(userService.getUserFromToken(token)).thenReturn(new User());
        when(userService.validateToken(token)).thenReturn(true);
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> {
            pokemonService.updatePokemon(token, pokemonId, "Pikachu", "Electric", 100, 50, 40);
        });
    }

    @Test
    void testUpdatePokemonInvalidAccess() {
        String token = "valid-token";
        Long pokemonId = 1L;
        User user = new User();
        user.setId(1L);

        User differentUser = new User();
        differentUser.setId(2L);

        Pokemon pokemon = new Pokemon();
        pokemon.setUser(differentUser);

        when(userService.getUserFromToken(token)).thenReturn(user);
        when(userService.validateToken(token)).thenReturn(true);
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(pokemon));

        assertThrows(InvalidItemAccessException.class, () -> {
            pokemonService.updatePokemon(token, pokemonId, "Pikachu", "Electric", 100, 50, 40);
        });
    }

    @Test
    void testUpdatePokemonInvalidToken() {
        String token = "invalid-token";

        when(userService.validateToken(token)).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> {
            pokemonService.updatePokemon(token, 1L, "Pikachu", "Electric", 100, 50, 40);
        });
    }

    @Test
    void testDeletePokemon() {
        String token = "valid-token";
        Long pokemonId = 1L;
        User user = new User();
        Pokemon pokemon = new Pokemon();
        pokemon.setUser(user);

        when(userService.getUserFromToken(token)).thenReturn(user);
        when(userService.validateToken(token)).thenReturn(true);
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(pokemon));

        pokemonService.deletePokemon(token, pokemonId);

        verify(pokemonRepository, times(1)).delete(pokemon);
    }

    @Test
    void testDeletePokemonNotFound() {
        String token = "valid-token";
        Long pokemonId = 1L;

        when(userService.getUserFromToken(token)).thenReturn(new User());
        when(userService.validateToken(token)).thenReturn(true);
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> {
            pokemonService.deletePokemon(token, pokemonId);
        });
    }

    @Test
    void testDeletePokemonInvalidAccess() {
        String token = "valid-token";
        Long pokemonId = 1L;
        User user = new User();
        user.setId(1L);

        User differentUser = new User();
        differentUser.setId(2L);

        Pokemon pokemon = new Pokemon();
        pokemon.setUser(differentUser);

        when(userService.getUserFromToken(token)).thenReturn(user);
        when(userService.validateToken(token)).thenReturn(true);
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(pokemon));

        assertThrows(InvalidItemAccessException.class, () -> {
            pokemonService.deletePokemon(token, pokemonId);
        });
    }

    @Test
    void testDeletePokemonInvalidToken() {
        String token = "invalid-token";

        when(userService.validateToken(token)).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> {
            pokemonService.deletePokemon(token, 1L);
        });
    }
}
