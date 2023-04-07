package maddori.keygo.service;

import lombok.RequiredArgsConstructor;
import maddori.keygo.common.exception.CustomException;
import maddori.keygo.common.response.ResponseCode;
import maddori.keygo.domain.ReflectionState;
import maddori.keygo.domain.entity.Reflection;
import maddori.keygo.domain.entity.Team;
import maddori.keygo.dto.reflection.ReflectionResponseDto;
import maddori.keygo.dto.reflection.ReflectionUpdateRequestDto;
import maddori.keygo.dto.reflection.ReflectionUpdateResponseDto;
import maddori.keygo.repository.ReflectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.sql.Ref;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReflectionService {

    private final ReflectionRepository reflectionRepository;

    @Transactional(readOnly = true)
    public List<ReflectionResponseDto> getPastReflectionList(Long teamId) {

        List<Reflection> reflectionList = reflectionRepository.findReflectionsByStateAndTeam_Id( ReflectionState.Done, teamId);
        List<ReflectionResponseDto> result = reflectionList.stream()
                .map(r -> ReflectionResponseDto.builder()
                        .id(r.getId())
                        .reflectionName(r.getReflectionName())
                        .date(r.getDate())
                        .state(r.getState())
                        .teamId(r.getTeam().getId())
                        .build())
                .collect(Collectors.toList());

        return result;
    }

    @Transactional
    public ReflectionResponseDto endReflection(Long teamId, Long reflectionId) {
        Reflection reflection = reflectionRepository.findById(reflectionId)
                .orElseThrow(
                () -> new CustomException(ResponseCode.GET_REFLECTION_FAIL));

        reflection.endReflection();

        Reflection nextReflection = Reflection.builder()
                .team(reflection.getTeam())
                .state(ReflectionState.SettingRequired)
                .build();

        Team team = reflection.getTeam();
        team.updateRecentReflection(reflection);
        team.updateCurrentReflection(nextReflection);

        return ReflectionResponseDto.builder()
                .id(reflection.getId())
                .reflectionName(reflection.getReflectionName())
                .date(reflection.getDate())
                .state(reflection.getState())
                .teamId(reflection.getTeam().getId())
                .build();
    }


    public ReflectionUpdateResponseDto updateReflectionDetail(Long teamId, Long reflectionId, ReflectionUpdateRequestDto requestDto) {
        Reflection reflection =  reflectionRepository.findById(reflectionId)
                .orElseThrow(() -> new CustomException(ResponseCode.GET_REFLECTION_FAIL));
        reflection.updateReflectionName(requestDto.getReflectionName());
        reflection.updateReflectionDate(requestDto.getReflectionDate());
        reflectionRepository.save(reflection);

        return ReflectionUpdateResponseDto.builder()
                .id(reflection.getId())
                .reflectionName(reflection.getReflectionName())
                .reflectionDate(reflection.getDate().toString())
                .reflectionState(reflection.getState().toString())
                .teamId(reflection.getTeam().getId())
                .build();
    }

    @Transactional
    public ReflectionResponseDto deleteReflectionDetail(Long reflectionId, Long teamId) {

        Reflection reflection = reflectionRepository.findById(reflectionId)
                .orElseThrow(
                        () -> new CustomException(ResponseCode.GET_REFLECTION_FAIL));

        reflection.deleteInfo();
        reflectionRepository.save(reflection);
        return ReflectionResponseDto.builder()
                .id(reflection.getId())
                .reflectionName(reflection.getReflectionName())
                .date(reflection.getDate())
                .state(reflection.getState())
                .teamId(teamId)
                .build();
    }

}
