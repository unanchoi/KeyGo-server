package maddori.keygo.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import maddori.keygo.team.TeamResponseDto;
import maddori.keygo.domain.entity.Team;
import maddori.keygo.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    @Transactional(readOnly = true)
    public TeamResponseDto getCertainTeam(String teamId) {
         Team team = teamRepository.findById(Long.valueOf(teamId)).orElseThrow(() -> new EntityNotFoundException("유저가 요청 대상 팀에 속해 있지 않음."));
         TeamResponseDto response = TeamResponseDto.
                 builder().id(team.getId())
                            .teamName(team.getTeamName())
                            .invitationCode(team.getInvitationCode())
                            .build();
         return response;
    }
}
