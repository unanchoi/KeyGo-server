package maddori.keygo.service;

import lombok.RequiredArgsConstructor;
import maddori.keygo.common.exception.CustomException;
import maddori.keygo.common.util.ImageHandler;
import maddori.keygo.domain.entity.Team;
import maddori.keygo.domain.entity.User;
import maddori.keygo.domain.entity.UserTeam;
import maddori.keygo.dto.team.TeamResponseDto;
import maddori.keygo.dto.user.UserTeamListResponseDto;
import maddori.keygo.dto.user.UserTeamRequestDto;
import maddori.keygo.dto.user.UserTeamResponseDto;
import maddori.keygo.repository.TeamRepository;
import maddori.keygo.repository.UserRepository;
import maddori.keygo.repository.UserTeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static maddori.keygo.common.response.ResponseCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserTeamRepository userTeamRepository;
    private final TeamRepository teamRepository;
    private final ImageHandler imageHandler;

    @Transactional(readOnly = true)
    public List<UserTeamListResponseDto> getUserTeamList(Long userId) {
        List<UserTeam> userTeamList= userTeamRepository.findUserTeamsByUserId(userId);
        List<UserTeamListResponseDto> result = userTeamList.stream()
                        .map(r -> UserTeamListResponseDto.builder()
                                        .id(r.getTeam().getId())
                                        .teamName(r.getTeam().getTeamName())
                                        .build())
                        .collect(Collectors.toList());

        return result;
    }

    @Transactional
    public UserTeamResponseDto userJoinTeam(Long userId, Long teamId, MultipartFile profileImage, UserTeamRequestDto requestDto) throws IOException {
        // 유저 정보
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_EXIST));
        // 팀의 존재 여부 체크
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new CustomException(TEAM_NOT_EXIST));
        // 이미 합류한 팀인지 체크
        if (userTeamRepository.findUserTeamsByUserIdAndTeamId(userId, teamId).isPresent()) throw new CustomException(ALREADY_TEAM_MEMBER);

        String profileImagePath = (profileImage == null) ? null : imageHandler.imageUploadS3(profileImage);

        // userteam 테이블 업데이트
        UserTeam userTeam = userTeamRepository.save(UserTeam.builder()
                        .user(user)
                        .team(team)
                        .nickname(requestDto.getNickname())
                        .role(requestDto.getRole())
                        .profileImagePath(profileImagePath)
                        .build());

        return UserTeamResponseDto.builder()
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
    }


    @Transactional
    public void userLeaveTeam(Long userId, Long teamId) {
        // 프로필 이미지 삭제
        imageHandler.imageDeleteS3(userTeamRepository.findUserTeamsByUserIdAndTeamId(userId, teamId).get().getProfileImagePath());
        // userteam에서 데이터 삭제
        userTeamRepository.deleteByUserIdAndTeamId(userId, teamId);
    }

    public UserTeamResponseDto editProfile(Long userId, Long teamId, MultipartFile profileImage, UserTeamRequestDto requestDto) throws IOException {
        // 유저 정보
        userRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_EXIST));
        // 팀의 존재 여부 체크
        teamRepository.findById(teamId).orElseThrow(() -> new CustomException(TEAM_NOT_EXIST));
        // 유저가 팀에 속해있는지 체크
        UserTeam userTeam = userTeamRepository.findUserTeamsByUserIdAndTeamId(userId, teamId).orElseThrow(() -> new CustomException(USER_NOT_TEAM_MEMBER));

        // 이미지, 닉네임, 역할 변경사항 존재한다면 업데이트
        if (profileImage != null) {
            if (userTeam.getProfileImagePath() != null) imageHandler.imageDeleteS3(userTeam.getProfileImagePath());
            userTeam.updateProfileImagePath(imageHandler.imageUploadS3(profileImage));
        }
        if (requestDto.getNickname() != null) {
            userTeam.updateNickname(requestDto.getNickname());
        }
        if (requestDto.getRole() != null) {
            userTeam.updateRole(requestDto.getRole());
        }
        userTeamRepository.save(userTeam);

        return UserTeamResponseDto.builder()
                .id(userId)
                .nickname(userTeam.getNickname())
                .role(userTeam.getRole())
                .profileImagePath(userTeam.getProfileImagePath())
                .build();
    }
}
