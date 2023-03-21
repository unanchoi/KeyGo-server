package maddori.keygo.controller;

import lombok.RequiredArgsConstructor;
import maddori.keygo.common.response.BasicResponse;
import maddori.keygo.common.response.FailResponse;
import maddori.keygo.common.response.ResponseCode;
import maddori.keygo.common.response.SuccessResponse;
import maddori.keygo.dto.feedback.FeedbackUpdateRequestDto;
import maddori.keygo.dto.feedback.FeedbackUpdateResponseDto;
import maddori.keygo.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/teams")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @DeleteMapping("/{teamId}/reflections/{reflectionId}/feedbacks/{feedbackId}")
    public ResponseEntity<? extends BasicResponse> deleteFeedback(
            @PathVariable("teamId") Long teamId,
            @PathVariable("reflectionId") Long reflectionId,
            @PathVariable("feedbackId") Long feedbackId
    ) {
        try {
            feedbackService.delete(teamId, reflectionId, feedbackId);
            return SuccessResponse.toResponseEntity(ResponseCode.DELETE_FEEDBACK_SUCCESS, null);
        } catch (RuntimeException e) {
            return FailResponse.toResponseEntity(ResponseCode.DELETE_FEEDBACK_NOT_EXIST);
        }
    }

    @PutMapping("/{teamId}/reflections/{reflectionId}/feedback/{feedbackId}")
    public ResponseEntity<? extends BasicResponse> updateFeedback(
            @PathVariable("teamId") Long teamId,
            @PathVariable("reflectionId") Long reflectionId,
            @PathVariable("feedbackId") Long feedbackId,
            @RequestBody FeedbackUpdateRequestDto feedbackUpdateRequestDto
    ) {
        try
        {
            FeedbackUpdateResponseDto responseDto = feedbackService.update(teamId, reflectionId, feedbackId, feedbackUpdateRequestDto);
            return SuccessResponse.toResponseEntity(ResponseCode.UPDATE_FEEDBACK_SUCCESS, responseDto);
        }
        catch (RuntimeException e) {
            return FailResponse.toResponseEntity(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
