package com.kirai.repository;

import com.kirai.model.RiceMill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiceMillRepository extends MongoRepository<RiceMill,String> {
    List<RiceMill> findAll();
}

