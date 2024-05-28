package com.pokebackend.adapter.out.persistence;

import com.pokebackend.domain.Pokemon;
import com.pokebackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    List<Pokemon> findByUser(User user);
    List<Pokemon> findByIsPublic(boolean isPublic);
}
