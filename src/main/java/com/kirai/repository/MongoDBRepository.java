package com.kirai.repository;

import com.kirai.model.Kirai;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBRepository extends MongoRepository<Kirai,String> {
}
