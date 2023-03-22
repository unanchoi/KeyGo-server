package maddori.keygo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import maddori.keygo.common.exception.CustomException;
import maddori.keygo.common.response.BasicResponse;
import maddori.keygo.common.response.FailResponse;
import maddori.keygo.common.response.SuccessResponse;
import maddori.keygo.dto.user.UserTeamListResponseDto;
import maddori.keygo.dto.user.UserTeamRequestDto;
import maddori.keygo.dto.user.UserTeamResponseDto;
import maddori.keygo.service.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static maddori.keygo.common.response.ResponseCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/teams")
    public ResponseEntity<? extends BasicResponse> getUserTeamList(@RequestHeader("user_id") Long userId) {
        try {
            List<UserTeamListResponseDto> userTeamListResponseDtoList = userService.getUserTeamList(userId);
            return SuccessResponse.toResponseEntity(GET_USER_TEAM_LIST_SUCCESS, userTeamListResponseDtoList);
        } catch (RuntimeException e) {
            return FailResponse.toResponseEntity(INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin
    @PostMapping("/join-team/{teamId}")
    public ResponseEntity<? extends BasicResponse> userJoinTeam(@RequestHeader("user_id") Long userId, @PathVariable("teamId") Long teamId,
                                                                @RequestPart("profile_image") @Nullable MultipartFile profileImage,
                                                                UserTeamRequestDto userTeamRequestDto) throws IOException {
//        System.out.println("teamId = " + teamId);
//        System.out.println("userId = " + userId);
//        System.out.println("profile_image = " + profileImage);
//        System.out.println("userTeamRequestDto = " + userTeamRequestDto);
        UserTeamResponseDto userTeamResponseDto = userService.userJoinTeam(userId, teamId, profileImage, userTeamRequestDto);
        return SuccessResponse.toResponseEntity(USER_JOIN_TEAM_SUCCESS, userTeamResponseDto);
//        return null;
    }

}
