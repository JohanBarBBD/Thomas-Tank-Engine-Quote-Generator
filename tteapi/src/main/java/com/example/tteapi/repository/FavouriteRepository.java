package com.example.tteapi.repository;
import com.example.tteapi.model.Favourite;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface FavouriteRepository extends CrudRepository<Favourite, Long> {
    List<Favourite> findByUserID(long userId);
}
