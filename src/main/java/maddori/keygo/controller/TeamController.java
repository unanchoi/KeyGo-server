package maddori.keygo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Basic;
import lombok.RequiredArgsConstructor;
import maddori.keygo.common.response.BasicResponse;
import maddori.keygo.common.response.FailResponse;
import maddori.keygo.common.response.SuccessResponse;
import maddori.keygo.dto.team.CreateTeamRequestDto;
import maddori.keygo.dto.team.TeamNameResponseDto;
import maddori.keygo.dto.user.UserTeamRequestDto;
import maddori.keygo.dto.user.UserTeamResponseDto;
import maddori.keygo.service.TeamService;
import maddori.keygo.dto.team.TeamResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static maddori.keygo.common.response.ResponseCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/teams")
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/{teamId}")
    public ResponseEntity<? extends BasicResponse> getCertainTeamDetail(@PathVariable("teamId") String teamId) {
        TeamResponseDto teamResponseDto = teamService.getCertainTeam(teamId);
        return SuccessResponse.toResponseEntity(GET_TEAM_INFO_SUCCESS, teamResponseDto);
    }

    @GetMapping("")
    public ResponseEntity<? extends BasicResponse> getCertainTeamName(@RequestParam("invitation_code") String invitationCode) {
        TeamNameResponseDto teamNameResponseDto = teamService.getCertainTeamName(invitationCode);
        return SuccessResponse.toResponseEntity(GET_TEAM_INFO_SUCCESS, teamNameResponseDto);
    }

    @PostMapping("")
    public ResponseEntity<? extends BasicResponse> createTeam(@RequestHeader("user_id") Long userId,
                                                                @RequestPart("profile_image") @Nullable MultipartFile profileImage,
                                                                @RequestParam Map<String, String> params) throws IOException {
        // reference: https://tailerbox.tistory.com/30
        ObjectMapper mapper = new ObjectMapper();
        CreateTeamRequestDto createTeamRequestDto = mapper.convertValue(params, CreateTeamRequestDto.class);
        UserTeamResponseDto userTeamResponseDto = teamService.createTeamAndJoinTeam(userId, profileImage, createTeamRequestDto);
        return SuccessResponse.toResponseEntity(CREATE_JOIN_TEAM_SUCCESS, userTeamResponseDto);
    }
}
