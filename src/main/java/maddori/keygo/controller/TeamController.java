package maddori.keygo.controller;

import maddori.keygo.service.TeamService;
import maddori.keygo.dto.team.TeamResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/teams")
public class TeamController {

    private TeamService teamService;

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamResponseDto> getCertainTeamDetail(@RequestParam String teamId) {

        TeamResponseDto teamResponseDto = teamService.getCertainTeam(teamId);

        return new ResponseEntity<>(teamResponseDto, HttpStatus.OK);
    }
}
