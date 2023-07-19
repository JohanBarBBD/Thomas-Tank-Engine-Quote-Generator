package com.example.tteapi.repository;
import com.example.tteapi.model.Character;

import org.springframework.data.repository.CrudRepository;

public interface CharacterRepository extends CrudRepository<Character, Long> {
}
