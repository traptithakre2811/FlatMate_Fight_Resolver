package com.flatmate.exceptionhandler.customeexception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotFoundException extends RuntimeException{
    private Integer errorCode;
    private String message;


}
