package com.flatmate.exceptionhandler.customeexception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WrongInputException extends RuntimeException{
    private Integer errorCode;
    private String message;
}
