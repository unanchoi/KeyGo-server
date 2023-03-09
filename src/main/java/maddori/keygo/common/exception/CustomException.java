package maddori.keygo.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import maddori.keygo.common.response.ResponseCode;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    private final ResponseCode responseCode;
}
