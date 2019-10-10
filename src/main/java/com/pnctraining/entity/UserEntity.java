package com.pnctraining.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "UserCollection")
@TypeAlias("UserCollection")
public class UserEntity {

    @Id
    private String userId;
    private String displayName;
    private String email;
    private String password;
    private List<QuestionEntity> questionList;
    private List<AnswerEntity> answerList;
    private List<CommentEntity> commentList;
    private List<TagEntity> tagList;
    private boolean accountDeleted;
    private boolean accountDisable;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<QuestionEntity> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionEntity> questionList) {
        this.questionList = questionList;
    }

    public List<AnswerEntity> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<AnswerEntity> answerList) {
        this.answerList = answerList;
    }

    public List<CommentEntity> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentEntity> commentList) {
        this.commentList = commentList;
    }

    public List<TagEntity> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagEntity> tagList) {
        this.tagList = tagList;
    }

    public boolean isAccountDeleted() {
        return accountDeleted;
    }

    public void setAccountDeleted(boolean accountDeleted) {
        this.accountDeleted = accountDeleted;
    }

    public boolean isAccountDisable() {
        return accountDisable;
    }

    public void setAccountDisable(boolean accountDisable) {
        this.accountDisable = accountDisable;
    }

    public UserEntity(String displayName, String email, String password,
                      List<QuestionEntity> questionList, List<AnswerEntity> answerList,
                      List<CommentEntity> commentList, List<TagEntity> tagList) {

        this.displayName = displayName;
        this.email = email;
        this.password = password;
        this.questionList = questionList;
        this.answerList = answerList;
        this.commentList = commentList;
        this.tagList = tagList;
        this.accountDeleted = false;
        this.accountDisable = false;
    }


    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", questionList=" + questionList +
                ", answerList=" + answerList +
                ", commentList=" + commentList +
                ", tagList=" + tagList +
                ", accountDeleted=" + accountDeleted +
                ", accountDisable=" + accountDisable +
                '}';
    }
}
