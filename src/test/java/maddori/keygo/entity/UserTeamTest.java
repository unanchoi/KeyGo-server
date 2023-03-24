package maddori.keygo.entity;

import maddori.keygo.domain.entity.Team;
import maddori.keygo.domain.entity.User;
import maddori.keygo.domain.entity.UserTeam;
import maddori.keygo.repository.TeamRepository;
import maddori.keygo.repository.UserRepository;
import maddori.keygo.repository.UserTeamRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserTeamTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserTeamRepository userTeamRepository;
    
    @Test
    public void userTeamCreateSuccess () throws Exception {
    //given

        User user1 =  User.builder()
                .id(1L)
                .username("user")
                .email("admin@admin.com")
                .sub("123412341234")
                .build();
        userRepository.save(user1);


        Team team1 = Team.builder()
                .id(1L)
                .invitationCode("abcdef")
                .teamName("맛쟁이 사과처럼")
                .build();
        teamRepository.save(team1);

        UserTeam userTeam1 = UserTeam.builder()
                .id(1L)
                .team(team1)
                .user(user1)
                .admin(true)
                .nickname("닉네임")
                .role("role")
                .profileImagePath("image path")
                .build();
        userTeamRepository.save(userTeam1);
    //when
        UserTeam userTeam2 = userTeamRepository.findById(1L).get();
    
    //then
        assertThat(userTeam1.getId()).isEqualTo(userTeam2.getId());
        assertThat(userTeam1.getTeam().getId()).isEqualTo(userTeam2.getTeam().getId());
        assertThat(userTeam1.getUser().getId()).isEqualTo(userTeam2.getUser().getId());
        assertThat(userTeam1.getAdmin()).isEqualTo(userTeam2.getAdmin());
        assertThat(userTeam1.getNickname()).isEqualTo(userTeam2.getNickname());
        assertThat(userTeam1.getRole()).isEqualTo(userTeam2.getRole());
        assertThat(userTeam1.getProfileImagePath()).isEqualTo(userTeam2.getProfileImagePath());



    }
}
