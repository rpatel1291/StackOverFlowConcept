package com.pnctraining.entity;

import java.util.Date;
import java.util.List;

public class QuestionModel {
    private String questionTitle;
    private String questionBody;
    private List<TagEntity> tagList;
    private Date dateQuestionAsked;
    private String displayName;

    public QuestionModel() {}

    public QuestionModel(String questionTitle, String questionBody, List<TagEntity> tagList, Date dateQuestionAsked, String displayName) {
        this.questionTitle = questionTitle;
        this.questionBody = questionBody;
        this.tagList = tagList;
        this.dateQuestionAsked = dateQuestionAsked;
        this.displayName = displayName;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionBody() {
        return questionBody;
    }

    public void setQuestionBody(String questionBody) {
        this.questionBody = questionBody;
    }

    public List<TagEntity> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagEntity> tagList) {
        this.tagList = tagList;
    }

    public Date getDateQuestionAsked() {
        return dateQuestionAsked;
    }

    public void setDateQuestionAsked(Date dateQuestionAsked) {
        this.dateQuestionAsked = dateQuestionAsked;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
