package com.pnctraining.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CPSOExpectionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ResponseMessage> handleAllExceptions(Exception e, WebRequest request){
        ResponseMessage responseMessage = new ResponseMessage(1000, "unable to process");
        return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CPSOException.class)
    public final ResponseEntity<ResponseMessage> handleCPSOExceptions(CPSOException e, WebRequest request){
        ResponseMessage responseMessage = new ResponseMessage(e.getStatusCode(), e.getStatusMessage());

        if(e.getStatusCode().equals(1001)||e.getStatusCode().equals(1024)) return new ResponseEntity<>(responseMessage, HttpStatus.UNAUTHORIZED);
        else if(e.getStatusCode().equals(1034)) return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }
}
