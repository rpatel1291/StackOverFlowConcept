package com.pnctraining.service;

import com.pnctraining.entity.AnswerEntity;
import com.pnctraining.entity.AnswerModel;
import com.pnctraining.entity.QuestionEntity;
import com.pnctraining.entity.UserEntity;
import com.pnctraining.exception.CPSOException;
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
public class AnswerServiceImp implements AnswerService{

    private static final Logger LOGGER = LogManager.getLogger(AnswerServiceImp.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTHandler jwtHandler;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;


    @Override
    public void addAnswer(String token, String questionId ,AnswerModel answerModel) throws CPSOException {
        LOGGER.info(String.format("ANSWER: Adding answer to a Question with id: %s",questionId));
        if(jwtHandler.getUsernameFromToken(token) != null ) {
            answerModel.setQuestionId(questionId);
            answerModel.setUserId(jwtHandler.getUsernameFromToken(token));
            answerModel.setDateAnswered(new Date());
            answerModel.setDisplayName(userRepository.findByUserId(jwtHandler.getUsernameFromToken(token)).getDisplayName());
            AnswerEntity newAnswerEntity = createNewAnswerEntityFromModel(answerModel);

            LOGGER.info("ANSWER: Finding Question");

            Optional<UserEntity> userEntityOptional = userRepository.findUserByQuestionId(questionId);
            if(userEntityOptional.isPresent()){
                UserEntity userEntity = userEntityOptional.get();
                Optional<QuestionEntity> questionEntityOptional = userEntity.getQuestionList().stream().filter(QE -> QE.getQuestionId().equals(questionId)).findFirst();
                if(questionEntityOptional.isPresent()){
                    QuestionEntity questionEntity = questionEntityOptional.get();
                    if(questionEntity.getAnswerList() == null){
                        List<AnswerEntity> answerEntityArrayList = new ArrayList<>();
                        questionEntity.setAnswerList(answerEntityArrayList);
                    }
                    List<AnswerEntity> answerEntityList = questionEntity.getAnswerList();
                    answerEntityList.add(newAnswerEntity);
                    questionEntity.setAnswerList(answerEntityList);

                    //Saving to User
                    List<QuestionEntity> userQuestionEntityList = userRepository.findByDisplayName(answerModel.getDisplayName()).getQuestionList();
                    int counter = 0;
                    for(QuestionEntity qe : userQuestionEntityList){
                        if(qe.getQuestionId().equals(questionId)){
                            break;
                        }
                        counter++;
                    }
                    userQuestionEntityList.remove(counter);
                    userQuestionEntityList.add(questionEntity);

                    userEntity.setQuestionList(userQuestionEntityList);

                    UserEntity user = userRepository.findByUserId(jwtHandler.getUsernameFromToken(token));
                    if(user.getAnswerList()==null){
                        user.setAnswerList(new ArrayList<AnswerEntity>());
                    }
                    List<AnswerEntity> userAnswerEntities = user.getAnswerList();
                    userAnswerEntities.add(newAnswerEntity);
                    user.setAnswerList(userAnswerEntities);
                    userRepository.save(userEntity);
                    userRepository.save(user);

                }else{
                    //Error question not found
                    throw new CPSOException(1015,"Question with Question Id not found.");
                }
            }
            else{
                throw new CPSOException(1014,String.format("User who asked Question with ID: %s does not exist",questionId));

            }


        }else{
            LOGGER.error("ANSWER Error: User not Authorized");
            throw new CPSOException(1010, "User is not Authorized");
        }
    }

    private AnswerEntity createNewAnswerEntityFromModel(AnswerModel answerModel) {
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setQuestionId(answerModel.getQuestionId());
        answerEntity.setUserId(answerModel.getUserId());
        answerEntity.setDateAnswered(answerModel.getDateAnswered());
        answerEntity.setAnswerBody(answerModel.getAnswerBody());
        return answerEntity;
    }
}
