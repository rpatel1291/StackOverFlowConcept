package com.pnctraining.entity;

import java.util.Date;

public class CommentModel {
    private String userId;
    private String questionId;
    private String answerId;
    private String displayName;
    private Date dateCommentAdded;
    private String commentBody;


    public CommentModel() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Date getDateCommentAdded() {
        return dateCommentAdded;
    }

    public void setDateCommentAdded(Date dateCommentAdded) {
        this.dateCommentAdded = dateCommentAdded;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }
}
