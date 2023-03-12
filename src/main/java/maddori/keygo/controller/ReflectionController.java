package maddori.keygo.controller;

import lombok.RequiredArgsConstructor;
import maddori.keygo.common.response.BasicResponse;
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
        List<ReflectionResponseDto> reflectionResponseDtoList = reflectionService.getPastReflectionList(teamId);
        return SuccessResponse.toResponseEntity(ResponseCode.GET_REFLECTION_LIST_SUCCESS, reflectionResponseDtoList);
    }
}
