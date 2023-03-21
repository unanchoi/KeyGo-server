package maddori.keygo.entity;

import maddori.keygo.domain.CssType;
import maddori.keygo.domain.ReflectionState;
import maddori.keygo.domain.entity.Feedback;
import maddori.keygo.domain.entity.Reflection;
import maddori.keygo.domain.entity.Team;
import maddori.keygo.domain.entity.User;
import maddori.keygo.repository.FeedbackRepository;
import maddori.keygo.repository.ReflectionRepository;
import maddori.keygo.repository.TeamRepository;
import maddori.keygo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class FeedbackTest {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    ReflectionRepository reflectionRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserRepository userRepository;
    
    @Test
    public void createFeedbackSuccess() throws Exception {
    //given
        Team team = createTeam();
        Feedback feedback1 = Feedback.builder()
                .id(1L)
                .startContent("start content")
                .content("content")
                .toUser(createToUser())
                .fromUser(createFromUser())
                .type(CssType.Continue)
                .team(team)
                .reflection(createReflection(team))
                .keyword("keyword")
                .build();
        feedbackRepository.save(feedback1);
    //when
        Feedback feedback2 = feedbackRepository.findById(1L).get();
    
    //then
        assertThat(feedback1.getId()).isEqualTo(feedback2.getId());
        assertThat(feedback1.getContent()).isEqualTo(feedback2.getContent());
        assertThat(feedback1.getFromUser().getId()).isEqualTo(feedback2.getFromUser().getId());
        assertThat(feedback1.getToUser().getId()).isEqualTo(feedback2.getToUser().getId());
        assertThat(feedback1.getTeam().getId()).isEqualTo(feedback2.getTeam().getId());
        assertThat(feedback1.getStartContent()).isEqualTo(feedback2.getStartContent());
        assertThat(feedback1.getType()).isEqualTo(feedback2.getType());
        assertThat(feedback1.getReflection().getId()).isEqualTo(feedback2.getReflection().getId());
        assertThat(feedback1.getKeyword()).isEqualTo(feedback2.getKeyword());
    }

    private User createToUser() {
        User toUser =  User.builder()
                .id(1L)
                .username("toUser")
                .email("adminTo@admin.com")
                .sub("123412341234to")
                .build();
        userRepository.save(toUser);
        return toUser;
    }

    private User createFromUser() {
        User fromUser =  User.builder()
                .id(2L)
                .username("from")
                .email("adminFrom@admin.com")
                .sub("123412341234from")
                .build();
        userRepository.save(fromUser);
        return fromUser;
    }

    private Team createTeam() {
        Team team1 = Team.builder()
                .id(1L)
                .invitationCode("abcdef")
                .teamName("맛쟁이 사과처럼")
                .build();
        teamRepository.save(team1);
        return team1;
    }

    private Reflection createReflection(Team team) {
        Reflection reflection1 = Reflection.builder()
                .id(1L)
                .date(LocalDateTime.now())
                .reflectionName("맛쟁이 사자처럼 회고")
                .state(ReflectionState.Before)
                .team(team)
                .build();
        reflectionRepository.save(reflection1);
        return reflection1;
    }


}
