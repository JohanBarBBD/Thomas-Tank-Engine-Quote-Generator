package com.example.tteapi.repository;
import com.example.tteapi.model.Quote;

import org.springframework.data.repository.CrudRepository;

public interface QuoteRepository extends CrudRepository<Quote, Long> {
}
