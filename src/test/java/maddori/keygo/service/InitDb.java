package maddori.keygo.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import maddori.keygo.domain.CssType;
import maddori.keygo.domain.ReflectionState;
import maddori.keygo.domain.entity.*;
import maddori.keygo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Ref;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitDb {

    @Autowired
    InitService initService;

    @PostConstruct
    public void init() {
        initService.doInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        @Autowired
        UserRepository userRepository;
        @Autowired
        TeamRepository teamRepository;
        @Autowired
        UserTeamRepository userTeamRepository;
        @Autowired
        ReflectionRepository reflectionRepository;
        @Autowired
        FeedbackRepository feedbackRepository;

        public void doInit(){
            User toUser = createToUser();
            User fromUser = createFromUser();
            Team team = createTeam();
            UserTeam toUserTeam = createUserTeam(toUser, team, 1L);
            UserTeam fromUserTeam = createUserTeam(fromUser, team, 2L);
            Reflection reflection = createReflection(team);
            createFeedback(team, reflection, toUser, fromUser);

        }

        private User createToUser() {
            User toUser =  User.builder()
                    .id(1L)
                    .username("to")
                    .email("adminto@admin.com")
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
            Team team = Team.builder()
                    .id(1L)
                    .teamName("team")
                    .invitationCode("abcdef")
                    .build();
            teamRepository.save(team);
            return team;
        }

        private UserTeam createUserTeam(User user, Team team, Long id) {
            UserTeam userTeam = UserTeam.builder()
                    .id(id)
                    .user(user)
                    .team(team)
                    .nickname("u" + id)
                    .build();
            userTeamRepository.save(userTeam);
            return userTeam;
        }

        private Reflection createReflection(Team team) {
            Reflection reflection = Reflection.builder()
                    .id(1L)
                    .date(LocalDateTime.now())
                    .reflectionName("맛쟁이 사자처럼 회고")
                    .state(ReflectionState.Before)
                    .team(team)
                    .build();
            reflectionRepository.save(reflection);
            return reflection;
        }

        private void createFeedback(Team team, Reflection reflection, User toUser, User fromUser) {
            for (int i = 0; i < 10; i++) {
                Feedback feedback = Feedback.builder()
                        .id((long) i)
                        .startContent("start content" + i)
                        .content("content " + i)
                        .toUser(toUser)
                        .fromUser(fromUser)
                        .type(CssType.Continue)
                        .team(team)
                        .reflection(reflection)
                        .keyword("keyword")
                        .build();
                feedbackRepository.save(feedback);
            }

        }
    }
}
