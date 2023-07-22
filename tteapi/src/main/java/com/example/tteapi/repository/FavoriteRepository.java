package com.example.tteapi.repository;
import com.example.tteapi.model.FavoriteQuote;

import org.springframework.data.repository.CrudRepository;

public interface FavoriteRepository extends CrudRepository<FavoriteQuote, Long> {
}
