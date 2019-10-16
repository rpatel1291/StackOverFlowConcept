package com.pnctraining.repository;

import com.pnctraining.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    public UserEntity findByEmail(String email);
    public UserEntity findByDisplayName(String displayName);
    public UserEntity findByUserId(String userId);

    @Query("{'questionList.questionId' :?0}")
    Optional<UserEntity> findUserByQuestionId(String id);

    @Query("{'answerList.answerId' :?0}")
    Optional<UserEntity> findUserByAnswerId(String id);
}
