package maddori.keygo.controller;

import lombok.RequiredArgsConstructor;
import maddori.keygo.common.response.BasicResponse;
import maddori.keygo.common.response.NoDetailSuccessResponse;
import maddori.keygo.common.response.SuccessResponse;
import maddori.keygo.dto.auth.LoginRequestDto;
import maddori.keygo.dto.auth.LoginResponseDto;
import maddori.keygo.dto.team.TeamResponseDto;
import maddori.keygo.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static maddori.keygo.common.response.ResponseCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/auth/")
public class AuthController {
    private final AuthService authService;

    @DeleteMapping("signOut")
    public ResponseEntity<? extends BasicResponse> signOut(@RequestHeader("user_id") Long userId) {
        authService.deleteUser(userId);
        return NoDetailSuccessResponse.toResponseEntity(DELETE_USER_SUCCESS);
    }

    @PostMapping("")
    public ResponseEntity<? extends BasicResponse> signOut(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = authService.appleLogin(loginRequestDto);

        return SuccessResponse.toResponseEntity(SIGN_UP_SUCCESS, loginResponseDto);
    }
}
