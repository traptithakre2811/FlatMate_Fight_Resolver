package com.flatmate.exceptionhandler.customeexception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmptyInputException extends RuntimeException {
    private Integer errorCode;
    private String message;
}
