package com.pnctraining.controller;

import com.pnctraining.entity.UserModel;
import com.pnctraining.exception.CPSOException;
import com.pnctraining.exception.ResponseMessage;
import com.pnctraining.security.JWTHandler;
import com.pnctraining.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("{base.url}/user/profile")
public class ProfileController {

    @Autowired
    JWTHandler jwtHandler;

    @Autowired
    UserService userService;

    private static final Logger LOGGER = LogManager.getLogger(ProfileController.class);

    @GetMapping
    public ResponseEntity getUserDetail(@RequestHeader String token){

        LOGGER.info("USER PROFILE: Get user profile request");
        UserModel userModel = userService.getUserDetail(token);
        return new ResponseEntity<>(userModel, HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity updateUserDetail(@RequestHeader String token, @RequestBody UserModel userModel){
        LOGGER.info("USER PROFILE: Update user profile request");
        userService.updateUserDetail(token, userModel);
        LOGGER.info("USER PROFILE: Update to user profile has been processed");
        return new ResponseEntity(HttpStatus.OK);
    }



}
