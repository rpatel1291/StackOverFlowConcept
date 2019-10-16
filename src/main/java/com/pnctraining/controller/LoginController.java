package com.pnctraining.controller;

import com.pnctraining.entity.UserLoginModel;
import com.pnctraining.exception.CPSOException;
import com.pnctraining.exception.ResponseMessage;
import com.pnctraining.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${base.url}")
public class LoginController {

    @Autowired
    private UserService userService;

    //Log4j
    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(@RequestBody UserLoginModel userLoginModel){
        try{
            LOGGER.info("LOGIN: User has entered Email and Password");
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("token",userService.logIntoAccount(userLoginModel));
            LOGGER.info("LOGIN: Successful");
            return new ResponseEntity<>(responseHeaders,HttpStatus.OK);
        }catch(CPSOException e){
            LOGGER.info("LOGIN: Error invalid email and password combination");
            return new ResponseEntity<>(new ResponseMessage(e.getStatusCode(),e.getStatusMessage()), HttpStatus.BAD_REQUEST);
        }
    }




}
