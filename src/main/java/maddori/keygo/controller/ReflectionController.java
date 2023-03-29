package maddori.keygo.controller;

import com.nimbusds.jose.shaded.gson.Gson;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import maddori.keygo.common.response.BasicResponse;
import maddori.keygo.common.response.FailResponse;
import maddori.keygo.common.response.ResponseCode;
import maddori.keygo.common.response.SuccessResponse;
import maddori.keygo.dto.reflection.ReflectionResponseDto;
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
        try {
            List<ReflectionResponseDto> reflectionResponseDtoList = reflectionService.getPastReflectionList(teamId);
            ReflectionListResponseDto responseData = ReflectionListResponseDto.builder()
                    .reflection(reflectionResponseDtoList)
                    .build();

            return SuccessResponse.toResponseEntity(ResponseCode.GET_REFLECTION_LIST_SUCCESS, responseData);
        }
        catch (RuntimeException e) {
            return FailResponse.toResponseEntity(ResponseCode.GET_REFLECTION_LIST_FAIL);
        }

    }

    @PatchMapping("{team_id}/reflections/{reflection_id}/end")
    public ResponseEntity<? extends BasicResponse> endInProgressReflection(
            @PathVariable("team_id") Long teamId,
            @PathVariable("reflection_id") Long reflectionId
    ) {
        ReflectionResponseDto responseDto = reflectionService.endReflection(teamId, reflectionId);
        return SuccessResponse.toResponseEntity(ResponseCode.END_REFLECTION_SUCCESS, responseDto);
    }

    @Data
    @Builder
    public static class ReflectionListResponseDto {
        private List<ReflectionResponseDto> reflection;
    }
}

