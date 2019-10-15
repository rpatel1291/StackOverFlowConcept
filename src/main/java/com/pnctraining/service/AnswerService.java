package com.pnctraining.service;

import com.pnctraining.entity.AnswerModel;
import com.pnctraining.exception.CPSOException;

public interface AnswerService {
    public void addAnswer(String token, String questionId,AnswerModel answerModel) throws CPSOException;
}
