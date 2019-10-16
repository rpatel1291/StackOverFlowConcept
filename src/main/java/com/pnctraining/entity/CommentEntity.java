package com.pnctraining.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "CommentCollection")
@TypeAlias("CommentCollection")
public class CommentEntity {

    @Id
    private String commentId;
    private String userId;
    private String questionId;
    private String displayName;
    private Date dateCommentAdded;
    private String commentBody;
    private boolean commentDeleted;

    public CommentEntity() {
    }

    public CommentEntity(String commentId, String userId, Date dateCommentAdded, String commentBody, boolean commentDeleted, String displayName, String questionId) {
        this.commentId = commentId;
        this.userId = userId;
        this.dateCommentAdded = dateCommentAdded;
        this.commentBody = commentBody;
        this.commentDeleted = commentDeleted;
        this.displayName = displayName;
        this.questionId = questionId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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


    public boolean isCommentDeleted() {
        return commentDeleted;
    }

    public void setCommentDeleted(boolean commentDeleted) {
        this.commentDeleted = commentDeleted;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "CommentEntity{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", dateCommentAdded=" + dateCommentAdded +
                ", commentBody='" + commentBody + '\'' +
                ", commentDeleted=" + commentDeleted +
                '}';
    }
}
