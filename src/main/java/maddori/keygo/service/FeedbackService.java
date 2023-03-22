package maddori.keygo.service;

import lombok.RequiredArgsConstructor;
import maddori.keygo.common.exception.CustomException;
import maddori.keygo.common.response.ResponseCode;
import maddori.keygo.domain.CssType;
import maddori.keygo.domain.entity.Feedback;
import maddori.keygo.dto.feedback.FeedbackResponseDto;
import maddori.keygo.dto.feedback.FeedbackUpdateRequestDto;
import maddori.keygo.dto.feedback.FeedbackUpdateResponseDto;
import maddori.keygo.dto.feedback.FeedbackUserAndTeamResponseDto;
import maddori.keygo.dto.user.UserDto;
import maddori.keygo.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

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

        UserDto userDto = UserDto.builder()
                .id(feedback.getToUser().getId())
                .nickName(feedback.getToUser().getUsername())
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

    public List<FeedbackResponseDto> getFeedbackList(String type, Long teamId, Long reflectionId) {
        return feedbackRepository.findAllByTypeAndReflectionId(toType(type), reflectionId)
                .stream()
                .map(feedback -> FeedbackResponseDto.builder()
                        .id(feedback.getId())
                        .type(feedback.getType().getValue())
                        .keyword(feedback.getKeyword())
                        .content(feedback.getContent())
                        .fromUser(UserDto.builder()
                                .id(feedback.getToUser().getId())
                                .nickName(feedback.getToUser().getUsername())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }

    // TODO: userID 맞춰서 수정.
    public FeedbackUserAndTeamResponseDto getUserAndTeamFeedbackList(
            Long userId,
            Long teamId,
            Long reflectionId,
            Long memberId
    ) {
        // 팀의 회고 중에서, 본인이 쓴 feedback
        List<FeedbackResponseDto> userFeedbackList =
        feedbackRepository.findAllByFromUserIdAndReflectionId(userId, reflectionId)
                .stream().map(feedback -> FeedbackResponseDto.builder()
                        .id(feedback.getId())
                        .type(feedback.getType().getValue())
                        .keyword(feedback.getKeyword())
                        .content(feedback.getContent())
                        .fromUser(UserDto.builder()
                                .id(feedback.getToUser().getId())
                                .nickName(feedback.getToUser().getUsername())
                                .build())
                        .build())
                .collect(Collectors.toList());


        // 팀의 회고 중에서, 본인을 제외한 팀의 피드백
        List<FeedbackResponseDto> teamFeedbackList =
                feedbackRepository.findAllExceptFromUserIdAndReflectionId(userId, reflectionId)
                        .stream().map(feedback -> FeedbackResponseDto.builder()
                                .id(feedback.getId())
                                .type(feedback.getType().getValue())
                                .keyword(feedback.getKeyword())
                                .content(feedback.getContent())
                                .fromUser(UserDto.builder()
                                        .id(feedback.getToUser().getId())
                                        .nickName(feedback.getToUser().getUsername())
                                        .build())
                                .build())
                        .collect(Collectors.toList());

        return FeedbackUserAndTeamResponseDto.builder()
                .category(category(userId, memberId))
                .userFeedbackList(userFeedbackList)
                .teamFeedbackList(teamFeedbackList)
                .build();
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


