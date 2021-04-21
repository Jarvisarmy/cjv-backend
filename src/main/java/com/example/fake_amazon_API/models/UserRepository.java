package com.example.fake_amazon_API.models;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserModel, String> {
    // This tells MOngo that there needs to be an implementation for this
    UserModel findByUsername(String username);
}
