package maddori.keygo.entity;

import maddori.keygo.domain.entity.Team;
import maddori.keygo.repository.TeamRepository;
import maddori.keygo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class TeamTest {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void teamCreateSuccess() throws Exception {
    //given
        Team team1 = Team.builder()
                .id(1L)
                .invitationCode("abcdef")
                .teamName("맛쟁이 사과처럼")
                .build();
        teamRepository.save(team1);
    //when
        Team team2 = teamRepository.findById(1L).get();

    //then
        assertThat(team1.getId()).isEqualTo(team2.getId());
        assertThat(team1.getInvitationCode()).isEqualTo(team2.getInvitationCode());
        assertThat(team1.getTeamName()).isEqualTo(team2.getTeamName());

    }


}
