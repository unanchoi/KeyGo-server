package maddori.keygo.service;

import lombok.RequiredArgsConstructor;
import maddori.keygo.common.exception.CustomException;
import maddori.keygo.common.response.ResponseCode;
import maddori.keygo.domain.CssType;
import maddori.keygo.domain.entity.Feedback;
import maddori.keygo.domain.entity.UserTeam;
import maddori.keygo.dto.feedback.FeedbackUpdateRequestDto;
import maddori.keygo.dto.feedback.FeedbackUpdateResponseDto;
import maddori.keygo.dto.user.UserDto;
import maddori.keygo.repository.FeedbackRepository;
import maddori.keygo.repository.UserTeamRepository;
import org.springframework.stereotype.Service;

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

        UserTeam userTeam  =  userTeamRepository.findUserTeamByUserIdAndTeamId(feedback.getToUser().getId(), TeamId)
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

    private CssType toType(String type) {
        CssType value = switch (type)
        {
            case "Continue" -> CssType.Continue;
            case "Stop" -> CssType.Stop;
            default -> CssType.Continue;
        };
        return value;
    }
}


