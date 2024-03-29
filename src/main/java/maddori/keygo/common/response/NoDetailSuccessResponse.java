package maddori.keygo.common.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.ResponseEntity;

@SuperBuilder
@Getter
@Setter
public class NoDetailSuccessResponse extends BasicResponse {

    public static ResponseEntity<BasicResponse> toResponseEntity(ResponseCode responseCode) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(FailResponse.builder()
                        .success(responseCode.getSuccess())
                        .message(responseCode.getMessage())
                        .build()
                );
    }
}
