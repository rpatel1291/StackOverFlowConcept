package com.pnctraining.service;


import com.pnctraining.entity.*;
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
        try {
            if (jwtHandler.getUsernameFromToken(token) != null) {
                commentModel.setUserId(jwtHandler.getUsernameFromToken(token));
                commentModel.setDisplayName(userRepository.findByUserId(jwtHandler.getUsernameFromToken(token)).getDisplayName());
                commentModel.setDateCommentAdded(new Date());
                commentModel.setQuestionId(questionId);
                CommentEntity commentEntity = createCommentEntityFromModel(commentModel);

                Optional<UserEntity> userEntityOptional = userRepository.findUserByQuestionId(questionId);

                if (userEntityOptional.isPresent()) {
                    UserEntity userEntity = userEntityOptional.get();
                    List<QuestionEntity> questionEntityList = userEntity.getQuestionList();
                    Optional<QuestionEntity> questionEntityOptional = questionEntityList.stream().filter(qe -> qe.getQuestionId().equals(questionId)).findFirst();
                    if (questionEntityOptional.isPresent()) {
                        QuestionEntity questionEntity = questionEntityOptional.get();
                        if (questionEntity.getCommentList() == null) {
                            questionEntity.setCommentList(new ArrayList<CommentEntity>());
                        }
                        List<CommentEntity> commentEntityList = questionEntity.getCommentList();
                        commentEntityList.add(commentEntity);

                        questionEntity.setCommentList(commentEntityList);
                        int counter = 0;
                        for (QuestionEntity qe : questionEntityList) {
                            if (qe.getQuestionId().equals(questionId)) {
                                break;
                            }
                            counter++;
                        }
                        questionEntityList.remove(counter);
                        questionEntityList.add(questionEntity);

                        userEntity.setQuestionList(questionEntityList);

                        userRepository.save(userEntity);

                        userEntity = userRepository.findByUserId(jwtHandler.getUsernameFromToken(token));
                        if (userEntity.getCommentList() == null) {
                            userEntity.setCommentList(new ArrayList<CommentEntity>());
                        }
                        commentEntityList = userEntity.getCommentList();
                        commentEntityList.add(commentEntity);
                        userEntity.setCommentList(commentEntityList);

                        userRepository.save(userEntity);

                    } else {
                        //question does not exist
                        LOGGER.error("CommentServiceImp[addCommentToQuestion]: Question not found");
                        throw new CPSOException(1020, "Question not found");
                    }
                } else {
                    //user does not exist
                    throw new  CPSOException(1002, "User not found");
                }

            }else{
                throw new CPSOException(1001, "Unauthorized User");
            }

        }catch(CPSOException se){
            LOGGER.error(String.format("CommentServiceImp[addCommentToQuestion] : %s ",se));
            throw se;
        }catch(Exception e){
            LOGGER.error(String.format("CommentServiceImp[addCommentToQuestion] : %s ",e));
            throw new CPSOException(1026,"Unable to add comment to question");
        }
    }

    @Override
    public void addCommentToAnswer(String token, CommentModel commentModel, String answerId) throws CPSOException {
        try{
            if(jwtHandler.getUsernameFromToken(token) == null){
                throw new CPSOException(1001, "Unauthorized User");
            }
            commentModel.setUserId(jwtHandler.getUsernameFromToken(token));
            commentModel.setDisplayName(userRepository.findByUserId(jwtHandler.getUsernameFromToken(token)).getDisplayName());
            commentModel.setDateCommentAdded(new Date());
            commentModel.setAnswerId(answerId);
            CommentEntity commentEntity = createCommentEntityFromModel(commentModel);


            Optional<UserEntity> userEntityOptional = userRepository.findUserByAnswerId(answerId);
            if(userEntityOptional.isPresent()){
                  UserEntity userEntity = userEntityOptional.get();
                  List<AnswerEntity> answerEntityList = userEntity.getAnswerList();
                  Optional<AnswerEntity> answerEntityOptional = answerEntityList.stream().filter(ae -> ae.getAnswerId().equals(answerId)).findFirst();
                  if(answerEntityOptional.isPresent()){
                      AnswerEntity answerEntity = answerEntityOptional.get();
                      List<CommentEntity> answerCommentEntityList = answerEntity.getCommentsList();
                      answerCommentEntityList.add(commentEntity);
                      answerEntity.setCommentsList(answerCommentEntityList);

                      int counter = 0;
                      for(AnswerEntity ae: answerEntityList){
                          if(ae.getAnswerId().equals(answerId)){
                              break;
                          }
                          counter++;
                      }

                      answerEntityList.remove(counter);
                      answerEntityList.add(answerEntity);
                      userEntity.setAnswerList(answerEntityList);
                      userRepository.save(userEntity);

                      UserEntity userWhoMadeTheComment = userRepository.findByUserId(jwtHandler.getIssuerFromToken(token));
                      List<CommentEntity> commentEntityList = userWhoMadeTheComment.getCommentList();
                      commentEntityList.add(commentEntity);
                      userWhoMadeTheComment.setCommentList(commentEntityList);

                      userRepository.save(userWhoMadeTheComment);



                  }else{
                      throw new CPSOException(1021, "Answer not found");
                  }

            }else{
                throw new CPSOException(1002, "User not found");
            }

        }catch (CPSOException se){
            LOGGER.error(String.format("CommentServiceImp[addCommentToAnswer] : %s ",se));
            throw se;
        }
        catch (Exception e){
            LOGGER.error(String.format("CommentServiceImp[addCommentToAnswer] : %s ",e));
            throw new CPSOException(1026,"Unable to add comment to answer");}
    }

    private CommentEntity createCommentEntityFromModel(CommentModel commentModel) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setUserId(commentModel.getUserId());
        commentEntity.setDisplayName(commentModel.getDisplayName());
        commentEntity.setDateCommentAdded(commentModel.getDateCommentAdded());
        commentEntity.setCommentBody(commentModel.getCommentBody());
        commentEntity.setQuestionId(commentModel.getQuestionId());
        commentEntity.setAnswerId(commentModel.getAnswerId());
        return commentEntity;
    }
}
