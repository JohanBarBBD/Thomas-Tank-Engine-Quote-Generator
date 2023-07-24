package com.example.tteapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.tteapi.model.Character;
import com.example.tteapi.repository.CharacterRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CharacterController {

	@Autowired
	CharacterRepository characterRepository;

	@GetMapping("/characters")
	public ResponseEntity<List<Character>> getAllCharacters() {
		try {
			List<Character> characters = new ArrayList<Character>();

			characterRepository.findAll().forEach(characters::add);
			
			return new ResponseEntity<>(characters, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/characters/{id}")
	public ResponseEntity<Character> getCharacterById(@PathVariable("id") long id) {
		Optional<Character> characterData = characterRepository.findById(id);

		if (characterData.isPresent()) {
			return new ResponseEntity<>(characterData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/characters")
	public ResponseEntity<Character> createCharacter(@RequestBody Character character) {
		try {
			Character _character = characterRepository
					.save(new Character(character.getName()));
			return new ResponseEntity<>(_character, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/characters/batch-create")
	public ResponseEntity<List<Character>> createCharacters(@RequestBody List<Character> characters) {
		try {
			List<Character> savedCharacters = new ArrayList<>();
			for (Character character : characters) {
				Character _character = characterRepository.save(new Character(character.getName()));
				savedCharacters.add(_character);
			}
			return new ResponseEntity<>(savedCharacters, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/characters/{id}")
	public ResponseEntity<HttpStatus> deleteCharacter(@PathVariable("id") long id) {
		try {
			characterRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
