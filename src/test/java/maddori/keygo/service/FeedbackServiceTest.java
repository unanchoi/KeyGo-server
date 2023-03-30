package maddori.keygo.service;

import maddori.keygo.domain.CssType;
import maddori.keygo.domain.ReflectionState;
import maddori.keygo.domain.entity.Feedback;
import maddori.keygo.domain.entity.Reflection;
import maddori.keygo.domain.entity.Team;
import maddori.keygo.domain.entity.User;
import maddori.keygo.dto.feedback.FeedbackUpdateRequestDto;
import maddori.keygo.dto.feedback.FeedbackUpdateResponseDto;
import maddori.keygo.repository.FeedbackRepository;
import maddori.keygo.repository.ReflectionRepository;
import maddori.keygo.repository.TeamRepository;
import maddori.keygo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class FeedbackServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    ReflectionRepository reflectionRepository;

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    FeedbackService feedbackService;

    @Test
    public void updateFeedbackSuccess() throws Exception {
        //given
        Team team = createTeam();
        Reflection reflection = createReflection(team);
        Feedback feedback = Feedback.builder()
                .id(1L)
                .startContent("start content")
                .content("content")
                .toUser(createToUser())
                .fromUser(createFromUser())
                .type(CssType.Continue)
                .team(team)
                .reflection(reflection)
                .keyword("keyword")
                .build();
        feedbackRepository.save(feedback);

        FeedbackUpdateResponseDto dto = feedbackService.update(
                team.getId(),
                reflection.getId(),
                feedback.getId(),
                FeedbackUpdateRequestDto.builder()
                        .type(CssType.Continue.getValue())
                        .keyword("keyword update")
                        .content("content update")
                        .build());

        //when
        Feedback updatedFeedback = feedbackRepository.findById(1L).get();
        System.out.println("updatedFeedback : " +  updatedFeedback);
        //then
        assertThat(dto.getType()).isEqualTo(updatedFeedback.getType().toString());
        assertThat(dto.getKeyword()).isEqualTo(updatedFeedback.getKeyword());
        assertThat(dto.getContent()).isEqualTo(updatedFeedback.getContent());

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
