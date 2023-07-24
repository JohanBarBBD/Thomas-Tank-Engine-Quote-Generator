package com.example.tteapi.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.tteapi.model.Favourite;
import com.example.tteapi.model.Quote;
import com.example.tteapi.model.User;
import com.example.tteapi.repository.FavouriteRepository;
import com.example.tteapi.repository.QuoteRepository;
import com.example.tteapi.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class FavouriteController {

    @Autowired
    FavouriteRepository favouriteRepository;
    QuoteRepository quoteRepository;
    UserRepository userRepository;

    @GetMapping("/favourites")
    public ResponseEntity<List<Favourite>> getAllFavourites() {
        try {
            List<Favourite> favourites = new ArrayList<Favourite>();

            favouriteRepository.findAll().forEach(favourites::add);

            return new ResponseEntity<>(favourites, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/favourites")
    public ResponseEntity<Favourite> createFavourite(@RequestBody Favourite favourite) {
        try {
            Date now = new Date();
            long quoteID = favourite.getQuoteID();
            long userID = favourite.getUserID();

            Quote quote = quoteRepository.findById(quoteID).orElse(null);
            User user = userRepository.findById(userID).orElse(null);

            if (quote == null || user == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            Favourite _favourite = favouriteRepository.save(new Favourite(quote.getId(), user.getId(), now));
            return new ResponseEntity<>(_favourite, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/favourites/{id}")
    public ResponseEntity<HttpStatus> deleteFavourite(@PathVariable("id") long id) {
        try {
            favouriteRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
