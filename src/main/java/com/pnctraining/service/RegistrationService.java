package com.pnctraining.service;

import com.pnctraining.entity.RegisterModel;
import com.pnctraining.entity.UserEntity;
import com.pnctraining.exception.CPSOException;

import java.security.NoSuchAlgorithmException;


public interface RegistrationService {
    public void createNewUser(RegisterModel registerModel) throws NoSuchAlgorithmException;
}
