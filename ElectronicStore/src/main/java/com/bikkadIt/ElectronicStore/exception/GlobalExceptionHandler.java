package com.bikkadIt.ElectronicStore.exception;

import com.bikkadIt.ElectronicStore.dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalExceptionHandler {

    //handler resourse not foound exception


    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
@ExceptionHandler(ResourseNotFoundException.class)
    public ResponseEntity<ApiResponseMessage>resourseNotFoundExceptionHandler(ResourseNotFoundException ex){

    logger.info("Exception Handler invoke");
    ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(false).build();
    return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
}
}
