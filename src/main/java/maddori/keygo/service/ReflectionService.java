package maddori.keygo.service;

import lombok.RequiredArgsConstructor;
import maddori.keygo.domain.ReflectionState;
import maddori.keygo.domain.entity.Reflection;
import maddori.keygo.dto.reflection.ReflectionResponseDto;
import maddori.keygo.repository.ReflectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
