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
        String token = null;
        try {
            if (userEntity != null){
                if(userEntity.getPassword().equals(toHexString(getSHA(userLoginModel.getPassword())))) {
                    token = jwtHandler.createToken(userEntity.getUserId());
                }
            }
        }
        catch(Exception e){
                LOGGER.info("Login: Failed to login the user");
                throw new CPSOException(1008,"User has entered invalid email or password");
        }
        return token;
    }

    @Override
    public void invalidateToken(String token) throws CPSOException {
        try{
            jwtHandler.revokeToken(token);
        }
        catch(Exception e){
            LOGGER.info("Logout: Failed to logout the user");
            throw new CPSOException(1009,"Unable to Logout");
        }
    }

    @Override
    public UserModel getUserDetail(String token) throws CPSOException {
        LOGGER.info("USER DETAIL: request made to get user details for profile");
        try{
            Optional<UserEntity> userEntityOptional = userRepository.findById(jwtHandler.getUsernameFromToken(token));
            if(!userEntityOptional.isPresent()){
                throw new CPSOException(1000, String.format("User not found with User ID: %s",jwtHandler.getUsernameFromToken((token))));
            }else {
                return createUserModelFromUserEntity(userEntityOptional.get());
            }
        }catch(Exception e){
            LOGGER.info("USER DETAIL: Error User is not Authorized ");
            throw new  CPSOException(1010,"User is not Authorized");
        }
    }

    @Override
    public void updateUserDetail(String token, UserModel userModel) throws CPSOException {
        LOGGER.info("USER DETAIL: request made to update user information");
        try {
            Optional<UserEntity> userEntityOptional = userRepository.findById(jwtHandler.getUsernameFromToken(token));
            if(!userEntityOptional.isPresent()){
                throw new CPSOException(1000, String.format("User not found with User ID: %s", jwtHandler.getUsernameFromToken(token)));
            }
            else{
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
                LOGGER.info("USER DETAIL: saving updated User Details");
                userRepository.save(userEntity);
            }
        }catch (Exception e){
            LOGGER.info("USER DETAIL: Error User is not Authorized");
            throw new CPSOException(1010, "User is not Authorized");
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
