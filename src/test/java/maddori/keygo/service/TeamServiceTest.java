package maddori.keygo.service;

import maddori.keygo.domain.entity.Team;
import maddori.keygo.domain.entity.UserTeam;
import maddori.keygo.dto.team.TeamMemberListResponseDto;
import maddori.keygo.dto.team.TeamResponseDto;
import maddori.keygo.dto.user.UserTeamResponseDto;
import maddori.keygo.repository.TeamRepository;
import maddori.keygo.repository.UserRepository;
import maddori.keygo.repository.UserTeamRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class TeamServiceTest {

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserTeamRepository userTeamRepository;

    @Autowired
    TeamService teamService;

    @Test
    public void getCertainTeamDetailSuccess() throws Exception {
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

    @Test
    public void getTeamMembersSuccess() throws Exception {
        // given
        Team team = Team.builder()
                .id(2L)
                .teamName("team")
                .invitationCode("123ABC")
                .build();
        teamRepository.save(team);

        UserTeam userTeam1 = UserTeam.builder()
                .user(userRepository.findById(1L).get())
                .team(team)
                .nickname("user1")
                .role("role1")
                .build();

        UserTeam userTeam2 = UserTeam.builder()
                .user(userRepository.findById(2L).get())
                .team(team)
                .nickname("user2")
                .role("role2")
                .build();
        userTeamRepository.save(userTeam1);
        userTeamRepository.save(userTeam2);

        // when
        TeamMemberListResponseDto teamMemberListResponseDto = teamService.getTeamMembers(team.getId());

        // then
        UserTeamResponseDto userTeamResponseDto1 = UserTeamResponseDto.builder()
                .id(1L)
                .nickname("user1")
                .role("role1")
                .build();
        UserTeamResponseDto userTeamResponseDto2 = UserTeamResponseDto.builder()
                .id(2L)
                .nickname("user2")
                .role("role2")
                .build();

        assertThat(teamMemberListResponseDto.getMembers())
                .containsOnly(userTeamResponseDto1, userTeamResponseDto2);
    }
}
