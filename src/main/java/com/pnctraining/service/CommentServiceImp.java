package com.pnctraining.service;


import com.pnctraining.entity.CommentEntity;
import com.pnctraining.entity.CommentModel;
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
public class CommentServiceImp implements CommentService {

    private static final Logger LOGGER = LogManager.getLogger(CommentServiceImp.class);

    @Autowired
    private JWTHandler jwtHandler;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public void addCommentToQuestion(String token, CommentModel commentModel, String questionId) throws CPSOException{
        if(jwtHandler.getUsernameFromToken(token) != null) {
            commentModel.setUserId(jwtHandler.getUsernameFromToken(token));
            commentModel.setUserId(userRepository.findByUserId(jwtHandler.getUsernameFromToken(token)).getDisplayName());
            commentModel.setDateCommentAdded(new Date());
            CommentEntity commentEntity = createCommentEntityFromModel(commentModel);

            Optional<UserEntity> userEntityOptional = userRepository.findUserByQuestionId(questionId);

            if(userEntityOptional.isPresent()){
                UserEntity userEntity = userEntityOptional.get();
                List<QuestionEntity> questionEntityList = userEntity.getQuestionList();
                Optional<QuestionEntity> questionEntityOptional = questionEntityList.stream().filter(qe -> qe.getQuestionId().equals(questionId)).findFirst();
                if(questionEntityOptional.isPresent()){
                    QuestionEntity questionEntity = questionEntityOptional.get();
                    if(questionEntity.getCommentList() == null){
                        questionEntity.setCommentList(new ArrayList<CommentEntity>());
                    }
                    List<CommentEntity> commentEntityList = questionEntity.getCommentList();
                    commentEntityList.add(commentEntity);

                    questionEntity.setCommentList(commentEntityList);
                    int counter = 0;
                    for(QuestionEntity qe : questionEntityList){
                        if(qe.getQuestionId().equals(questionId)){
                            break;
                        }
                        counter++;
                    }
                    questionEntityList.remove(counter);
                    questionEntityList.add(questionEntity);

                    userEntity.setQuestionList(questionEntityList);

                    userRepository.save(userEntity);

                    userEntity = userRepository.findByUserId(jwtHandler.getUsernameFromToken(token));
                    if(userEntity.getCommentList() == null){
                        userEntity.setCommentList(new ArrayList<CommentEntity>());
                    }
                    commentEntityList = userEntity.getCommentList();
                    commentEntityList.add(commentEntity);
                    userEntity.setCommentList(commentEntityList);

                    userRepository.save(userEntity);

                }else{
                    //question does not exist
                    throw new CPSOException(1020,String.format("Question with question ID: %s does not exist.",questionId));
                }
            }else{
                //user does not exist
                throw new CPSOException(1010,String.format("User with Question ID: %s does not exist.",questionId) );
            }

        }
    }

    private CommentEntity createCommentEntityFromModel(CommentModel commentModel) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setUserId(commentModel.getUserId());
        commentEntity.setDisplayName(commentModel.getDisplayName());
        commentEntity.setDateCommentAdded(commentModel.getDateCommentAdded());
        commentEntity.setCommentBody(commentModel.getCommentBody());
        return commentEntity;
    }
}
