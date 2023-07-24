package com.example.tteapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.tteapi.model.Quote;
import com.example.tteapi.model.Character;
import com.example.tteapi.repository.CharacterRepository;
import com.example.tteapi.repository.QuoteRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class QuoteController {

	@Autowired
	QuoteRepository quoteRepository;

	@Autowired
	CharacterRepository characterRepository;

	@GetMapping("/quotes")
	public ResponseEntity<List<Quote>> getAllQuotes() {
		try {
			List<Quote> quotes = new ArrayList<Quote>();

			quoteRepository.findAll().forEach(quotes::add);

			return new ResponseEntity<>(quotes, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/quotes/{id}")
	public ResponseEntity<Quote> getQuoteById(@PathVariable("id") long id) {
		Optional<Quote> tutorialData = quoteRepository.findById(id);

		if (tutorialData.isPresent()) {
			return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/quotes")
	public ResponseEntity<Quote> createQuote(@RequestBody Quote quote) {
		try {
			Long characterId = quote.getCharacterID();

			Character character = characterRepository.findById(characterId).orElse(null);
			if (character == null) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}

			Quote _quote = quoteRepository
					.save(new Quote(quote.getQuoteText(), quote.getQuoteEp(), quote.getQuoteSeason(),
							character.getId()));

			return new ResponseEntity<>(_quote, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/quotes/batch-create")
	public ResponseEntity<List<Quote>> createQuotes(@RequestBody List<Quote> quotes) {
		try {
			List<Quote> savedQuotes = new ArrayList<>();
			for (Quote quote : quotes) {
				Long characterId = quote.getCharacterID();

				Character character = characterRepository.findById(characterId).orElse(null);
				if (character == null) {
					return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
				}

				Quote _quote = quoteRepository.save(new Quote(quote.getQuoteText(), quote.getQuoteEp(),
						quote.getQuoteSeason(), character.getId()));
				savedQuotes.add(_quote);
			}
			return new ResponseEntity<>(savedQuotes, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/quotes/{id}")
	public ResponseEntity<HttpStatus> deleteQuote(@PathVariable("id") long id) {
		try {
			quoteRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/quotes")
	public ResponseEntity<HttpStatus> deleteAllQuotes() {
		try {
			quoteRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
