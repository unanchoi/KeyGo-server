package maddori.keygo.controller;

import lombok.RequiredArgsConstructor;
import maddori.keygo.common.response.BasicResponse;
import maddori.keygo.common.response.NoDetailSuccessResponse;
import maddori.keygo.common.response.SuccessResponse;
import maddori.keygo.dto.team.TeamResponseDto;
import maddori.keygo.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static maddori.keygo.common.response.ResponseCode.DELETE_USER_SUCCESS;
import static maddori.keygo.common.response.ResponseCode.GET_TEAM_INFO_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/auth/")
public class AuthController {
    private final AuthService authService;

    @DeleteMapping("signOut")
    public ResponseEntity<? extends BasicResponse> getCertainTeamDetail(@RequestHeader("user_id") Long userId) {
        authService.deleteUser(userId);
        return NoDetailSuccessResponse.toResponseEntity(DELETE_USER_SUCCESS);
    }

}
