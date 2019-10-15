package com.pnctraining.entity;

import java.util.Date;

public class CommentModel {
    private String userId;
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
}
