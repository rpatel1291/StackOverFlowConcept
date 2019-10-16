package com.pnctraining.service;

import com.pnctraining.entity.UserEntity;
import com.pnctraining.entity.UserLoginModel;
import com.pnctraining.entity.UserModel;
import com.pnctraining.exception.CPSOException;
import com.pnctraining.repository.UserRepository;
import com.pnctraining.security.JWTHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    //Log4j
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImp.class);

    @Autowired
    private JWTHandler jwtHandler;


    @Override
    public Optional<UserEntity> findUserByUserId(String userId) throws CPSOException {
        LOGGER.info("FIND USER BY USER ID");
        if(!userRepository.findById(userId).isPresent()){
            LOGGER.info("FIND USER BY ID: Error User does not exist");
            throw new CPSOException(1000,String.format("User with UserId: %s does not exist", userId));
        }
        else {
            LOGGER.info("FIND USER BY ID: User exists");
            return userRepository.findById(userId);
        }
    }



    @Override
    public String logIntoAccount(UserLoginModel userLoginModel) throws CPSOException {
        LOGGER.info("LOGIN: Request to login");
        UserEntity userEntity = userRepository.findByEmail(userLoginModel.getEmail());

        try {
            if (userEntity != null && userEntity.getPassword().equals(toHexString(getSHA(userLoginModel.getPassword())))) {
                    return jwtHandler.createToken(userEntity.getUserId());
            }
            else{
                throw new CPSOException(1024, "Invalid credentials");
            }
        }catch (CPSOException e){
            LOGGER.error(String.format("UserServiceImpl[login(user] : %s", e));
            throw e;
        }
        catch(Exception e){
            LOGGER.error(String.format("UserServiceImpl[login(user] : %s", e));
            throw new CPSOException(1023,"User has entered invalid email or password");
        }
    }

    @Override
    public void logoutOfAccount(String token) {
        try{
            jwtHandler.revokeToken(token);
        }
        catch(Exception e){
            LOGGER.error(String.format("UserServiceImp[logout(String)] : %s", e));
        }
    }

    @Override
    public UserModel getUserDetail(String token) throws CPSOException {
        LOGGER.info("UserServiceImp[getUserDetail(String)] : request made to get user details for profile");
        try{
            Optional<UserEntity> userEntityOptional = userRepository.findById(jwtHandler.getUsernameFromToken(token));
            if(!userEntityOptional.isPresent() || !jwtHandler.validate(token,jwtHandler.getUsernameFromToken(token))) {
                throw new CPSOException(1001, "Unauthorized user");
            }

            return createUserModelFromUserEntity(userEntityOptional.get());
        }catch (CPSOException se){
            LOGGER.error(String.format("UserServiceImp[getUserDetail(String)] : %s", se));
            throw se;
        }
        catch(Exception e){
            LOGGER.error(String.format("UserServiceImp[getUserDetail(String)] : %s", e));
            throw new  CPSOException(1025, "Unable to get user details");
        }
    }

    @Override
    public void updateUserDetail(String token, UserModel userModel) throws CPSOException {
        LOGGER.info("UserServiceImp[updateUserDetails(String, UserModel)] : request made to update user information");
        try {
            Optional<UserEntity> userEntityOptional = userRepository.findById(jwtHandler.getUsernameFromToken(token));
            if(!userEntityOptional.isPresent()){
                throw new CPSOException(1001, "Unauthorized User");
            } else{
                UserEntity userEntity = userEntityOptional.get();
                if(!userModel.getDisplayName().equals(userEntity.getDisplayName())){
                    userEntity.setDisplayName(userModel.getDisplayName());
                }
                else if(!userModel.getEmail().equals(userEntity.getEmail())){
                    userEntity.setEmail(userModel.getEmail());
                }else if(!userEntity.getPassword().equals(toHexString(getSHA(userModel.getPassword())))){
                    userEntity.setPassword(toHexString(getSHA(userModel.getPassword())));
                }else if(userModel.getTagList().size() != userEntity.getTagList().size()){
                    userEntity.setTagList(userModel.getTagList());
                }
                LOGGER.info("UserServiceImp[updateUserDetails(String, UserModel)] : saving updated User Details");
                userRepository.save(userEntity);
            }
        }catch (CPSOException se){
            LOGGER.error(String.format("UserServiceImp[updateUserDetails(String,UserModel)] : %s",se));
            throw se;
        }
        catch (Exception e){
            LOGGER.error(String.format("UserServiceImp[updateUserDetails(String,UserModel)] : %s",e));
            throw new CPSOException(1026, "Unable to update user details");
        }
    }

    /*
        Internal Methods
     */

    private static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        return messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private static String toHexString(byte[] hash){
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while(hexString.length() < 32){
            hexString.insert(0,'0');
        }
        return hexString.toString();
    }

    private static UserModel createUserModelFromUserEntity(UserEntity userEntity){
        UserModel userModel = new UserModel();
        userModel.setDisplayName(userEntity.getDisplayName());
        userModel.setEmail(userEntity.getEmail());
        userModel.setQuestionList(userEntity.getQuestionList());
        userModel.setAnswerList(userEntity.getAnswerList());
        userModel.setCommentList(userEntity.getCommentList());
        return userModel;
    }
}
