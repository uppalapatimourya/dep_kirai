package com.kirai.repository;

import com.kirai.model.DhalariDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DhalariRepository extends MongoRepository<DhalariDetails,String> {
    List<DhalariDetails> findAll();
}

