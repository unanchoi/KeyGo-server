package maddori.keygo.service;

import lombok.RequiredArgsConstructor;
import maddori.keygo.common.exception.CustomException;
import maddori.keygo.common.response.ResponseCode;
import maddori.keygo.domain.CssType;
import maddori.keygo.domain.entity.Feedback;
import maddori.keygo.domain.entity.UserTeam;
import maddori.keygo.dto.feedback.FeedbackResponseDto;
import maddori.keygo.dto.feedback.FeedbackUpdateRequestDto;
import maddori.keygo.dto.feedback.FeedbackUpdateResponseDto;
import maddori.keygo.dto.user.UserDto;
import maddori.keygo.repository.FeedbackRepository;
import maddori.keygo.repository.UserTeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    private final UserTeamRepository userTeamRepository;

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
                .nickname(feedback.getToUser().getUsername())
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

    private CssType toType(String type) {
        CssType value = switch (type)
        {
            case "Continue" -> CssType.Continue;
            case "Stop" -> CssType.Stop;
            default -> throw new CustomException(ResponseCode.BAD_REQUEST);
        };
        return value;
    }
}


