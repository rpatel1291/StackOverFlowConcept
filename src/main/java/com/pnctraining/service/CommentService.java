package com.pnctraining.service;

import com.pnctraining.entity.CommentModel;
import com.pnctraining.exception.CPSOException;

public interface CommentService {
    public void addCommentToQuestion(String token, CommentModel commentModel, String questionId) throws CPSOException;

    public void addCommentToAnswer(String token, CommentModel commentModel, String answerId) throws CPSOException;
}
