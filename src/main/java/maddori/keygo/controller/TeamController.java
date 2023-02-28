package maddori.keygo.controller;

import lombok.RequiredArgsConstructor;
import maddori.keygo.service.TeamService;
import maddori.keygo.dto.team.TeamResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/teams")
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamResponseDto> getCertainTeamDetail(@PathVariable("teamId") String teamId) {
        TeamResponseDto teamResponseDto = teamService.getCertainTeam(teamId);

        return new ResponseEntity<>(teamResponseDto, HttpStatus.OK);
    }
}
