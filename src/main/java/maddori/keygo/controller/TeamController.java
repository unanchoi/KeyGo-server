package maddori.keygo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Basic;
import lombok.RequiredArgsConstructor;
import maddori.keygo.common.response.BasicResponse;
import maddori.keygo.common.response.FailResponse;
import maddori.keygo.common.response.SuccessResponse;
import maddori.keygo.dto.team.*;
import maddori.keygo.dto.user.UserTeamRequestDto;
import maddori.keygo.dto.user.UserTeamResponseDto;
import maddori.keygo.security.SecurityService;
import maddori.keygo.service.TeamService;
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
    public ResponseEntity<? extends BasicResponse> createTeam(@RequestPart("profile_image") @Nullable MultipartFile profileImage,
                                                                @RequestParam Map<String, String> params) throws IOException {
        // reference: https://tailerbox.tistory.com/30
        ObjectMapper mapper = new ObjectMapper();
        CreateTeamRequestDto createTeamRequestDto = mapper.convertValue(params, CreateTeamRequestDto.class);
        UserTeamResponseDto userTeamResponseDto = teamService.createTeamAndJoinTeam(SecurityService.getCurrentUserId(), profileImage, createTeamRequestDto);
        return SuccessResponse.toResponseEntity(CREATE_JOIN_TEAM_SUCCESS, userTeamResponseDto);
    }

    @PatchMapping("/{teamId}/team-name")
    public ResponseEntity<? extends BasicResponse> editTeamName(@PathVariable("teamId") Long teamId,
                                                                @RequestBody TeamRequestDto teamRequestDto) {
        TeamNameResponseDto teamNameResponseDto = teamService.editTeamName(teamId, teamRequestDto);
        return SuccessResponse.toResponseEntity(EDIT_TEAM_NAME_SUCCESS, teamNameResponseDto);
    }

    @GetMapping("/{teamId}/members")
    public ResponseEntity<? extends BasicResponse> getTeamMembers(@PathVariable("teamId") Long teamId) {
        TeamMemberListResponseDto response = teamService.getTeamMembers(teamId);
        return SuccessResponse.toResponseEntity(GET_USER_TEAM_LIST_SUCCESS, response);
    }
}
