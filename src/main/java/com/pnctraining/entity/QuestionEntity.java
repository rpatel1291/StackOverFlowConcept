package com.pnctraining.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "QuestionCollection")
@TypeAlias("QuestionCollection")
public class QuestionEntity {
    @Transient
    public static final String SEQUENCE_NAME = "Question_Sequence";

    @Id
    private String questionId;
    private String userId;
    private String displayName;
    @Indexed(unique = true)
    private String questionTitle;
    private String questionBody;
    private List<TagEntity> tagList;
    @CreatedDate
    private Date dateQuestionAsked;
    private int questionViews;
    private int numberOfAnswers;
    private int questionLikes;
    private int questionDislikes;
    private List<AnswerEntity> answerList;
    private List<CommentEntity> commentList;
    private QuestionStatusEnum questionStatus;


    public QuestionEntity() {
    }

    public QuestionEntity(String questionTitle, String questionBody, List<TagEntity> tagList,
                          int questionDislikes, List<AnswerEntity> answerList, List<CommentEntity> commentList) {
        this.questionTitle = questionTitle;
        this.questionBody = questionBody;
        this.tagList = tagList;
        this.dateQuestionAsked = new Date();
        this.questionViews = 0;
        this.numberOfAnswers = 0;
        this.questionLikes = 0;
        this.questionDislikes = questionDislikes;
        this.answerList = answerList;
        this.commentList = commentList;
        this.questionStatus = QuestionStatusEnum.ACTIVE;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public int getQuestionViews() {
        return questionViews;
    }

    public void setQuestionViews(int questionViews) {
        this.questionViews = questionViews;
    }

    public int getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public void setNumberOfAnswers(int numberOfAnswers) {
        this.numberOfAnswers = numberOfAnswers;
    }

    public int getQuestionLikes() {
        return questionLikes;
    }

    public void setQuestionLikes(int questionLikes) {
        this.questionLikes = questionLikes;
    }

    public int getQuestionDislikes() {
        return questionDislikes;
    }

    public void setQuestionDislikes(int questionDislikes) {
        this.questionDislikes = questionDislikes;
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

    public QuestionStatusEnum getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(QuestionStatusEnum questionStatus) {
        this.questionStatus = questionStatus;
    }

    public void changeQuestionStatusDelete(){
        this.questionStatus = QuestionStatusEnum.DELETED;
    }

    public void changeQuestionStatusClosed(){
        this.questionStatus = QuestionStatusEnum.CLOSED;
    }

    public void questionViewIncrement(){
        this.questionViews += 1;
    }

    public void questionAnswerIncrement() {
        this.numberOfAnswers += 1;
    }

    public void questionAnswerDecrement() {
        this.numberOfAnswers -= 1;
    }

    public void questionLikeIncrement() {
        this.questionLikes += 1;
    }

    public void questionLikeDecrement() {
        this.questionLikes -= 1;
    }

    public void questionDislikeIncrement() {
        this.questionDislikes += 1;
    }

    public void questionDislikeDecrement() {
        this.questionDislikes -= 1;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "QuestionEntity{" +
                "questionId=" + questionId +
                ", userId=" + userId +
                ", questionTitle='" + questionTitle + '\'' +
                ", questionBody='" + questionBody + '\'' +
                ", tagList=" + tagList +
                ", dateQuestionAsked=" + dateQuestionAsked +
                ", questionViews=" + questionViews +
                ", numberOfAnswers=" + numberOfAnswers +
                ", questionLikes=" + questionLikes +
                ", questionDislikes=" + questionDislikes +
                ", answerList=" + answerList +
                ", commentList=" + commentList +
                ", questionStatus=" + questionStatus +
                '}';
    }
}
