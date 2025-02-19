package com.flatmate.exceptionhandler;

import com.flatmate.exceptionhandler.customeexception.CommonException;
import com.flatmate.exceptionhandler.customeexception.EmptyInputException;
import com.flatmate.exceptionhandler.customeexception.NotFoundException;
import com.flatmate.exceptionhandler.customeexception.WrongInputException;
import com.flatmate.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EmptyInputException.class})
    public ResponseEntity<?> emptyInput(EmptyInputException emptyInputException) {
        return ResponseEntity.status(400)
                .body(new CommonResponse(emptyInputException.getMessage(), emptyInputException.getErrorCode(), null));
    }
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> notFoundException(NotFoundException notFoundException) {
        return ResponseEntity.status(500)
                .body(new CommonResponse(notFoundException.getMessage(), notFoundException.getErrorCode(), null));
    }

    @ExceptionHandler(WrongInputException.class)
    public ResponseEntity<?> wrongInputException(WrongInputException wrongInputException) {
        return ResponseEntity.status(400)
                .body(new CommonResponse(wrongInputException.getMessage(), wrongInputException.getErrorCode(), null));
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<?> commonException(CommonException commonException) {
        return ResponseEntity.status(400)
                .body(new CommonResponse(commonException.getMessage(), commonException.getErrorCode(), null));
    }



}
