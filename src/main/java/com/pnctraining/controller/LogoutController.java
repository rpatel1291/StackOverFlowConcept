package com.pnctraining.controller;


import com.pnctraining.exception.CPSOException;
import com.pnctraining.exception.ResponseMessage;
import com.pnctraining.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${base.url}")
public class LogoutController {

    @Autowired
    private UserService userService;

    //Log4j
    private static final Logger LOGGER = LogManager.getLogger(LogoutController.class);

    @PostMapping("/logout")
    public ResponseEntity<ResponseMessage> logout(@RequestHeader("token") String token ){
        userService.logoutOfAccount(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
