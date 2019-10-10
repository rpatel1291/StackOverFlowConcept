package com.pnctraining.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "CommentCollection")
@TypeAlias("CommentCollection")
public class CommentEntity {

    @Id
    private ObjectId commentId;
    private ObjectId userId;
    private Date dateCommentAdded;
    private String commentBody;
    private List<CommentEntity> commentList;
    private boolean commentDeleted;

    public CommentEntity() {
    }

    public CommentEntity(ObjectId commentId, ObjectId userId, Date dateCommentAdded, String commentBody, List<CommentEntity> commentList, boolean commentDeleted) {
        this.commentId = commentId;
        this.userId = userId;
        this.dateCommentAdded = dateCommentAdded;
        this.commentBody = commentBody;
        this.commentList = commentList;
        this.commentDeleted = commentDeleted;
    }

    public ObjectId getCommentId() {
        return commentId;
    }

    public void setCommentId(ObjectId commentId) {
        this.commentId = commentId;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
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

    public List<CommentEntity> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentEntity> commentList) {
        this.commentList = commentList;
    }

    public boolean isCommentDeleted() {
        return commentDeleted;
    }

    public void setCommentDeleted(boolean commentDeleted) {
        this.commentDeleted = commentDeleted;
    }

    @Override
    public String toString() {
        return "CommentEntity{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", dateCommentAdded=" + dateCommentAdded +
                ", commentBody='" + commentBody + '\'' +
                ", commentList=" + commentList +
                ", commentDeleted=" + commentDeleted +
                '}';
    }
}
