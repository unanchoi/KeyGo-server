package maddori.keygo.service;

import maddori.keygo.domain.entity.Team;
import maddori.keygo.dto.team.TeamResponseDto;
import maddori.keygo.repository.TeamRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class TeamServiceTest {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamService teamService;

    @Test
    public void getTeamDetailSuccess() throws Exception {
    //given
        Team team = Team.builder()
                .id(1L)
                .teamName("team")
                .invitationCode("abcdef")
                .build();
        teamRepository.save(team);
    //when
        TeamResponseDto teamResponseDto = teamService.getCertainTeam(String.valueOf(1L));

    //then
        assertThat(teamResponseDto.getId()).isEqualTo(team.getId());
        assertThat(teamResponseDto.getTeamName()).isEqualTo(team.getTeamName());
        assertThat(teamResponseDto.getInvitationCode()).isEqualTo(team.getInvitationCode());
    }
}
