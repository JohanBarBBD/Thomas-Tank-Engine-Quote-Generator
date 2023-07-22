package com.example.tteapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.sql.Date;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.tteapi.model.FavoriteQuote;
import com.example.tteapi.repository.FavoriteRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class FavoriteController {

    @Autowired
    FavoriteRepository FavoriteQuoteRepository;

    @GetMapping("/FavoriteQuotes")
    public ResponseEntity<List<FavoriteQuote>> getAllFavoriteQuotes() {
        try {
            List<FavoriteQuote> FavoriteQuotes = new ArrayList<FavoriteQuote>();

            FavoriteQuoteRepository.findAll().forEach(FavoriteQuotes::add);

            return new ResponseEntity<>(FavoriteQuotes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/FavoriteQuotes")
    public ResponseEntity<FavoriteQuote> createFavoriteQuote(@RequestBody FavoriteQuote FavoriteQuote) {
        try {
            Date now =new java.sql.Date(System.currentTimeMillis());
            FavoriteQuote _FavoriteQuote = FavoriteQuoteRepository
                .save(new FavoriteQuote(FavoriteQuote.getQuote(), FavoriteQuote.getUser(), now));
            return new ResponseEntity<>(_FavoriteQuote, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/FavoriteQuotes/{id}")
    public ResponseEntity<FavoriteQuote> updateFavoriteQuote(@PathVariable("id") long id, @RequestBody FavoriteQuote FavoriteQuote) {
        Optional<FavoriteQuote> FavoriteQuoteData = FavoriteQuoteRepository.findById(id);

        if (FavoriteQuoteData.isPresent()) {
            FavoriteQuote _FavoriteQuote = FavoriteQuoteData.get();
            _FavoriteQuote.setQuote(FavoriteQuote.getQuote());
            _FavoriteQuote.setUser(FavoriteQuote.getUser());
            return new ResponseEntity<>(FavoriteQuoteRepository.save(_FavoriteQuote), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/FavoriteQuotes/{id}")
    public ResponseEntity<HttpStatus> deleteFavoriteQuote(@PathVariable("id") long id) {
        try {
            FavoriteQuoteRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/FavoriteQuotes")
    public ResponseEntity<HttpStatus> deleteAllFavoriteQuotes() {
        try {
            FavoriteQuoteRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
