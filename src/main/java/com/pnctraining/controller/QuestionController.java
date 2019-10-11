package com.pnctraining.controller;

import com.pnctraining.entity.QuestionEntity;
import com.pnctraining.entity.QuestionModel;
import com.pnctraining.exception.CPSOException;
import com.pnctraining.exception.ResponseMessage;
import com.pnctraining.service.QuestionService;
import com.pnctraining.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("${base.url}/question")
public class QuestionController {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    //Log4j
    private static final Logger LOGGER = LogManager.getLogger(QuestionController.class);


    @PostMapping("/newquestion")
    public ResponseEntity createNewQuestion(@RequestBody QuestionModel questionModel, @RequestHeader String token){
        try{
            LOGGER.info("NEW QUESTION: Creating a new question request");
            questionService.createNewQuestion(questionModel, token);
            LOGGER.info("NEW QUESTION: Question Created");
        } catch (CPSOException e) {
            return new ResponseEntity(new ResponseMessage(e.getStatusCode(),e.getStatusMessage()), HttpStatus.BAD_REQUEST);
        }

        return  new ResponseEntity(HttpStatus.CREATED);

    }

    @GetMapping(value = "/{questionId}")
    public ResponseEntity getQuestionById(@PathVariable String questionId, @RequestHeader String token) {
        try{
            LOGGER.info("SEARCH QUESTION: Search question for question id request");
            QuestionEntity result = questionService.getQuestionDetails(questionId, token);
            LOGGER.info("SEARCH QUESTION: Found question with question id");
            return new ResponseEntity(result, HttpStatus.OK);

        } catch (CPSOException e) {
            LOGGER.info("SEARCH QUESTION: Error Invalid Question Id");
            return new ResponseEntity(new ResponseMessage(e.getStatusCode(),e.getStatusMessage()),HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(value = "/{questionId}/update")
    public ResponseEntity updateQuestionDetails(@PathVariable String questionId, @RequestBody QuestionEntity questionEntity, @RequestHeader String token){
        try{
            LOGGER.info("UPDATE QUESTION: Update question request");
            questionService.updateQuestionDetails(questionId,questionEntity,token);
            LOGGER.info("UPDATE QUESTION: Update request complete");
            return new ResponseEntity(HttpStatus.OK);
        }catch(CPSOException e){
            LOGGER.info("SEARCH QUESTION: Error Invalid Question Id");
            return new ResponseEntity(new ResponseMessage(e.getStatusCode(),e.getStatusMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity getAllQuestions(@RequestHeader String token){
        try {
            LOGGER.info("QUESTION: Request to retrieve all question");
            List<QuestionModel> questionEntityList = questionService.getAllQuestions(token);
            LOGGER.info("");
            return new ResponseEntity(questionEntityList, HttpStatus.OK);
        }catch (CPSOException e){
            LOGGER.info("");
            return new ResponseEntity(new ResponseMessage(e.getStatusCode(),e.getStatusMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{userid}")
    public ResponseEntity getQuestionsByUserId(@RequestHeader String token, @PathVariable String userId){
        try{
            LOGGER.info("Question: Request to retrieve all question for user");
            List<QuestionEntity> questionEntityList = questionService.getQuestionById(token);
            LOGGER.info("Question: Request complete");
            return new ResponseEntity(questionEntityList, HttpStatus.OK);

        }
        catch (CPSOException e){
            return new ResponseEntity(new ResponseMessage(e.getStatusCode(),e.getStatusMessage()),HttpStatus.BAD_REQUEST);
        }
    }
}
