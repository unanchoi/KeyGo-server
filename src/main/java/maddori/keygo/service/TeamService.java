package maddori.keygo.service;

import lombok.RequiredArgsConstructor;
import maddori.keygo.common.exception.CustomException;
import maddori.keygo.common.util.ImageHandler;
import maddori.keygo.domain.ReflectionState;
import maddori.keygo.domain.entity.Reflection;
import maddori.keygo.domain.entity.User;
import maddori.keygo.domain.entity.UserTeam;
import maddori.keygo.dto.team.CreateTeamRequestDto;
import maddori.keygo.dto.team.TeamNameResponseDto;
import maddori.keygo.dto.team.TeamRequestDto;
import maddori.keygo.dto.team.TeamResponseDto;
import maddori.keygo.domain.entity.Team;
import maddori.keygo.dto.user.UserTeamResponseDto;
import maddori.keygo.repository.ReflectionRepository;
import maddori.keygo.repository.TeamRepository;
import maddori.keygo.repository.UserRepository;
import maddori.keygo.repository.UserTeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Random;

import static maddori.keygo.common.response.ResponseCode.*;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final ReflectionRepository reflectionRepository;
    private final UserTeamRepository userTeamRepository;

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

    @Transactional(readOnly = true)
    public TeamNameResponseDto getCertainTeamName(String invitationCode) {
        Team team = teamRepository.findByInvitationCode(invitationCode).orElseThrow(() -> new CustomException(INVALID_INVITATION_CODE));
        TeamNameResponseDto response = TeamNameResponseDto.
                builder().id(team.getId())
                        .teamName(team.getTeamName())
                        .build();
        return response;
    }

    @Transactional
    public UserTeamResponseDto createTeamAndJoinTeam(Long userId, MultipartFile profileImage, CreateTeamRequestDto createTeamRequestDto) throws IOException {
        // 유저 정보
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_EXIST));
        // 팀 생성
        Team team = teamRepository.save(Team.builder()
                .teamName(createTeamRequestDto.getTeamName())
                .invitationCode(generateCode())
                .build());

        // 팀의 첫번째 회고 생성
        team.updateCurrentReflection(reflectionRepository.save(Reflection.builder()
                        .team(team)
                        .state(ReflectionState.SettingRequired)
                        .build()));
        teamRepository.save(team);

        // 프로필 이미지 업로드
        String profileImagePath = (profileImage == null) ? null : ImageHandler.imageUpload(profileImage);

        // userteam 테이블 업데이트
        UserTeam userTeam = userTeamRepository.save(UserTeam.builder()
                .user(user)
                .team(team)
                .nickname(createTeamRequestDto.getNickname())
                .role(createTeamRequestDto.getRole())
                .profileImagePath(profileImagePath)
                .build());

        UserTeamResponseDto response = UserTeamResponseDto.builder()
                .id(userTeam.getId())
                .nickname(userTeam.getNickname())
                .role(userTeam.getRole())
                .profileImagePath(profileImagePath)
                .userId(userTeam.getUser().getId())
                .team(TeamResponseDto.builder()
                        .id(team.getId())
                        .teamName(team.getTeamName())
                        .invitationCode(team.getInvitationCode())
                        .build())
                .build();

        return response;
    }

    // 팀 이름 업데이트
    @Transactional
    public TeamNameResponseDto editTeamName(Long teamId, TeamRequestDto teamRequestDto) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new CustomException(TEAM_NOT_EXIST));
        team.updateTeamName(teamRequestDto.getTeamName());
        teamRepository.save(team);

        return TeamNameResponseDto.
                builder().id(team.getId())
                .teamName(team.getTeamName())
                .build();
    }

    // 알파벳 대문자 + 숫자로 이루어진 랜덤 문자열 6자리 생성
    // reference: https://www.baeldung.com/java-random-string
    public String generateCode() {
        String generatedCode;
        int leftLimit = 48; // numeral '0'
        int rightLimit = 90; // letter 'Z'
        int targetStringLength = 6;
        Random random = new Random();

        do {
            generatedCode = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

        } while(teamRepository.findByInvitationCode(generatedCode).isPresent());

        return generatedCode;
    }
}
