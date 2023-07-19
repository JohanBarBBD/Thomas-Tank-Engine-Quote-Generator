package com.example.tteapi.repository;
import com.example.tteapi.model.Favourite;

import org.springframework.data.repository.CrudRepository;

public interface FavouriteRepository extends CrudRepository<Favourite, Long> {
}
