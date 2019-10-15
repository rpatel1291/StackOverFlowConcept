package com.pnctraining.controller;

import com.pnctraining.entity.AnswerModel;
import com.pnctraining.exception.CPSOException;
import com.pnctraining.exception.ResponseMessage;
import com.pnctraining.service.AnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${base.url}/{questionId}")
public class AnswerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnswerController.class);

    @Autowired
    private AnswerService answerService;

    @PostMapping("/answer")
    public ResponseEntity addAnswer(@RequestHeader(value="token") String token,@PathVariable(value = "questionId") String questionId , @RequestBody AnswerModel answerModel){

        try{
            answerService.addAnswer(token,questionId,answerModel);
            return new ResponseEntity(HttpStatus.CREATED);
        }catch (CPSOException e){
            LOGGER.error("ANSWER: Error occurred at '/answer' endpoint");
            return new ResponseEntity(new ResponseMessage(e.getStatusCode(), e.getStatusMessage()),HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            LOGGER.error("ANSWER: Unknown error");
            return  new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

}
