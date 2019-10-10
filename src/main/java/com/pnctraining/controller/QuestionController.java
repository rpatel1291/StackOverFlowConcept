package com.pnctraining.controller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
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
        } catch (CPSOException e) {
            e.printStackTrace();
        }

        return  new ResponseEntity(HttpStatus.OK);

//
//        try{
//            if(token != null){
//                LOGGER.info("Question: Creating new question request");
//                questionService.createNewQuestion(questionEntity, token);
////                questionService.findUserByUserId(token);
//                return new ResponseEntity(HttpStatus.CREATED);
//            }
//            else{
//                return new ResponseEntity(new ResponseMessage(1011,"Invalid Question."), HttpStatus.BAD_REQUEST);
//            }
//        }catch (CPSOException e){
//            return new ResponseEntity(new ResponseMessage(e.getStatusCode(),e.getStatusMessage()), HttpStatus.FORBIDDEN);
//        }
    }


}
