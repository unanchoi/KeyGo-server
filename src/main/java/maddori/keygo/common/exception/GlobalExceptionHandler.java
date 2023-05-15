package maddori.keygo.common.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import maddori.keygo.common.response.BasicResponse;
import maddori.keygo.common.response.FailResponse;
import maddori.keygo.common.response.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static maddori.keygo.common.response.ResponseCode.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<BasicResponse> handleCustomException(CustomException e) {
        return FailResponse.toResponseEntity(e.getResponseCode());
    }

    @ExceptionHandler({JwtException.class, java.security.SignatureException.class})
    protected ResponseEntity<BasicResponse> handleJwtException(Exception e) {
        if (e.getClass().equals(ExpiredJwtException.class)){
            return FailResponse.toResponseEntity(TOKEN_EXPIRED);
        } else {
            return FailResponse.toResponseEntity(TOKEN_INVALID);
        }
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<BasicResponse> handleUnexpectedException(Exception e) {
        return FailResponse.toResponseEntity(INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<BasicResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return FailResponse.toResponseEntity(BAD_REQUEST);
    }

}


