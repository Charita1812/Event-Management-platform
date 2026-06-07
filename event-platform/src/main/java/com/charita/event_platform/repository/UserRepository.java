package com.charita.event_platform.repository;

import com.charita.event_platform.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {

    User findByEmail(String email);

    }


