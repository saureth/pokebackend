package com.pokebackend.application.service;

import com.pokebackend.adapter.out.persistence.PokemonRepository;
import com.pokebackend.domain.Pokemon;
import com.pokebackend.domain.User;
import com.pokebackend.domain.exception.InvalidItemAccessException;
import com.pokebackend.domain.exception.ItemNotFoundException;
import com.pokebackend.domain.exception.UnauthorizedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PokemonService {
    private final PokemonRepository pokemonRepository;
    private final UserService userService;

    public PokemonService(PokemonRepository pokemonRepository, UserService userService) {
        this.pokemonRepository = pokemonRepository;
        this.userService = userService;
    }

    public Pokemon createPokemon(String token, String name, String type, int hp, int attack, int defense, boolean isPublic) {
        User user = validateTokenAndGetUser(token);
        Pokemon pokemon = new Pokemon();
        pokemon.setName(name);
        pokemon.setType(type);
        pokemon.setHp(hp);
        pokemon.setAttack(attack);
        pokemon.setDefense(defense);
        pokemon.setPublic(isPublic);
        pokemon.setUser(user);
        return pokemonRepository.save(pokemon);
    }

    public Page<Pokemon> getAllPublicPokemons(Pageable pageable) {
        return pokemonRepository.findByIsPublic(true, pageable);
    }

    public Page<Pokemon> getUserPokemons(String token, Pageable pageable) {
        User user = validateTokenAndGetUser(token);
        return pokemonRepository.findByUser(user, pageable);
    }

    public Pokemon updatePokemon(String token, Long pokemonId, String name, String type, int hp, int attack, int defense) {
        User user = validateTokenAndGetUser(token);
        Pokemon pokemon = findPokemonByIdAndValidateUser(pokemonId, user);
        pokemon.setName(name);
        pokemon.setType(type);
        pokemon.setHp(hp);
        pokemon.setAttack(attack);
        pokemon.setDefense(defense);
        return pokemonRepository.save(pokemon);
    }

    public void deletePokemon(String token, Long pokemonId) {
        User user = validateTokenAndGetUser(token);
        Pokemon pokemon = findPokemonByIdAndValidateUser(pokemonId, user);
        pokemonRepository.delete(pokemon);
    }

    private User validateTokenAndGetUser(String token) {
        if (!userService.validateToken(token)) {
            throw new UnauthorizedException("Invalid token");
        }
        return userService.getUserFromToken(token);
    }

    private Pokemon findPokemonByIdAndValidateUser(Long pokemonId, User user) {
        Optional<Pokemon> optionalPokemon = pokemonRepository.findById(pokemonId);
        if (optionalPokemon.isEmpty()) {
            throw new ItemNotFoundException("Pokemon not found");
        }
        Pokemon pokemon = optionalPokemon.get();
        if (!pokemon.getUser().equals(user)) {
            throw new InvalidItemAccessException("You are not allowed to access this Pokemon");
        }
        return pokemon;
    }
}
