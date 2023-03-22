package maddori.keygo.common.exception;

import maddori.keygo.common.response.BasicResponse;
import maddori.keygo.common.response.FailResponse;
import maddori.keygo.common.response.ResponseCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static maddori.keygo.common.response.ResponseCode.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<BasicResponse> handleCustomException(CustomException e) {
        return FailResponse.toResponseEntity(e.getResponseCode());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<BasicResponse> handleUnexpectedException(Exception e) {
        return FailResponse.toResponseEntity(INTERNAL_SERVER_ERROR);
    }

}


