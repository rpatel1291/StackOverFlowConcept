package com.pnctraining.controller;


import com.pnctraining.entity.CommentModel;
import com.pnctraining.exception.CPSOException;
import com.pnctraining.exception.ResponseMessage;
import com.pnctraining.service.CommentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${base.url}/{questionId}")
public class CommentController {

    private static final Logger LOGGER = LogManager.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;


    @PostMapping("/addquestioncomment")
    public ResponseEntity<ResponseMessage> addCommentToQuestion(@RequestHeader String token, @RequestBody CommentModel commentModel, @PathVariable(value="questionId") String questionId ) {
            commentService.addCommentToQuestion(token, commentModel, questionId);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{answerId}/addanswercomment")
    public ResponseEntity addCommentToAnswer(@RequestHeader String token, @RequestBody CommentModel commentModel, @PathVariable(value = "answerId") String answerId) {
            commentService.addCommentToAnswer(token, commentModel, answerId);
            return new ResponseEntity(HttpStatus.CREATED);

    }


}
