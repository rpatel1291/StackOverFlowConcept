package com.pnctraining.service;

import com.pnctraining.entity.QuestionEntity;
import com.pnctraining.entity.QuestionModel;
import com.pnctraining.entity.UserEntity;
import com.pnctraining.exception.CPSOException;
import com.pnctraining.repository.QuestionRepository;
import com.pnctraining.repository.UserRepository;
import com.pnctraining.security.JWTHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImp implements QuestionService {

    //Log4j
    private static final Logger LOGGER = LogManager.getLogger(QuestionServiceImp.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private JWTHandler jwtHandler;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public void createNewQuestion(QuestionModel questionModel, String token) throws CPSOException {
        LOGGER.info("Question: Creating new Question. Checking Token for validity");
        if(jwtHandler.getUsernameFromToken(token) != null){
            LOGGER.info("Question: Token is valid. Getting Question Model.");
            QuestionEntity newQuestionEntity = createNewQuestionEntityFromModel(questionModel);
            newQuestionEntity.setUserId(jwtHandler.getUsernameFromToken(token));
            LOGGER.info("Question: Finding User");
            Optional<UserEntity> userEntityOptional = userRepository.findById(jwtHandler.getUsernameFromToken(token));

            if(userEntityOptional.isPresent()) {
                LOGGER.info("Question: Setting Question to user");
                UserEntity userEntity = userEntityOptional.get();
                if(userEntity.getQuestionList() == null){
                    List<QuestionEntity> userEntityQuestionList = new ArrayList<>();
                    userEntity.setQuestionList(userEntityQuestionList);
                }
                List<QuestionEntity> userEntityQuestionList = userEntity.getQuestionList();
                userEntityQuestionList.add(newQuestionEntity);
                userEntity.setQuestionList(userEntityQuestionList);
                userRepository.save(userEntity);
                LOGGER.info("Question: Question saved to user");
            }
            else{
                throw new CPSOException(1012, "Non Valid User. Please sign in.");
            }
        }
        else{
            throw new  CPSOException(1010,"User is not Authorized");
        }
    }


    private QuestionEntity createNewQuestionEntityFromModel(QuestionModel questionModel){
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setQuestionId(String.valueOf(sequenceGeneratorService.generateSequence(QuestionEntity.SEQUENCE_NAME)));
        questionEntity.setDateQuestionAsked(new Date());
        questionEntity.setQuestionTitle(questionModel.getQuestionTitle());
        questionEntity.setQuestionBody(questionModel.getQuestionBody());
        questionEntity.setTagList(questionModel.getTagList());
        return questionEntity;
    }
}
