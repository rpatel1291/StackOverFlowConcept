package com.pnctraining.service;

import com.pnctraining.entity.RegisterModel;
import com.pnctraining.entity.UserEntity;
import com.pnctraining.exception.CPSOException;
import com.pnctraining.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class RegistrationServiceImp implements RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;



    //Log4j
    private static final Logger LOGGER = LogManager.getLogger(RegistrationServiceImp.class);

    @Override
    public void createNewUser(RegisterModel registerModel) throws NoSuchAlgorithmException {
        LOGGER.info("CREATE NEW USER: Starting process for creating new user.");
        UserEntity userEntity = createUserEntityFromModel(registerModel);
        if(userRepository.findByEmail(userEntity.getEmail()) == null){
            if(userRepository.findByDisplayName(userEntity.getDisplayName()) == null){
                userEntity.setPassword(toHexString(getSHA(userEntity.getPassword())));
                LOGGER.info(String.format("CREATE NEW USER: New user %s has been made", userEntity.getDisplayName()));
                userRepository.save(userEntity);
            }
            else{
                LOGGER.info("CREATE NEW USER: Exception display name chosen is already taken");
                throw new CPSOException(1003,"Display Name already exists");
            }
        }
        else{
            LOGGER.info("CREATE NEW USER: Exception email already exists");
            throw new CPSOException(1007,"Email already exists");
        }
    }

    private UserEntity createUserEntityFromModel(RegisterModel registerModel) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(String.valueOf(sequenceGeneratorService.generateSequence(UserEntity.SEQUENCE_NAME)));
        userEntity.setEmail(registerModel.getEmail());
        userEntity.setPassword(registerModel.getPassword());
        userEntity.setDisplayName(registerModel.getDisplayName());
        return userEntity;
    }


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

}

