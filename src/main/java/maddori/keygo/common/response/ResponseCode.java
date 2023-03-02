package maddori.keygo.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    //team
    GET_TEAM_INFO_SUCCESS(HttpStatus.OK, true, "팀 정보 가져오기 성공");

    private final HttpStatus httpStatus;
    private final Boolean success;
    private final String message;
}
