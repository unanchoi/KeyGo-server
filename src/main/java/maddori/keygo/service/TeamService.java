package maddori.keygo.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import maddori.keygo.common.exception.CustomException;
import maddori.keygo.common.response.ResponseCode;
import maddori.keygo.dto.team.TeamResponseDto;
import maddori.keygo.domain.entity.Team;
import maddori.keygo.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static maddori.keygo.common.response.ResponseCode.*;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    @Transactional(readOnly = true)
    public TeamResponseDto getCertainTeam(String teamId) {
         Team team = teamRepository.findById(Long.valueOf(teamId)).orElseThrow(() -> new CustomException(TEAM_NOT_EXIST));
         TeamResponseDto response = TeamResponseDto.
                 builder().id(team.getId())
                            .teamName(team.getTeamName())
                            .invitationCode(team.getInvitationCode())
                            .build();
         return response;
    }

}
