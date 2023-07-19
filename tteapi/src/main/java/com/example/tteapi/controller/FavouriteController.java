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
import com.example.tteapi.repository.FavouriteRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class FavouriteController {

    @Autowired
    FavouriteRepository favouriteRepository;

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
            Favourite _favourite = favouriteRepository
                    .save(new Favourite(favourite.getQuote(), favourite.getUser(), now));
            return new ResponseEntity<>(_favourite, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/favourites/{id}")
    public ResponseEntity<Favourite> updateFavourite(@PathVariable("id") long id, @RequestBody Favourite favourite) {
        Optional<Favourite> favouriteData = favouriteRepository.findById(id);

        if (favouriteData.isPresent()) {
            Favourite _favourite = favouriteData.get();
            _favourite.setQuote(favourite.getQuote());
            _favourite.setUser(favourite.getUser());
            return new ResponseEntity<>(favouriteRepository.save(_favourite), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

    @DeleteMapping("/favourites")
    public ResponseEntity<HttpStatus> deleteAllFavourites() {
        try {
            favouriteRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
