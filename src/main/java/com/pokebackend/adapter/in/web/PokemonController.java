package com.pokebackend.adapter.in.web;

import com.pokebackend.application.service.PokemonService;
import com.pokebackend.application.service.UserService;
import com.pokebackend.domain.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pokemons")
public class PokemonController {
    private final PokemonService pokemonService;
    private final UserService userService;

    public PokemonController(PokemonService pokemonService, UserService userService) {
        this.pokemonService = pokemonService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Pokemon> createPokemon(@RequestHeader("Authorization") String token, @RequestParam String name, @RequestParam String type, @RequestParam int hp, @RequestParam int attack, @RequestParam int defense, @RequestParam boolean isPublic) {
        Pokemon pokemon = pokemonService.createPokemon(token, name, type, hp, attack, defense, isPublic);
        return ResponseEntity.ok(pokemon);
    }

    @GetMapping("/public")
    public ResponseEntity<Page<Pokemon>> getAllPublicPokemons(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Pokemon> pokemons = pokemonService.getAllPublicPokemons(pageable);
        return ResponseEntity.ok(pokemons);
    }

    @GetMapping("/user")
    public ResponseEntity<Page<Pokemon>> getUserPokemons(@RequestHeader("Authorization") String token, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Pokemon> pokemons = pokemonService.getUserPokemons(token, pageable);
        return ResponseEntity.ok(pokemons);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Pokemon> updatePokemon(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestParam String name, @RequestParam String type, @RequestParam int hp, @RequestParam int attack, @RequestParam int defense) {
        Pokemon updatedPokemon = pokemonService.updatePokemon(token, id, name, type, hp, attack, defense);
        return ResponseEntity.ok(updatedPokemon);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePokemon(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        pokemonService.deletePokemon(token, id);
        return ResponseEntity.noContent().build();
    }
}
