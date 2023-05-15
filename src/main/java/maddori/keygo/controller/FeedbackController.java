package maddori.keygo.controller;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import maddori.keygo.common.response.BasicResponse;
import maddori.keygo.common.response.FailResponse;
import maddori.keygo.common.response.ResponseCode;
import maddori.keygo.common.response.SuccessResponse;
import maddori.keygo.dto.feedback.*;
import maddori.keygo.dto.reflection.ReflectionResponseDto;
import maddori.keygo.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/teams")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping( "{teamId}/reflections/{reflectionId}/feedbacks")
    public ResponseEntity<? extends BasicResponse> createFeedback(
            @PathVariable("teamId") Long teamId,
            @PathVariable("reflectionId") Long reflectionId,
            @Valid @RequestBody FeedbackCreateRequestDto feedbackCreateRequestDto,
            @RequestHeader Long userId
    ) {
        FeedbackCreateResponseDto responseDto = feedbackService.createFeedback(feedbackCreateRequestDto, teamId, reflectionId, userId);
        return SuccessResponse.toResponseEntity(ResponseCode.CREATE_FEEDBACK_SUCCESS, responseDto);
    }

    @DeleteMapping("/{teamId}/reflections/{reflectionId}/feedbacks/{feedbackId}")
    public ResponseEntity<? extends BasicResponse> deleteFeedback(
            @PathVariable("teamId") Long teamId,
            @PathVariable("reflectionId") Long reflectionId,
            @PathVariable("feedbackId") Long feedbackId
    ) {
        feedbackService.delete(teamId, reflectionId, feedbackId);
        return SuccessResponse.toResponseEntity(ResponseCode.DELETE_FEEDBACK_SUCCESS, null);

    }

    @PutMapping("/{teamId}/reflections/{reflectionId}/feedbacks/{feedbackId}")
    public ResponseEntity<? extends BasicResponse> updateFeedback(
            @PathVariable("teamId") Long teamId,
            @PathVariable("reflectionId") Long reflectionId,
            @PathVariable("feedbackId") Long feedbackId,
            @Valid @RequestBody FeedbackUpdateRequestDto feedbackUpdateRequestDto
    ) {
        FeedbackUpdateResponseDto responseDto = feedbackService.update(teamId, reflectionId, feedbackId, feedbackUpdateRequestDto);
        return SuccessResponse.toResponseEntity(ResponseCode.UPDATE_FEEDBACK_SUCCESS, responseDto);
    }

    @GetMapping("/{teamId}/reflections/{reflection_id}/feedbacks")
    public ResponseEntity<? extends BasicResponse> getCertainTypeFeedbackAll(
            @RequestParam("type") String type,
            @PathVariable("teamId") Long teamId,
            @PathVariable("reflection_id") Long reflectionId,
            Long userId
    ) {
        List<FeedbackResponseDto> responseDtoList = feedbackService.getFeedbackList(type, teamId, reflectionId, userId);
        FeedbackListResponseDto responseData = FeedbackListResponseDto.builder()
                .feedback(responseDtoList)
                .build();
        return SuccessResponse.toResponseEntity(ResponseCode.GET_FEEDBACK_SUCCESS, responseData);
    }

    @GetMapping("/{teamId}/reflections/{reflectionId}/feedbacks/from-team")
    public ResponseEntity<? extends BasicResponse> getTeamAndUserFeedback(
            Long userId,
            @RequestParam("members") Long memberId,
            @PathVariable("teamId") Long teamId,
            @PathVariable("reflectionId") Long reflectionId
    ) {
        FeedbackUserAndTeamResponseDto responseDto =
                feedbackService.getUserAndTeamFeedbackList(userId, teamId, reflectionId, memberId);
        return SuccessResponse.toResponseEntity(ResponseCode.GET_FEEDBACK_SUCCESS, responseDto);
    }

    @Data
    @Builder
    public static class FeedbackListResponseDto {
        private List<FeedbackResponseDto> feedback;
    }
}
