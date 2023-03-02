package maddori.keygo.common.exception;

import maddori.keygo.common.response.BasicResponse;
import maddori.keygo.common.response.FailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<BasicResponse> handleCustomException(CustomException e) {
        return FailResponse.toResponseEntity(e.getResponseCode());
    }
}
