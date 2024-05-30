CREATE SCHEMA `pokedb` DEFAULT CHARACTER SET utf8 ;

#DROP TABLE USER;
#DROP TABLE POKEMON;

CREATE TABLE USER (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    UNIQUE KEY unique_email (email)
);

#prefill:
INSERT INTO USER (email,password, firstName, lastName) values ("saureth001@gmail.com","santiagoSanmartin123!","Santiago","Sanmartin");
INSERT INTO USER (email, password, firstName, lastName) VALUES
('john.doe@example.com', 'Password123!', 'John', 'Doe'),
('jane.smith@example.com', 'Password123!', 'Jane', 'Smith'),
('alice.jones@example.com', 'Password123!', 'Alice', 'Jones'),
('bob.brown@example.com', 'Password123!', 'Bob', 'Brown'),
('charlie.davis@example.com', 'Password123!', 'Charlie', 'Davis'),
('diana.evans@example.com', 'Password123!', 'Diana', 'Evans'),
('frank.garcia@example.com', 'Password123!', 'Frank', 'Garcia'),
('grace.harris@example.com', 'Password123!', 'Grace', 'Harris'),
('henry.lee@example.com', 'Password123!', 'Henry', 'Lee'),
('isabella.martin@example.com', 'Password123!', 'Isabella', 'Martin');

CREATE TABLE POKEMON (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    hp INT NOT NULL,
    attack INT NOT NULL,
    defense INT NOT NULL,
    user_id BIGINT NOT NULL,
    isPublic BOOLEAN NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USER(id)
);

#UNCOMMON, NOT PUBLIC, ONE FOR EACH USER
INSERT INTO POKEMON (name, type, hp, attack, defense, user_id, isPublic) VALUES
('Dragonite', 'Dragon/Flying', 91, 134, 95, 1, false),
('Tyranitar', 'Rock/Dark', 100, 134, 110, 2, false),
('Metagross', 'Steel/Psychic', 80, 135, 130, 3, false),
('Garchomp', 'Dragon/Ground', 108, 130, 95, 4, false),
('Lucario', 'Fighting/Steel', 70, 110, 70, 5, false),
('Greninja', 'Water/Dark', 72, 95, 67, 6, false),
('Aegislash', 'Steel/Ghost', 60, 150, 150, 7, false),
('Salamence', 'Dragon/Flying', 95, 135, 80, 8, false),
('Gardevoir', 'Psychic/Fairy', 68, 85, 65, 9, false),
('Gengar', 'Ghost/Poison', 60, 65, 60, 10, false),
('Blaziken', 'Fire/Fighting', 80, 120, 70, 1, false),
('Mimikyu', 'Ghost/Fairy', 55, 90, 80, 2, false),
('Infernape', 'Fire/Fighting', 76, 104, 71, 3, false),
('Toxtricity', 'Electric/Poison', 75, 98, 70, 4, false),
('Excadrill', 'Ground/Steel', 110, 135, 60, 5, false),
('Scizor', 'Bug/Steel', 70, 130, 100, 6, false),
('Magnezone', 'Electric/Steel', 70, 70, 115, 7, false),
('Togekiss', 'Fairy/Flying', 85, 50, 95, 8, false),
('Porygon-Z', 'Normal', 85, 135, 70, 9, false),
('Hydreigon', 'Dark/Dragon', 92, 105, 90, 10, false);

#COMMON PUBLIC POKEMONS, 20 FOR EACH USER
-- User 1
INSERT INTO POKEMON (name, type, hp, attack, defense, user_id, isPublic) VALUES
('Pikachu', 'Electric', 35, 55, 40, 1, true),
('Bulbasaur', 'Grass/Poison', 45, 49, 49, 1, true),
('Charmander', 'Fire', 39, 52, 43, 1, true),
('Squirtle', 'Water', 44, 48, 65, 1, true),
('Jigglypuff', 'Normal/Fairy', 115, 45, 20, 1, true),
('Meowth', 'Normal', 40, 45, 35, 1, true),
('Psyduck', 'Water', 50, 52, 48, 1, true),
('Machop', 'Fighting', 70, 80, 50, 1, true),
('Magnemite', 'Electric/Steel', 25, 35, 70, 1, true),
('Geodude', 'Rock/Ground', 40, 80, 100, 1, true),
('Ponyta', 'Fire', 50, 85, 55, 1, true),
('Slowpoke', 'Water/Psychic', 90, 65, 65, 1, true),
('Gastly', 'Ghost/Poison', 30, 35, 30, 1, true),
('Krabby', 'Water', 30, 105, 90, 1, true),
('Voltorb', 'Electric', 40, 30, 50, 1, true),
('Exeggcute', 'Grass/Psychic', 60, 40, 80, 1, true),
('Cubone', 'Ground', 50, 50, 95, 1, true),
('Koffing', 'Poison', 40, 65, 95, 1, true),
('Rhyhorn', 'Ground/Rock', 80, 85, 95, 1, true),
('Horsea', 'Water', 30, 40, 70, 1, true);

-- User 2
INSERT INTO POKEMON (name, type, hp, attack, defense, user_id, isPublic) VALUES
('Pidgey', 'Normal/Flying', 40, 45, 40, 2, true),
('Rattata', 'Normal', 30, 56, 35, 2, true),
('Caterpie', 'Bug', 45, 30, 35, 2, true),
('Weedle', 'Bug/Poison', 40, 35, 30, 2, true),
('Pidgeotto', 'Normal/Flying', 63, 60, 55, 2, true),
('Raticate', 'Normal', 55, 81, 60, 2, true),
('Metapod', 'Bug', 50, 20, 55, 2, true),
('Kakuna', 'Bug/Poison', 45, 25, 50, 2, true),
('Pidgeot', 'Normal/Flying', 83, 80, 75, 2, true),
('Fearow', 'Normal/Flying', 65, 90, 65, 2, true),
('Ekans', 'Poison', 35, 60, 44, 2, true),
('Sandshrew', 'Ground', 50, 75, 85, 2, true),
('Nidoran♀', 'Poison', 55, 47, 52, 2, true),
('Nidoran♂', 'Poison', 46, 57, 40, 2, true),
('Vulpix', 'Fire', 38, 41, 40, 2, true),
('Jigglypuff', 'Normal/Fairy', 115, 45, 20, 2, true),
('Zubat', 'Poison/Flying', 40, 45, 35, 2, true),
('Oddish', 'Grass/Poison', 45, 50, 55, 2, true),
('Paras', 'Bug/Grass', 35, 70, 55, 2, true),
('Venonat', 'Bug/Poison', 60, 55, 50, 2, true);

-- User 3
INSERT INTO POKEMON (name, type, hp, attack, defense, user_id, isPublic) VALUES
('Pikachu', 'Electric', 35, 55, 40, 3, true),
('Bulbasaur', 'Grass/Poison', 45, 49, 49, 3, true),
('Charmander', 'Fire', 39, 52, 43, 3, true),
('Squirtle', 'Water', 44, 48, 65, 3, true),
('Jigglypuff', 'Normal/Fairy', 115, 45, 20, 3, true),
('Meowth', 'Normal', 40, 45, 35, 3, true),
('Psyduck', 'Water', 50, 52, 48, 3, true),
('Machop', 'Fighting', 70, 80, 50, 3, true),
('Magnemite', 'Electric/Steel', 25, 35, 70, 3, true),
('Geodude', 'Rock/Ground', 40, 80, 100, 3, true),
('Ponyta', 'Fire', 50, 85, 55, 3, true),
('Slowpoke', 'Water/Psychic', 90, 65, 65, 3, true),
('Gastly', 'Ghost/Poison', 30, 35, 30, 3, true),
('Krabby', 'Water', 30, 105, 90, 3, true),
('Voltorb', 'Electric', 40, 30, 50, 3, true),
('Exeggcute', 'Grass/Psychic', 60, 40, 80, 3, true),
('Cubone', 'Ground', 50, 50, 95, 3, true),
('Koffing', 'Poison', 40, 65, 95, 3, true),
('Rhyhorn', 'Ground/Rock', 80, 85, 95, 3, true),
('Horsea', 'Water', 30, 40, 70, 3, true);

-- User 4
INSERT INTO POKEMON (name, type, hp, attack, defense, user_id, isPublic) VALUES
('Pidgey', 'Normal/Flying', 40, 45, 40, 4, true),
('Rattata', 'Normal', 30, 56, 35, 4, true),
('Caterpie', 'Bug', 45, 30, 35, 4, true),
('Weedle', 'Bug/Poison', 40, 35, 30, 4, true),
('Pidgeotto', 'Normal/Flying', 63, 60, 55, 4, true),
('Raticate', 'Normal', 55, 81, 60, 4, true),
('Metapod', 'Bug', 50, 20, 55, 4, true),
('Kakuna', 'Bug/Poison', 45, 25, 50, 4, true),
('Pidgeot', 'Normal/Flying', 83, 80, 75, 4, true),
('Fearow', 'Normal/Flying', 65, 90, 65, 4, true),
('Ekans', 'Poison', 35, 60, 44, 4, true),
('Sandshrew', 'Ground', 50, 75, 85, 4, true),
('Nidoran♀', 'Poison', 55, 47, 52, 4, true),
('Nidoran♂', 'Poison', 46, 57, 40, 4, true),
('Vulpix', 'Fire', 38, 41, 40, 4, true),
('Jigglypuff', 'Normal/Fairy', 115, 45, 20, 4, true),
('Zubat', 'Poison/Flying', 40, 45, 35, 4, true),
('Oddish', 'Grass/Poison', 45, 50, 55, 4, true),
('Paras', 'Bug/Grass', 35, 70, 55, 4, true),
('Venonat', 'Bug/Poison', 60, 55, 50, 4, true);

-- User 5
INSERT INTO POKEMON (name, type, hp, attack, defense, user_id, isPublic) VALUES
('Pikachu', 'Electric', 35, 55, 40, 5, true),
('Bulbasaur', 'Grass/Poison', 45, 49, 49, 5, true),
('Charmander', 'Fire', 39, 52, 43, 5, true),
('Squirtle', 'Water', 44, 48, 65, 5, true),
('Jigglypuff', 'Normal/Fairy', 115, 45, 20, 5, true),
('Meowth', 'Normal', 40, 45, 35, 5, true),
('Psyduck', 'Water', 50, 52, 48, 5, true),
('Machop', 'Fighting', 70, 80, 50, 5, true),
('Magnemite', 'Electric/Steel', 25, 35, 70, 5, true),
('Geodude', 'Rock/Ground', 40, 80, 100, 5, true),
('Ponyta', 'Fire', 50, 85, 55, 5, true),
('Slowpoke', 'Water/Psychic', 90, 65, 65, 5, true),
('Gastly', 'Ghost/Poison', 30, 35, 30, 5, true),
('Krabby', 'Water', 30, 105, 90, 5, true),
('Voltorb', 'Electric', 40, 30, 50, 5, true),
('Exeggcute', 'Grass/Psychic', 60, 40, 80, 5, true),
('Cubone', 'Ground', 50, 50, 95, 5, true),
('Koffing', 'Poison', 40, 65, 95, 5, true),
('Rhyhorn', 'Ground/Rock', 80, 85, 95, 5, true),
('Horsea', 'Water', 30, 40, 70, 5, true);

-- User 6
INSERT INTO POKEMON (name, type, hp, attack, defense, user_id, isPublic) VALUES
('Pidgey', 'Normal/Flying', 40, 45, 40, 6, true),
('Rattata', 'Normal', 30, 56, 35, 6, true),
('Caterpie', 'Bug', 45, 30, 35, 6, true),
('Weedle', 'Bug/Poison', 40, 35, 30, 6, true),
('Pidgeotto', 'Normal/Flying', 63, 60, 55, 6, true),
('Raticate', 'Normal', 55, 81, 60, 6, true),
('Metapod', 'Bug', 50, 20, 55, 6, true),
('Kakuna', 'Bug/Poison', 45, 25, 50, 6, true),
('Pidgeot', 'Normal/Flying', 83, 80, 75, 6, true),
('Fearow', 'Normal/Flying', 65, 90, 65, 6, true),
('Ekans', 'Poison', 35, 60, 44, 6, true),
('Sandshrew', 'Ground', 50, 75, 85, 6, true),
('Nidoran♀', 'Poison', 55, 47, 52, 6, true),
('Nidoran♂', 'Poison', 46, 57, 40, 6, true),
('Vulpix', 'Fire', 38, 41, 40, 6, true),
('Jigglypuff', 'Normal/Fairy', 115, 45, 20, 6, true),
('Zubat', 'Poison/Flying', 40, 45, 35, 6, true),
('Oddish', 'Grass/Poison', 45, 50, 55, 6, true),
('Paras', 'Bug/Grass', 35, 70, 55, 6, true),
('Venonat', 'Bug/Poison', 60, 55, 50, 6, true);

-- User 7
INSERT INTO POKEMON (name, type, hp, attack, defense, user_id, isPublic) VALUES
('Pikachu', 'Electric', 35, 55, 40, 7, true),
('Bulbasaur', 'Grass/Poison', 45, 49, 49, 7, true),
('Charmander', 'Fire', 39, 52, 43, 7, true),
('Squirtle', 'Water', 44, 48, 65, 7, true),
('Jigglypuff', 'Normal/Fairy', 115, 45, 20, 7, true),
('Meowth', 'Normal', 40, 45, 35, 7, true),
('Psyduck', 'Water', 50, 52, 48, 7, true),
('Machop', 'Fighting', 70, 80, 50, 7, true),
('Magnemite', 'Electric/Steel', 25, 35, 70, 7, true),
('Geodude', 'Rock/Ground', 40, 80, 100, 7, true),
('Ponyta', 'Fire', 50, 85, 55, 7, true),
('Slowpoke', 'Water/Psychic', 90, 65, 65, 7, true),
('Gastly', 'Ghost/Poison', 30, 35, 30, 7, true),
('Krabby', 'Water', 30, 105, 90, 7, true),
('Voltorb', 'Electric', 40, 30, 50, 7, true),
('Exeggcute', 'Grass/Psychic', 60, 40, 80, 7, true),
('Cubone', 'Ground', 50, 50, 95, 7, true),
('Koffing', 'Poison', 40, 65, 95, 7, true),
('Rhyhorn', 'Ground/Rock', 80, 85, 95, 7, true),
('Horsea', 'Water', 30, 40, 70, 7, true);

-- User 8
INSERT INTO POKEMON (name, type, hp, attack, defense, user_id, isPublic) VALUES
('Pidgey', 'Normal/Flying', 40, 45, 40, 8, true),
('Rattata', 'Normal', 30, 56, 35, 8, true),
('Caterpie', 'Bug', 45, 30, 35, 8, true),
('Weedle', 'Bug/Poison', 40, 35, 30, 8, true),
('Pidgeotto', 'Normal/Flying', 63, 60, 55, 8, true),
('Raticate', 'Normal', 55, 81, 60, 8, true),
('Metapod', 'Bug', 50, 20, 55, 8, true),
('Kakuna', 'Bug/Poison', 45, 25, 50, 8, true),
('Pidgeot', 'Normal/Flying', 83, 80, 75, 8, true),
('Fearow', 'Normal/Flying', 65, 90, 65, 8, true),
('Ekans', 'Poison', 35, 60, 44, 8, true),
('Sandshrew', 'Ground', 50, 75, 85, 8, true),
('Nidoran♀', 'Poison', 55, 47, 52, 8, true),
('Nidoran♂', 'Poison', 46, 57, 40, 8, true),
('Vulpix', 'Fire', 38, 41, 40, 8, true),
('Jigglypuff', 'Normal/Fairy', 115, 45, 20, 8, true),
('Zubat', 'Poison/Flying', 40, 45, 35, 8, true),
('Oddish', 'Grass/Poison', 45, 50, 55, 8, true),
('Paras', 'Bug/Grass', 35, 70, 55, 8, true),
('Venonat', 'Bug/Poison', 60, 55, 50, 8, true);

-- User 9
INSERT INTO POKEMON (name, type, hp, attack, defense, user_id, isPublic) VALUES
('Pikachu', 'Electric', 35, 55, 40, 9, true),
('Bulbasaur', 'Grass/Poison', 45, 49, 49, 9, true),
('Charmander', 'Fire', 39, 52, 43, 9, true),
('Squirtle', 'Water', 44, 48, 65, 9, true),
('Jigglypuff', 'Normal/Fairy', 115, 45, 20, 9, true),
('Meowth', 'Normal', 40, 45, 35, 9, true),
('Psyduck', 'Water', 50, 52, 48, 9, true),
('Machop', 'Fighting', 70, 80, 50, 9, true),
('Magnemite', 'Electric/Steel', 25, 35, 70, 9, true),
('Geodude', 'Rock/Ground', 40, 80, 100, 9, true),
('Ponyta', 'Fire', 50, 85, 55, 9, true),
('Slowpoke', 'Water/Psychic', 90, 65, 65, 9, true),
('Gastly', 'Ghost/Poison', 30, 35, 30, 9, true),
('Krabby', 'Water', 30, 105, 90, 9, true),
('Voltorb', 'Electric', 40, 30, 50, 9, true),
('Exeggcute', 'Grass/Psychic', 60, 40, 80, 9, true),
('Cubone', 'Ground', 50, 50, 95, 9, true),
('Koffing', 'Poison', 40, 65, 95, 9, true),
('Rhyhorn', 'Ground/Rock', 80, 85, 95, 9, true),
('Horsea', 'Water', 30, 40, 70, 9, true);

-- User 10
INSERT INTO POKEMON (name, type, hp, attack, defense, user_id, isPublic) VALUES
('Pidgey', 'Normal/Flying', 40, 45, 40, 10, true),
('Rattata', 'Normal', 30, 56, 35, 10, true),
('Caterpie', 'Bug', 45, 30, 35, 10, true),
('Weedle', 'Bug/Poison', 40, 35, 30, 10, true),
('Pidgeotto', 'Normal/Flying', 63, 60, 55, 10, true),
('Raticate', 'Normal', 55, 81, 60, 10, true),
('Metapod', 'Bug', 50, 20, 55, 10, true),
('Kakuna', 'Bug/Poison', 45, 25, 50, 10, true),
('Pidgeot', 'Normal/Flying', 83, 80, 75, 10, true),
('Fearow', 'Normal/Flying', 65, 90, 65, 10, true),
('Ekans', 'Poison', 35, 60, 44, 10, true),
('Sandshrew', 'Ground', 50, 75, 85, 10, true),
('Nidoran♀', 'Poison', 55, 47, 52, 10, true),
('Nidoran♂', 'Poison', 46, 57, 40, 10, true),
('Vulpix', 'Fire', 38, 41, 40, 10, true),
('Jigglypuff', 'Normal/Fairy', 115, 45, 20, 10, true),
('Zubat', 'Poison/Flying', 40, 45, 35, 10, true),
('Oddish', 'Grass/Poison', 45, 50, 55, 10, true),
('Paras', 'Bug/Grass', 35, 70, 55, 10, true),
('Venonat', 'Bug/Poison', 60, 55, 50, 10, true);