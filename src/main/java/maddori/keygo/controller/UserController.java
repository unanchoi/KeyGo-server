package maddori.keygo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maddori.keygo.common.response.BasicResponse;
import maddori.keygo.common.response.FailResponse;
import maddori.keygo.common.response.NoDetailSuccessResponse;
import maddori.keygo.common.response.SuccessResponse;
import maddori.keygo.dto.user.UserTeamListResponseDto;
import maddori.keygo.dto.user.UserTeamRequestDto;
import maddori.keygo.dto.user.UserTeamResponseDto;
import maddori.keygo.security.SecurityService;
import maddori.keygo.service.UserService;
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
    public ResponseEntity<? extends BasicResponse> getUserTeamList() {
        List<UserTeamListResponseDto> userTeamListResponseDtoList = userService.getUserTeamList(SecurityService.getCurrentUserId());
        return SuccessResponse.toResponseEntity(GET_USER_TEAM_LIST_SUCCESS, userTeamListResponseDtoList);
    }
    @CrossOrigin
    @PostMapping("/join-team/{teamId}")
    public ResponseEntity<? extends BasicResponse> userJoinTeam(@PathVariable("teamId") Long teamId,
                                                                @RequestPart("profile_image") @Nullable MultipartFile profileImage,
                                                                UserTeamRequestDto userTeamRequestDto) throws IOException {
        UserTeamResponseDto userTeamResponseDto = userService.userJoinTeam(SecurityService.getCurrentUserId(), teamId, profileImage, userTeamRequestDto);
        return SuccessResponse.toResponseEntity(USER_JOIN_TEAM_SUCCESS, userTeamResponseDto);
    }

    @DeleteMapping("team/{teamId}/leave")
    public ResponseEntity<? extends BasicResponse> userLeaveTeam(@PathVariable("teamId") Long teamId) {
        userService.userLeaveTeam(SecurityService.getCurrentUserId(), teamId);
        return NoDetailSuccessResponse.toResponseEntity(WITHDRAW_TEAM_SUCCESS);
    }
}
