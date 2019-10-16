package com.pnctraining.controller;

import com.pnctraining.entity.QuestionEntity;
import com.pnctraining.entity.QuestionModel;
import com.pnctraining.service.QuestionService;
import com.pnctraining.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("${base.url}/question")
public class QuestionController {

    @Autowired
    UserService userService;

    @Autowired
    private
    QuestionService questionService;

    //Log4j
    private static final Logger LOGGER = LogManager.getLogger(QuestionController.class);


    @PostMapping("/newquestion")
    public ResponseEntity createNewQuestion(@RequestBody QuestionModel questionModel, @RequestHeader String token) {

        LOGGER.info("QuestionController[createNewQuestion(QuestionModel,String)]: Creating a new question request");
        questionService.createNewQuestion(questionModel, token);
        LOGGER.info("QuestionController[createNewQuestion(QuestionModel,String)]: Question Created");
        return new ResponseEntity(HttpStatus.CREATED);

    }

    @GetMapping(value = "/{questionId}")
    public ResponseEntity getQuestionById(@PathVariable String questionId, @RequestHeader String token) {
            LOGGER.info("SEARCH QUESTION: Search question for question id request");
            QuestionEntity result = questionService.getQuestionDetails(questionId, token);
            LOGGER.info("SEARCH QUESTION: Found question with question id");
            return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PatchMapping(value = "/{questionId}/update")
    public ResponseEntity updateQuestionDetails(@PathVariable String questionId, @RequestBody QuestionEntity questionEntity, @RequestHeader String token){
            LOGGER.info("UPDATE QUESTION: Update question request");
            questionService.updateQuestionDetails(questionId,questionEntity,token);
            LOGGER.info("UPDATE QUESTION: Update request complete");
            return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity getAllQuestions(@RequestHeader String token){
            LOGGER.info("QUESTION: Request to retrieve all question");
            List<QuestionModel> questionEntityList = questionService.getAllQuestions(token);
            LOGGER.info("");
            return new ResponseEntity<>(questionEntityList, HttpStatus.OK);
    }

    @GetMapping(value = "/{userid}")
    public ResponseEntity getQuestionsByUserId(@RequestHeader String token, @PathVariable String userId){
            LOGGER.info("Question: Request to retrieve all question for user");
            List<QuestionEntity> questionEntityList = questionService.getQuestionById(token);
            LOGGER.info("Question: Request complete");
            return new ResponseEntity<>(questionEntityList, HttpStatus.OK);

    }
}
