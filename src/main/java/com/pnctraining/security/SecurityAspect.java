package com.pnctraining.security;


import com.pnctraining.exception.CPSOException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

    @Autowired
    private JWTHandler jwtHandler;

    @Before("execution(* com.pnctraining.service.QuestionServiceImp.*(..)) && args(token)")
    public void validateUser(JoinPoint joinPoint, String token) throws CPSOException{

        try{
            if(!jwtHandler.validate(token)){
                throw new CPSOException(1012,"Unauthorized User");
            }
        }
        catch(StackOverflowError e){
            throw e;
        }
        catch(Exception e){
            throw e;
        }
    }

}
