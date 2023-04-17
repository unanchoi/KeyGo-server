package maddori.keygo.controller;

import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import maddori.keygo.common.response.BasicResponse;
import maddori.keygo.common.response.FailResponse;
import maddori.keygo.common.response.ResponseCode;
import maddori.keygo.common.response.SuccessResponse;
import maddori.keygo.dto.reflection.ReflectionResponseDto;
import maddori.keygo.dto.reflection.ReflectionUpdateRequestDto;
import maddori.keygo.dto.reflection.ReflectionUpdateResponseDto;
import maddori.keygo.service.ReflectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/teams")
public class ReflectionController {

    private final ReflectionService reflectionService;

    @GetMapping("/{teamId}/reflections")
    public ResponseEntity<? extends BasicResponse> getPastReflectionList(
            @PathVariable("teamId") Long teamId
    ) {
        List<ReflectionResponseDto> reflectionResponseDtoList = reflectionService.getPastReflectionList(teamId);
        ReflectionListResponseDto responseData = ReflectionListResponseDto.builder()
                .reflection(reflectionResponseDtoList)
                .build();

        return SuccessResponse.toResponseEntity(ResponseCode.GET_REFLECTION_LIST_SUCCESS, responseData);
    }

    @PatchMapping("{team_id}/reflections/{reflection_id}/end")
    public ResponseEntity<? extends BasicResponse> endInProgressReflection(
            @PathVariable("team_id") Long teamId,
            @PathVariable("reflection_id") Long reflectionId
    ) {
        ReflectionResponseDto responseData = reflectionService.endReflection(teamId, reflectionId);

        ReflectionListResponseDto responseDto = ReflectionListResponseDto.builder()
                .reflection(List.of(responseData))
                .build();

        return SuccessResponse.toResponseEntity(ResponseCode.END_REFLECTION_SUCCESS, responseDto);
    }

    @PatchMapping("{team_id}/reflections/{reflection_id}")
    public ResponseEntity<? extends BasicResponse> updateReflectionDetail(
            @PathVariable("team_id") Long teamId,
            @PathVariable("reflection_id") Long reflectionId,
            @Valid @RequestBody ReflectionUpdateRequestDto reflectionUpdateRequestDto
    ) {
        ReflectionUpdateResponseDto responseDto = reflectionService.updateReflectionDetail(teamId, reflectionId, reflectionUpdateRequestDto);
        return SuccessResponse.toResponseEntity(ResponseCode.UPDATE_REFLECTION_DETAIL_SUCCESS, responseDto);
    }

    @DeleteMapping("{team_id}/reflections/{reflection_id}")
    public ResponseEntity<? extends BasicResponse> deleteReflectionDetail(
            @PathVariable("team_id") Long teamId,
            @PathVariable("reflection_id") Long reflectionId
    ) {
        ReflectionResponseDto responseDto = reflectionService.deleteReflectionDetail(teamId, reflectionId);

        return SuccessResponse.toResponseEntity(ResponseCode.DELETE_REFLECTION_DETAIL_SUCCESS, responseDto);
    }

    @Data
    @Builder
    public static class ReflectionListResponseDto {
        private List<ReflectionResponseDto> reflection;
    }
}

