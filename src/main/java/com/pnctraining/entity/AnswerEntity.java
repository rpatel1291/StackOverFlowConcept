package com.pnctraining.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "AnswerCollection")
@TypeAlias("AnswerCollection")
public class AnswerEntity{

    @Id
    private ObjectId answerId;
    private ObjectId questionId;
    private ObjectId userId;
    private Date dateAnswered;
    private int answerViews;
    private int answerLikes;
    private int answerDislikes;
    private String answerBody;
    private List<CommentEntity> commentsList;
    private boolean answerDeleted;

    public AnswerEntity() {
    }

    public AnswerEntity(ObjectId questionId, ObjectId userId,
                        String answerBody, List<CommentEntity> commentsList) {
        this.answerId = new ObjectId();
        this.questionId = questionId;
        this.userId = userId;
        this.dateAnswered = new Date();
        this.answerViews = 0;
        this.answerLikes = 0;
        this.answerDislikes = 0;
        this.answerBody = answerBody;
        this.commentsList = commentsList;
        this.answerDeleted = false;
    }

    public ObjectId getAnswerId() {
        return answerId;
    }

    public void setAnswerId(ObjectId answerId) {
        this.answerId = answerId;
    }

    public ObjectId getQuestionId() {
        return questionId;
    }

    public void setQuestionId(ObjectId questionId) {
        this.questionId = questionId;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public Date getDateAnswered() {
        return dateAnswered;
    }

    public void setDateAnswered(Date dateAnswered) {
        this.dateAnswered = dateAnswered;
    }

    public int getAnswerViews() {
        return answerViews;
    }

    public void setAnswerViews(int answerViews) {
        this.answerViews = answerViews;
    }

    public int getAnswerLikes() {
        return answerLikes;
    }

    public void setAnswerLikes(int answerLikes) {
        this.answerLikes = answerLikes;
    }

    public int getAnswerDislikes() {
        return answerDislikes;
    }

    public void setAnswerDislikes(int answerDislikes) {
        this.answerDislikes = answerDislikes;
    }

    public String getAnswerBody() {
        return answerBody;
    }

    public void setAnswerBody(String answerBody) {
        this.answerBody = answerBody;
    }

    public List<CommentEntity> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<CommentEntity> commentsList) {
        this.commentsList = commentsList;
    }

    public boolean isAnswerDeleted() {
        return answerDeleted;
    }

    public void setAnswerDeleted(boolean answerDeleted) {
        this.answerDeleted = answerDeleted;
    }

    @Override
    public String toString() {
        return "AnswerEntity{" +
                "answerId=" + answerId +
                ", questionId=" + questionId +
                ", userId=" + userId +
                ", dateAnswered=" + dateAnswered +
                ", answerViews=" + answerViews +
                ", answerLikes=" + answerLikes +
                ", answerDislikes=" + answerDislikes +
                ", answerBody='" + answerBody + '\'' +
                ", commentsList=" + commentsList +
                ", answerDeleted=" + answerDeleted +
                '}';
    }
}
