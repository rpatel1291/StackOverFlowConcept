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

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImp implements QuestionService {

    //Log4j
    private static final Logger LOGGER = LogManager.getLogger(QuestionServiceImp.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    JWTHandler jwtHandler;

    @Override
    public void createNewQuestion(QuestionModel questionModel, String token) throws CPSOException {
        if(jwtHandler.getUsernameFromToken(token) != null){
            QuestionEntity newQuestionEntity = createNewQuestionEntityFromModel(questionModel);
            questionRepository.save(newQuestionEntity);
            System.out.println(newQuestionEntity.toString());

            UserEntity user = userRepository.findByUserId(jwtHandler.getUsernameFromToken(token));
            System.out.println(user.toString());
            List<QuestionEntity> userQuestion = user.getQuestionList();
            userQuestion.add(newQuestionEntity);
            user.setQuestionList(userQuestion);
            userRepository.save(user);
        }
        else{
            throw new  CPSOException(1010,"User is not Authorized");
        }
    }


    private QuestionEntity createNewQuestionEntityFromModel(QuestionModel questionModel){
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setDateQuestionAsked(new Date());
        questionEntity.setQuestionTitle(questionModel.getQuestionTitle());
        questionEntity.setQuestionBody(questionModel.getQuestionBody());
        questionEntity.setTagList(questionModel.getTagList());
        return questionEntity;
    }
}
