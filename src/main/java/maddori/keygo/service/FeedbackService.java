package maddori.keygo.service;

import lombok.RequiredArgsConstructor;
import maddori.keygo.common.exception.CustomException;
import maddori.keygo.common.response.ResponseCode;
import maddori.keygo.domain.CssType;
import maddori.keygo.domain.entity.Feedback;
import maddori.keygo.domain.entity.Reflection;
import maddori.keygo.domain.entity.User;
import maddori.keygo.domain.entity.UserTeam;
import maddori.keygo.dto.feedback.*;
import maddori.keygo.dto.user.UserDto;
import maddori.keygo.repository.FeedbackRepository;
import maddori.keygo.repository.ReflectionRepository;
import maddori.keygo.repository.UserRepository;
import maddori.keygo.repository.UserTeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    private final UserTeamRepository userTeamRepository;

    private final UserRepository userRepository;

    private final ReflectionRepository reflectionRepository;

    public void delete(Long TeamId, Long reflectionId, Long feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }

    public FeedbackUpdateResponseDto update(Long TeamId, Long reflectionId, Long feedbackId, FeedbackUpdateRequestDto feedbackUpdateRequestDto) {
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
        User toUser = userRepository.findById(dto.getToId())
                .orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_EXIST));

        User fromUser = userRepository.findById(userId).get();

        Reflection reflection = reflectionRepository.findById(reflectionId).get();

        Feedback feedback = Feedback.builder()
                .type(toType(dto.getType()))
                .keyword(dto.getKeyword())
                .content(dto.getContent())
                .toUser(toUser)
                .fromUser(fromUser)
                .reflection(reflection)
                .build();
        feedbackRepository.save(feedback);

        UserTeam userTeam = userTeamRepository.findUserTeamsByUserIdAndTeamId(
                feedback.getToUser().getId(), teamId).get();


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

    private CssType toType(String type) {
        CssType value = switch (type)
        {
            case "Continue" -> CssType.Continue;
            case "Stop" -> CssType.Stop;
            default -> throw new CustomException(ResponseCode.BAD_REQUEST);
        };
        return value;
    }

    private String category(Long userId, Long memberId) {
        if (userId == memberId) {
            return "self";
        } else {
            return "others";
        }
    }
}


