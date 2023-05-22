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
import maddori.keygo.dto.reflection.ReflectionCurrentResponseDto;
import maddori.keygo.dto.reflection.ReflectionResponseDto;
import maddori.keygo.dto.reflection.ReflectionUpdateRequestDto;
import maddori.keygo.dto.reflection.ReflectionUpdateResponseDto;
import maddori.keygo.service.ReflectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        List<List<ReflectionResponseDto>> nestedReflectionResponseDtoList = new ArrayList<>();
        nestedReflectionResponseDtoList.add(reflectionResponseDtoList);
        ReflectionListResponseDto responseData = ReflectionListResponseDto.builder()
                .reflection(nestedReflectionResponseDtoList)
                .build();

        return SuccessResponse.toResponseEntity(ResponseCode.GET_REFLECTION_LIST_SUCCESS, responseData);
    }

    @PatchMapping("{team_id}/reflections/{reflection_id}/end")
    public ResponseEntity<? extends BasicResponse> endInProgressReflection(
            @PathVariable("team_id") Long teamId,
            @PathVariable("reflection_id") Long reflectionId
    ) {
        return SuccessResponse.toResponseEntity(ResponseCode.END_REFLECTION_SUCCESS, reflectionService.endReflection(teamId, reflectionId));
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

    @GetMapping("{team_id}/reflections/current")
    public ResponseEntity<? extends  BasicResponse> getCurrentReflectionDetail(
        @PathVariable("team_id") Long teamId){
        ReflectionCurrentResponseDto responseDto = reflectionService.getCurrentReflectionDetail(teamId);
        return SuccessResponse.toResponseEntity(ResponseCode.GET_CURRENT_REFLECTION_SUCCESS, responseDto);
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
        private List<List<ReflectionResponseDto>> reflection;
    }
}

