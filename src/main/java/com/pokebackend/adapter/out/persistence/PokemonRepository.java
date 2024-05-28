package com.pokebackend.adapter.out.persistence;

import com.pokebackend.domain.Pokemon;
import com.pokebackend.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    Page<Pokemon> findByIsPublic(boolean isPublic, Pageable pageable);

    Page<Pokemon> findByUser(User user, Pageable pageable);
}
