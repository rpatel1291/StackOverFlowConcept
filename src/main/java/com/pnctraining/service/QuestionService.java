package com.pnctraining.service;

import com.pnctraining.entity.QuestionModel;
import com.pnctraining.exception.CPSOException;

public interface QuestionService {

    public void createNewQuestion(QuestionModel questionModel, String token) throws CPSOException;
}
