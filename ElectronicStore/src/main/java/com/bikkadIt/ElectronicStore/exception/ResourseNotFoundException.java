package com.bikkadIt.ElectronicStore.exception;

import lombok.Builder;

@Builder
public class ResourseNotFoundException extends RuntimeException{

    public ResourseNotFoundException(){
        super("resourse not found");
    }

    public ResourseNotFoundException(String message){
        super(message);
    }
}
