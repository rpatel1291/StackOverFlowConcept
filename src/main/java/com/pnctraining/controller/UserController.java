package com.pnctraining.controller;

import com.pnctraining.entity.UserEntity;
import com.pnctraining.exception.CPSOException;
import com.pnctraining.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("${base.url}/users")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public Optional<UserEntity> findUserByUserId(@RequestBody UserEntity userId) throws CPSOException {
        LOGGER.info("GET: Find User information by UserId");
        return userService.findUserByUserId(userId.getUserId());
    }


}



