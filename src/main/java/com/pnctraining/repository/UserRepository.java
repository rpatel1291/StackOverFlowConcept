package com.pnctraining.repository;

import com.pnctraining.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    public UserEntity findByEmail(String email);
    public UserEntity findByDisplayName(String displayName);
    public UserEntity findByUserId(String userId);
}
