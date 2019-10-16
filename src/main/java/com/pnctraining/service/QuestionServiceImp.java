package com.pnctraining.service;

import com.pnctraining.entity.QuestionEntity;
import com.pnctraining.entity.QuestionModel;
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
public class QuestionServiceImp implements QuestionService {

    //Log4j
    private static final Logger LOGGER = LogManager.getLogger(QuestionServiceImp.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTHandler jwtHandler;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public void createNewQuestion(QuestionModel questionModel, String token) throws CPSOException {
        try {
            LOGGER.info("QuestionServiceImp[createNewQuestion(QuestionModel,String)]: Creating new Question. Checking Token for validity");
            if (jwtHandler.getUsernameFromToken(token) != null) {
                LOGGER.info("QuestionServiceImp[createNewQuestion(QuestionModel,String)]:: Token is valid. Getting Question Model.");
                QuestionEntity newQuestionEntity = createNewQuestionEntityFromModel(questionModel);
                newQuestionEntity.setUserId(jwtHandler.getUsernameFromToken(token));
                newQuestionEntity.setDisplayName(userRepository.findByUserId(jwtHandler.getUsernameFromToken(token)).getDisplayName());
                LOGGER.info("QuestionServiceImp[createNewQuestion(QuestionModel,String)]:: Finding User");
                Optional<UserEntity> userEntityOptional = userRepository.findById(jwtHandler.getUsernameFromToken(token));

                if (userEntityOptional.isPresent()) {
                    LOGGER.info("QuestionServiceImp[createNewQuestion(QuestionModel,String)]:: Setting Question to user");
                    UserEntity userEntity = userEntityOptional.get();
                    if (userEntity.getQuestionList() == null) {
                        List<QuestionEntity> userEntityQuestionList = new ArrayList<>();
                        userEntity.setQuestionList(userEntityQuestionList);
                    }
                    List<QuestionEntity> userEntityQuestionList = userEntity.getQuestionList();
                    userEntityQuestionList.add(newQuestionEntity);
                    userEntity.setQuestionList(userEntityQuestionList);
                    userRepository.save(userEntity);
                    LOGGER.info("QuestionServiceImp[createNewQuestion(QuestionModel,String)]:: Question saved to user");
                } else {
                    LOGGER.error("QuestionServiceImp[createNewQuestion(QuestionModel,String)]: Invalid User");
                    throw new CPSOException(1002, "Unable to get user");
                }
            } else {
                LOGGER.error("QuestionServiceImp[createNewQuestion(QuestionModel,String)]: User not Authorized");
                throw new CPSOException(1001, "Unauthorized User");
            }
        }catch (CPSOException se){
            LOGGER.error(String.format("QuestionServiceImp[createNewQuestion(QuestionModel,String)]: %s",se));
            throw se;
        }catch (Exception e){
            LOGGER.error(String.format("QuestionServiceImp[createNewQuestion(QuestionModel,String)]: %s",e));
            throw new CPSOException(1031, "Unable to create new question");
        }
    }

    @Override
    public QuestionEntity getQuestionDetails(String questionId, String token) throws CPSOException {
        try {
            if (jwtHandler.getUsernameFromToken(token) != null) {
                Optional<UserEntity> userEntityOptional = userRepository.findUserByQuestionId(questionId);
                if(userEntityOptional.isPresent()) {
                    Optional<QuestionEntity> questionEntityOptional = userEntityOptional.get().getQuestionList().stream().filter(questionEntity -> questionEntity.getQuestionId().equals(questionId)).findFirst();
                    if (questionEntityOptional.isPresent()) {
                        return questionEntityOptional.get();
                    } else {
                        throw new CPSOException(1013, "Invalid Question Id");
                    }
                }else{
                    throw new CPSOException(1001,"Unauthorized User");
                }
            } else {
                LOGGER.info("Question Error: User not Authorized");
                throw new CPSOException(1001, "Unauthorized User");
            }
        }catch(CPSOException se){
            LOGGER.error(String.format("QuestionServiceImp[getQuestionDetails(String,String)]: %s", se));
            throw se;
        }catch (Exception e){
            LOGGER.error(String.format("QuestionServiceImp[getQuestionDetails(String,String)]: %s", e));
            throw new CPSOException(1033, "Unable to get get question details");
        }
    }

    @Override
    public void updateQuestionDetails(String questionId, QuestionEntity questionEntity, String token) throws CPSOException {
        try{
            if(jwtHandler.getUsernameFromToken(token) == null){ throw new CPSOException(1001, "Unauthorized User"); }
            Optional<UserEntity> userEntityOptional = userRepository.findUserByQuestionId(questionId);
            if(userEntityOptional.isPresent()){
                Optional<QuestionEntity> questionEntityOptional = userEntityOptional.get().getQuestionList().stream().filter(questionE -> questionE.getQuestionId().equals(questionId)).findFirst();
                if(questionEntityOptional.isPresent()){
                    QuestionEntity question = questionEntityOptional.get();
                    if(!question.getUserId().equals(jwtHandler.getIssuerFromToken(token))){throw new CPSOException(1001,"Unauthorized User"); }
                    if(!question.getQuestionTitle().equals(questionEntity.getQuestionTitle())){ question.setQuestionTitle(questionEntity.getQuestionTitle()); }
                    else if(!question.getQuestionBody().equals(questionEntity.getQuestionBody())){ question.setQuestionBody(questionEntity.getQuestionBody()); }
                    else if(!question.getQuestionStatus().equals(questionEntity.getQuestionStatus())){ question.setQuestionStatus(questionEntity.getQuestionStatus()); }
                    else{ throw new CPSOException(1015,"Invalid Update Request"); }
                    int counter = 0;
                    for(QuestionEntity qe : userEntityOptional.get().getQuestionList()){
                        if (qe.getQuestionId().equals(questionId)) { break; }
                        counter++;
                    }
                    UserEntity userEntity = userEntityOptional.get();
                    List<QuestionEntity> userQuestions = userEntity.getQuestionList();
                    userQuestions.remove(counter);
                    userQuestions.add(question);
                    userEntity.setQuestionList(userQuestions);
                    userRepository.save(userEntity);
                }else{
                    throw new CPSOException(1013,"Invalid Question Id");
                }
            }else{
                throw new CPSOException(1002, "User not found");
            }
        }catch(CPSOException se){
            LOGGER.error(String.format("QuestionServiceImp[updateQuestionDetail] : %s",se));
            throw se;
        }catch(Exception e){
            LOGGER.error(String.format("QuestionServiceImp[updateQuestionDetail] : %s",e));
            throw new CPSOException(1034, "Unable to Update Question Details");
        }

    }

    @Override
    public List<QuestionModel> getAllQuestions(String token) throws CPSOException {
        if(jwtHandler.getUsernameFromToken(token) != null) {
            List<UserEntity> userEntityList = userRepository.findAll();

            List<QuestionModel> questionModelList = new ArrayList<>();

            for (UserEntity userEntity : userEntityList) {
                List<QuestionEntity> questionEntityList = userEntity.getQuestionList();
                for (QuestionEntity questionEntity : questionEntityList) {
                    questionModelList.add(createNewQuestionModelFromEntity(questionEntity));
                }
            }
            return questionModelList;
        }else{
            throw new CPSOException(1010,"User is not Authorized");
        }
    }

    @Override
    public List<QuestionEntity> getQuestionById(String token) throws CPSOException {
        if(jwtHandler.getUsernameFromToken(token) != null){
            Optional<UserEntity> userEntityOptional = userRepository.findById(jwtHandler.getUsernameFromToken(token));
            if(userEntityOptional.isPresent()){
                UserEntity userEntity = userEntityOptional.get();
                if(userEntity.getQuestionList() == null){
                    return new ArrayList<QuestionEntity>();
                }else{
                    return userEntity.getQuestionList();
                }
            }else{
                throw new CPSOException(1007, String.format("User with user id: %s", jwtHandler.getUsernameFromToken(token)));
            }
        }else{
            throw new CPSOException(1010, "User is not Authorized");
        }
    }

    /* Internal Methods */
    private QuestionEntity createNewQuestionEntityFromModel(QuestionModel questionModel){
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setQuestionId(String.valueOf(sequenceGeneratorService.generateSequence(QuestionEntity.SEQUENCE_NAME)));
        questionEntity.setDateQuestionAsked(new Date());
        questionEntity.setQuestionTitle(questionModel.getQuestionTitle());
        questionEntity.setQuestionBody(questionModel.getQuestionBody());
        questionEntity.setTagList(questionModel.getTagList());
        return questionEntity;
    }

    private QuestionModel createNewQuestionModelFromEntity(QuestionEntity questionEntity){
        QuestionModel questionModel = new QuestionModel();
        questionModel.setQuestionTitle(questionEntity.getQuestionTitle());
        questionModel.setQuestionBody(questionEntity.getQuestionBody());
        questionModel.setTagList(questionEntity.getTagList());
        questionModel.setDateQuestionAsked(questionEntity.getDateQuestionAsked());
        questionModel.setDisplayName(questionEntity.getDisplayName());
        return  questionModel;
    }
}
