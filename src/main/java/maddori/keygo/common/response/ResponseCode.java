package maddori.keygo.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    // common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "서버 내부 오류"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, false, "요청을 잘못 보냈습니다."),
    PAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, false, "요청하신 페이지를 찾을 수 없습니다"),
    NEED_LOGIN(HttpStatus.BAD_REQUEST, false, "로그인이 필요합니다."),
    NO_CONTENT(HttpStatus.BAD_REQUEST, false, "데이터가 없습니다."),
    REPEATED_VALUE(HttpStatus.BAD_REQUEST, false, "중복된 데이터입니다."),
    UNAUTHORIZED(HttpStatus.BAD_REQUEST, false, "권한이 없습니다"),
    TEAM_NOT_EXIST(HttpStatus.BAD_REQUEST, false, "팀이 존재하지 않음"),
    USER_NOT_TEAM_MEMBER(HttpStatus.BAD_REQUEST, false, "유저가 요청 대상 팀에 속해있지 않음"),
    REFLECTION_STATUS_ERROR(HttpStatus.BAD_REQUEST, false, "현재 회고의 상태에 요청을 수행할 수 없음"),
    NOT_INCLUDED_REFLECTION(HttpStatus.BAD_REQUEST, false, "회고가 팀에 속하지 않음"),
    INPUT_VALUE_FORMAT_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, false, "입력 값의 형식이 잘못됨"),

    // JWT
    NO_TOKEN(HttpStatus.OK, true, "TOKEN이 존재하지 않습니다"),
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, false, "TOKEN이 유효하지 않습니다"),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, false, "TOKEN이 만료되었습니다"),

    // auth
    SIGN_IN_SUCCESS(HttpStatus.OK, true, "유저 로그인 성공"),
    SIGN_UP_SUCCESS(HttpStatus.CREATED, true, "유저 회원가입과 로그인 성공"),
    DELETE_USER_SUCCESS(HttpStatus.OK, true, "유저 정보 삭제 성공"),

    // user
    USER_JOIN_TEAM_SUCCESS(HttpStatus.OK, true, "유저 팀 합류 및 프로필 생성 성공"),
    USER_UPDATE_PROFILE_SUCCESS(HttpStatus.OK, true, "유저 프로필 수정 성공"),
    PROFILE_IMAGE_FORMAT_ERROR(HttpStatus.BAD_REQUEST, false, "이미지 파일만 업로드할 수 있습니다"),
    GET_USER_TEAM_LIST_SUCCESS(HttpStatus.OK, true, "유저의 팀 목록 가져오기 성공"),
    ALREADY_TEAM_MEMBER(HttpStatus.BAD_REQUEST, false, "이미 유저가 해당 팀에 합류된 상태"),

    // reflection
    UPDATE_REFLECTION_DETAIL_SUCCESS(HttpStatus.OK, true, "회고 디테일 정보 추가 성공"),
    REFLECTION_TIME_BEFORE(HttpStatus.BAD_REQUEST, false, "회고 시간이 현 시간 이전"),
    DELETE_REFLECTION_DETAIL_SUCCESS(HttpStatus.OK, true, "회고 디테일 정보 삭제 성공"),
    GET_REFLECTION_LIST_SUCCESS(HttpStatus.OK, true, "회고목록 조회 성공"),
    GET_REFLECTION_LIST_FAIL(HttpStatus.BAD_REQUEST, true, "회고목록 조회 실패"),

    END_REFLECTION_SUCCESS(HttpStatus.OK, true, "회고 종료 성공"),
    GET_CURRENT_REFLECTION_SUCCESS(HttpStatus.OK, true, "현재 회고 정보 가져오기 성공"),

    // team
    GET_TEAM_NAME_SUCCESS(HttpStatus.OK, true, "팀 이름 가져오기 성공"),
    GET_TEAM_INFO_SUCCESS(HttpStatus.OK, true, "팀 정보 가져오기 성공"),
    INVALID_INVITATION_CODE(HttpStatus.BAD_REQUEST, false, "초대코드가 잘못 됨"),
    WITHDRAW_TEAM_SUCCESS(HttpStatus.OK, true, "유저 팀 탈퇴 성공"),
    GET_TEAM_MEMBER_LIST_SUCCESS(HttpStatus.OK, true, "팀의 멤버 목록 가져오기 성공"),
    EDIT_TEAM_NAME_SUCCESS(HttpStatus.OK, true, "팀 이름 수정 성공"),
    CREATE_JOIN_TEAM_SUCCESS(HttpStatus.CREATED, true, "팀 생성 및 팀 합류 완료"),

    // feedback
    CREATE_FEEDBACK_SUCCESS(HttpStatus.CREATED, true, "피드백 생성하기 성공"),
    NOT_INCLUDED_USER(HttpStatus.BAD_REQUEST, false, "피드백을 받는 유저가 현재 팀에 속하지 않음"),
    FEEDBACK_TYPE_ERROR(HttpStatus.BAD_REQUEST, false, "피드백 타입 오류"),
    FEEDBACK_MYSELF_ERROR(HttpStatus.BAD_REQUEST, false, "본인에게는 피드백을 작성할 수 없음"),
    GET_FEEDBACK_SUCCESS(HttpStatus.OK, true, "피드백 조회 성공"),
    GET_RECENT_FEEDBACK_SUCCESS(HttpStatus.OK, true, "최근 피드백 조회 성공"),
    GET_FEEDBACK_LIST_TO_SPECIFIC_MEMBER_SUCCESS(HttpStatus.OK, true, "특정 멤버에게 작성한 피드백 목록 가져오기 성공"),
    DELETE_FEEDBACK_SUCCESS(HttpStatus.OK, true, "피드백 삭제 성공"),
    DELETE_FEEDBACK_NOT_EXIST(HttpStatus.BAD_REQUEST, false, "삭제할 피드백 정보가 없습니다"),
    DELETE_FEEDBACK_OTHERS_ERROR(HttpStatus.BAD_REQUEST, false, "타 유저가 작성한 피드백에 대한 삭제 권한 없음"),
    UPDATE_FEEDBACK_SUCCESS(HttpStatus.CREATED, true, "피드백 수정 성공"),
    UPDATE_FEEDBACK_OTHERS_ERROR(HttpStatus.BAD_REQUEST, false, "타 유저가 작성한 피드백에 대한 수정 권한 없음"),
    NOT_INCLUDED_FEEDBACK(HttpStatus.BAD_REQUEST, false, "피드백이 회고에 속하지 않음");

    // 입력값 형식 관련

    private final HttpStatus httpStatus;
    private final Boolean success;
    private final String message;
}
