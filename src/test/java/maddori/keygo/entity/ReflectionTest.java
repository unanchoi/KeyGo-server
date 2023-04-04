package maddori.keygo.entity;

import maddori.keygo.domain.ReflectionState;
import maddori.keygo.domain.entity.Reflection;
import maddori.keygo.domain.entity.Team;
import maddori.keygo.repository.ReflectionRepository;
import maddori.keygo.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class ReflectionTest {

    @Autowired
    ReflectionRepository reflectionRepository;

    @Autowired
    TeamRepository teamRepository;
    
    @Test
    public void createReflectionSuccess() throws Exception {
    //given
        Team team = createTeam();
        teamRepository.save(team);
        Reflection reflection1 = Reflection.builder()
                .id(1L)
                .date(isNow())
                .reflectionName("맛쟁이 사자처럼 회고")
                .state(ReflectionState.Before)
                .team(team)
                .build();
        team.updateCurrentReflection(reflection1);
        reflectionRepository.save(reflection1);
    //when
        Reflection reflection2 = reflectionRepository.findById(1L).get();
    
    //then
        assertThat(reflection1.getId()).isEqualTo(reflection2.getId());
        assertThat(reflection1.getReflectionName()).isEqualTo(reflection2.getReflectionName());
        assertThat(reflection1.getTeam().getId()).isEqualTo(reflection2.getTeam().getId());
        assertThat(reflection1.getState()).isEqualTo(reflection2.getState());
        assertThat(reflection1.getDate()).isEqualTo(reflection2.getDate());
    }

    private LocalDateTime isNow() {
        return LocalDateTime.now();
    }

    private Team createTeam() {
        return Team.builder()
                .id(1L)
                .teamName("맛쟁이 사과처럼")
                .invitationCode("abcdef")
                .build();
    }
}
