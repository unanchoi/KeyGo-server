package maddori.keygo.common.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

@SuperBuilder
@Getter
@Setter
public class SuccessResponse extends BasicResponse {
    @Nullable
    private Object detail;

    public static ResponseEntity<BasicResponse> toResponseEntity(ResponseCode responseCode, @Nullable Object data) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(SuccessResponse.builder()
                        .success(responseCode.getSuccess())
                        .message(responseCode.getMessage())
                        .detail(data)
                        .build()
                );
    }
}
