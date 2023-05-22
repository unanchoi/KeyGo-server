package maddori.keygo.service;

import lombok.RequiredArgsConstructor;
import maddori.keygo.common.exception.CustomException;
import maddori.keygo.common.response.ResponseCode;
import maddori.keygo.domain.CssType;
import maddori.keygo.domain.ReflectionState;
import maddori.keygo.domain.entity.*;
import maddori.keygo.dto.feedback.*;
import maddori.keygo.dto.reflection.ReflectionResponseDto;
import maddori.keygo.dto.user.UserDto;
import maddori.keygo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final UserTeamRepository userTeamRepository;

    private final TeamRepository teamRepository;

    private final UserRepository userRepository;

    private final ReflectionRepository reflectionRepository;

    private final ReflectionStateValidator reflectionStateValidator;

    public void delete(Long TeamId, Long reflectionId, Long feedbackId) {
        reflectionStateValidator.validate(reflectionId, ReflectionState.SettingRequired);
        reflectionStateValidator.validate(reflectionId, ReflectionState.Before);
        feedbackRepository.deleteById(feedbackId);
    }

    @Transactional
    public FeedbackUpdateResponseDto update(Long TeamId, Long reflectionId, Long feedbackId, FeedbackUpdateRequestDto feedbackUpdateRequestDto) {
        reflectionStateValidator.validate(reflectionId, ReflectionState.SettingRequired);
        reflectionStateValidator.validate(reflectionId, ReflectionState.Before);
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new CustomException(ResponseCode.FEEDBACK_NOT_EXIST));

        feedback.updateFeedback(
                toType(feedbackUpdateRequestDto.getType()),
                feedbackUpdateRequestDto.getKeyword(),
                feedbackUpdateRequestDto.getContent()
        );
        feedbackRepository.save(feedback);

        UserTeam userTeam  =  userTeamRepository.findUserTeamsByUserIdAndTeamId(feedback.getToUser().getId(), TeamId)
                .orElseThrow(() -> new CustomException(ResponseCode.TEAM_NOT_EXIST));

        UserDto userDto = UserDto.builder()
                .id(feedback.getToUser().getId())
                .nickname(userTeam.getNickname())
                .build();

        FeedbackUpdateResponseDto feedbackUpdateResponseDto = FeedbackUpdateResponseDto.builder()
                .id(feedback.getId())
                .type(feedback.getType().getValue())
                .keyword(feedback.getKeyword())
                .content(feedback.getContent())
                .toUser(userDto)
                .build();
        return feedbackUpdateResponseDto;
    }

    @Transactional
    public List<FeedbackResponseDto> getFeedbackList(String type, Long teamId, Long reflectionId, Long userId) {

        UserTeam userTeam = userTeamRepository.findUserTeamsByUserIdAndTeamId(userId, teamId)
                .orElseThrow(() -> new CustomException(ResponseCode.TEAM_NOT_EXIST));

        return feedbackRepository.findAllByTypeAndReflectionId(toType(type), reflectionId)
                .stream()
                .map(feedback -> FeedbackResponseDto.builder()
                        .id(feedback.getId())
                        .type(feedback.getType().getValue())
                        .keyword(feedback.getKeyword())
                        .keyword(feedback.getKeyword())
                        .content(feedback.getContent())
                        .fromUser(UserDto.builder()
                                .id(feedback.getFromUser().getId())
                                .nickname(userTeamRepository.findUserTeamsByUserIdAndTeamId(feedback.getFromUser().getId(), teamId).get().getNickname())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }

    // TODO: userID 맞춰서 수정.
    public FeedbackUserAndTeamResponseDto getUserAndTeamFeedbackList(Long userId, Long teamId, Long reflectionId, Long memberId) {
        // 팀의 회고 중에서, 본인이 쓴 feedback
        reflectionStateValidator.validate(reflectionId, ReflectionState.Progressing);
        reflectionStateValidator.validate(reflectionId, ReflectionState.Done);

        UserTeam userTeam = userTeamRepository.findUserTeamsByUserIdAndTeamId(userId, teamId)
                .orElseThrow(() -> new CustomException(ResponseCode.TEAM_NOT_EXIST));

        List<FeedbackResponseDto> userFeedbackList =
        feedbackRepository.findAllByToUserIdAndFromUserIdAndReflectionId(memberId, userId, reflectionId)
                .stream().map(feedback -> FeedbackResponseDto.builder()
                        .id(feedback.getId())
                        .type(feedback.getType().getValue())
                        .keyword(feedback.getKeyword())
                        .content(feedback.getContent())
                        .fromUser(UserDto.builder()
                                .id(feedback.getFromUser().getId())
                                .nickname(userTeamRepository.findUserTeamsByUserIdAndTeamId(feedback.getFromUser().getId(), teamId).get().getNickname())
                                .build())
                        .build())
                .collect(Collectors.toList());


        // 팀의 회고 중에서, 본인을 제외한 팀의 피드백
        List<FeedbackResponseDto> teamFeedbackList =
                feedbackRepository.findAllByToUserExceptFromUserIdAndReflectionId(memberId, userId, reflectionId)
                        .stream().map(feedback -> FeedbackResponseDto.builder()
                                .id(feedback.getId())
                                .type(feedback.getType().getValue())
                                .keyword(feedback.getKeyword())
                                .content(feedback.getContent())
                                .fromUser(UserDto.builder()
                                        .id(feedback.getFromUser().getId())
                                        .nickname(userTeamRepository.findUserTeamsByUserIdAndTeamId(feedback.getFromUser().getId(), teamId).get().getNickname())
                                        .build())
                                .build())
                        .collect(Collectors.toList());

        return FeedbackUserAndTeamResponseDto.builder()
                .category(category(userId, memberId))
                .userFeedbackList(userFeedbackList)
                .teamFeedbackList(teamFeedbackList)
                .build();
    }

    @Transactional
    public FeedbackCreateResponseDto createFeedback(FeedbackCreateRequestDto dto, Long teamId, Long reflectionId, Long userId) {
        reflectionStateValidator.validate(reflectionId, ReflectionState.SettingRequired);
        reflectionStateValidator.validate(reflectionId, ReflectionState.Before);
        User toUser = getUserById(dto.getToId());
        User fromUser = getUserById(userId);
        Reflection reflection = getReflectionById(reflectionId);

        Feedback feedback = Feedback.builder()
                .type(toType(dto.getType()))
                .keyword(dto.getKeyword())
                .content(dto.getContent())
                .toUser(toUser)
                .team(getTeamById(teamId))
                .fromUser(fromUser)
                .reflection(reflection)
                .build();
        feedbackRepository.save(feedback);

        UserTeam userTeam = getUserTeamByUserIdAndTeamId(feedback.getToUser().getId(), teamId);

        FeedbackCreateResponseDto feedbackResponseDto = FeedbackCreateResponseDto.builder()
                .id(feedback.getId())
                .type(feedback.getType().getValue())
                .keyword(feedback.getKeyword())
                .content(feedback.getContent())
                .toUser(UserDto.builder()
                        .id(feedback.getToUser().getId())
                        .nickname(userTeam.getNickname())
                        .build())
                .build();
        return feedbackResponseDto;
    }

    @Transactional
    public FeedbackFromMeToMemberResponseDto getFromMeToMemberFeedbackList(Long userId, Long teamId, Long memberId) {
        UserTeam toUser = getUserTeamByUserIdAndTeamId(memberId, teamId);
        Reflection reflection = getReflectionById(getTeamById(teamId).getCurrentReflection().getId());
        reflectionStateValidator.validate(reflection.getId(), ReflectionState.Progressing);
        reflectionStateValidator.validate(reflection.getId(), ReflectionState.SettingRequired);
        reflectionStateValidator.validate(reflection.getId(), ReflectionState.Before);

        Map<CssType, List<FeedbackContentResponseDto>> feedbackContentByType =
                feedbackRepository.findAllByToUserIdAndFromUserIdAndReflectionId(memberId, userId, reflection.getId())
                        .stream()
                        .collect(Collectors.groupingBy(
                                Feedback::getType,
                                Collectors.mapping(
                                        feedback -> FeedbackContentResponseDto.builder()
                                                .id(feedback.getId())
                                                .keyword(feedback.getKeyword())
                                                .content(feedback.getContent())
                                                .build(),
                                        Collectors.toList()
                                )
                        ));

        return FeedbackFromMeToMemberResponseDto.builder()
                .toUser(UserDto.builder()
                        .id(toUser.getId())
                        .nickname(toUser.getNickname())
                        .build())
                .reflection(ReflectionResponseDto.builder()
                        .id(reflection.getId())
                        .reflectionName(reflection.getReflectionName())
                        .date(reflection.getDate())
                        .state(reflection.getState())
                        .teamId(reflection.getTeam().getId())
                        .build())
                .continueType(feedbackContentByType.getOrDefault(toType("Continue"), new ArrayList<>()))
                .stopType(feedbackContentByType.getOrDefault(toType("Stop"), new ArrayList<>()))
                .build();
    }


    private Reflection getReflectionById(Long reflectionId) {
        return reflectionRepository.findById(reflectionId)
                .orElseThrow(() -> new CustomException(ResponseCode.GET_REFLECTION_FAIL));
    }

    private UserTeam getUserTeamByUserIdAndTeamId(Long userId, Long teamId) {
        return userTeamRepository.findUserTeamsByUserIdAndTeamId(userId, teamId)
                .orElseThrow(() -> new CustomException(ResponseCode.TEAM_NOT_EXIST));
    }

    private User getUserById(Long userId) {
            return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_EXIST));
    }

    private CssType toType(String type) {
        CssType value = switch (type)
        {
            case "Continue" -> CssType.Continue;
            case "Stop" -> CssType.Stop;
            default -> throw new CustomException(ResponseCode.BAD_REQUEST);
        };
        return value;
    }

    private Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(
                () -> new CustomException(ResponseCode.TEAM_NOT_EXIST));
    }
    private String category(Long userId, Long memberId) {
        if (userId == memberId) {
            return "self";
        } else {
            return "others";
        }
    }
}


