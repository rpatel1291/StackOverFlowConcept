package com.pnctraining.repository;

import com.pnctraining.entity.QuestionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends MongoRepository<QuestionEntity, String> {
}
