package com.pnctraining.service;

import com.pnctraining.entity.QuestionEntity;
import com.pnctraining.entity.QuestionModel;
import com.pnctraining.exception.CPSOException;

import java.util.List;

public interface QuestionService {

    public void createNewQuestion(QuestionModel questionModel, String token) throws CPSOException;
    public QuestionEntity getQuestionDetails(String questionId, String token) throws CPSOException;
    public void updateQuestionDetails(String questionId, QuestionEntity questionEntity, String token) throws CPSOException;
    public List<QuestionModel> getAllQuestions(String token) throws CPSOException;

    public List<QuestionEntity> getQuestionById(String token) throws  CPSOException;
}
