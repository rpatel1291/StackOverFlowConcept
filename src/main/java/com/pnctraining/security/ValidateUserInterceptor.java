package com.pnctraining.security;

import com.pnctraining.exception.CPSOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidateUserInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateUserInterceptor.class);

    @Autowired
    private
    JWTHandler jwtHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        try{
            if (!jwtHandler.validate(request.getHeader("token"))) throw new CPSOException(1001, "Unauthorized user");
            return true;
        }catch (CPSOException e){
            throw e;
        }catch (Exception e){
            LOGGER.error(String.format("ValidateUserInterceptor[preHandler(HttpServletRequest, HttpServletResponse,Object)] : %s",e));
            throw e;
        }
    }
}
