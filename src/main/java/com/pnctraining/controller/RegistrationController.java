package com.pnctraining.controller;

import com.pnctraining.entity.UserEntity;
import com.pnctraining.exception.CPSOException;
import com.pnctraining.exception.ResponseMessage;
import com.pnctraining.service.RegistrationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("${base.url}/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    //Log4j
    private static final Logger LOGGER = LogManager.getLogger(RegistrationController.class);

    @PostMapping
    public ResponseEntity register(@RequestBody UserEntity userEntity){
       try {
           LOGGER.info("Post(start): Register form has been submitted");
           registrationService.createNewUser(userEntity);
       }catch (CPSOException e){
           return new ResponseEntity<>(new ResponseMessage(e.getStatusCode(),e.getStatusMessage()), HttpStatus.BAD_REQUEST);
       } catch (NoSuchAlgorithmException e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
