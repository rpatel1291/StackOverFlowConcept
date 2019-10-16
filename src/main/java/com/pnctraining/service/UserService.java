package com.pnctraining.service;


import com.pnctraining.entity.UserEntity;
import com.pnctraining.entity.UserLoginModel;
import com.pnctraining.entity.UserModel;
import com.pnctraining.exception.CPSOException;

import java.util.Optional;

public interface UserService {
    public Optional<UserEntity> findUserByUserId(String userId) throws CPSOException;
    public String logIntoAccount(UserLoginModel userLoginModel) throws CPSOException;
    public void logoutOfAccount(String token);
    public UserModel getUserDetail(String token) throws CPSOException;
    public void updateUserDetail(String token, UserModel userModel) throws CPSOException;
}
