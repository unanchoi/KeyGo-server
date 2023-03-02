package maddori.keygo.common.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.ResponseEntity;

@SuperBuilder
@Getter
@Setter
public class FailResponse extends BasicResponse{

    public static ResponseEntity<BasicResponse> toResponseEntity(ResponseCode responseCode) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(SuccessResponse.builder()
                        .success(responseCode.getSuccess())
                        .message(responseCode.getMessage())
                        .build()
                );
    }
}
