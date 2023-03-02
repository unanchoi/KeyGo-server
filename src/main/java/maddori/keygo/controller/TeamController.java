package maddori.keygo.controller;

import jakarta.persistence.Basic;
import lombok.RequiredArgsConstructor;
import maddori.keygo.common.response.BasicResponse;
import maddori.keygo.common.response.SuccessResponse;
import maddori.keygo.service.TeamService;
import maddori.keygo.dto.team.TeamResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
