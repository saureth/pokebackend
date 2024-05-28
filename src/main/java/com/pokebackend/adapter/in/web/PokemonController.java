package com.pokebackend.adapter.in.web;

import com.pokebackend.application.service.PokemonService;
import com.pokebackend.domain.Pokemon;
import com.pokebackend.domain.exception.InvalidItemAccessException;
import com.pokebackend.domain.exception.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pokemons")
public class PokemonController {
    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @PostMapping("/create")
    public ResponseEntity<Pokemon> createPokemon(@RequestHeader("Authorization") String token,
                                                 @RequestParam String name,
                                                 @RequestParam String type,
                                                 @RequestParam int hp,
                                                 @RequestParam int attack,
                                                 @RequestParam int defense,
                                                 @RequestParam boolean isPublic) {
        Pokemon pokemon = pokemonService.createPokemon(token, name, type, hp, attack, defense, isPublic);
        return ResponseEntity.status(HttpStatus.CREATED).body(pokemon);
    }

    @GetMapping("/public")
    public ResponseEntity<List<Pokemon>> getAllPublicPokemons() {
        List<Pokemon> pokemons = pokemonService.getAllPublicPokemons();
        return ResponseEntity.ok(pokemons);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Pokemon>> getUserPokemons(@RequestHeader("Authorization") String token) {
        List<Pokemon> pokemons = pokemonService.getUserPokemons(token);
        return ResponseEntity.ok(pokemons);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Pokemon> updatePokemon(@RequestHeader("Authorization") String token,
                                                 @PathVariable Long id,
                                                 @RequestParam String name,
                                                 @RequestParam String type,
                                                 @RequestParam int hp,
                                                 @RequestParam int attack,
                                                 @RequestParam int defense) {
        try {
            Pokemon updatedPokemon = pokemonService.updatePokemon(token, id, name, type, hp, attack, defense);
            return ResponseEntity.ok(updatedPokemon);
        } catch (ItemNotFoundException | InvalidItemAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePokemon(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        try {
            pokemonService.deletePokemon(token, id);
            return ResponseEntity.noContent().build();
        } catch (ItemNotFoundException | InvalidItemAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
