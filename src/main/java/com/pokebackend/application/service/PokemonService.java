package com.pokebackend.application.service;

import com.pokebackend.adapter.out.persistence.PokemonRepository;
import com.pokebackend.domain.Pokemon;
import com.pokebackend.domain.User;
import com.pokebackend.domain.exception.InvalidItemAccessException;
import com.pokebackend.domain.exception.ItemNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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
        User user = userService.getUserFromToken(token);
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

    public List<Pokemon> getAllPublicPokemons() {
        return pokemonRepository.findByIsPublic(true);
    }

    public List<Pokemon> getUserPokemons(String token) {
        User user = userService.getUserFromToken(token);
        return pokemonRepository.findByUser(user);
    }

    public Pokemon updatePokemon(String token, Long pokemonId, String name, String type, int hp, int attack, int defense) {
        User user = userService.getUserFromToken(token);
        Optional<Pokemon> optionalPokemon = pokemonRepository.findById(pokemonId);
        if (optionalPokemon.isEmpty()) {
            throw new ItemNotFoundException("Pokemon not found");
        }
        Pokemon pokemon = optionalPokemon.get();
        if (!pokemon.getUser().equals(user)) {
            throw new InvalidItemAccessException("You are not allowed to update this Pokemon");
        }
        pokemon.setName(name);
        pokemon.setType(type);
        pokemon.setHp(hp);
        pokemon.setAttack(attack);
        pokemon.setDefense(defense);
        return pokemonRepository.save(pokemon);
    }

    public void deletePokemon(String token, Long pokemonId) {
        User user = userService.getUserFromToken(token);
        Optional<Pokemon> optionalPokemon = pokemonRepository.findById(pokemonId);
        if (optionalPokemon.isEmpty()) {
            throw new ItemNotFoundException("Pokemon not found");
        }
        Pokemon pokemon = optionalPokemon.get();
        if (!pokemon.getUser().equals(user)) {
            throw new InvalidItemAccessException("You are not allowed to delete this Pokemon");
        }
        pokemonRepository.delete(pokemon);
    }
}
