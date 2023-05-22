package maddori.keygo.service;

import lombok.RequiredArgsConstructor;
import maddori.keygo.common.exception.CustomException;
import maddori.keygo.common.response.ResponseCode;
import maddori.keygo.domain.ReflectionState;
import maddori.keygo.domain.entity.Feedback;
import maddori.keygo.domain.entity.Reflection;
import maddori.keygo.domain.entity.Team;
import maddori.keygo.dto.reflection.ReflectionCurrentResponseDto;
import maddori.keygo.dto.reflection.ReflectionResponseDto;
import maddori.keygo.dto.reflection.ReflectionUpdateRequestDto;
import maddori.keygo.dto.reflection.ReflectionUpdateResponseDto;
import maddori.keygo.repository.FeedbackRepository;
import maddori.keygo.repository.ReflectionRepository;
import maddori.keygo.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReflectionService {

    private final ReflectionRepository reflectionRepository;

    private final FeedbackRepository feedbackRepository;

    private final TeamRepository teamRepository;

    private final ReflectionStateValidator reflectionStateValidator;

    @Transactional(readOnly = true)
    public List<ReflectionResponseDto> getPastReflectionList(Long teamId) {

        List<Reflection> reflectionList = reflectionRepository.findReflectionsByStateAndTeam_Id(ReflectionState.Done, teamId);
        List<ReflectionResponseDto> result = reflectionList.stream().map(r -> ReflectionResponseDto.builder().id(r.getId()).reflectionName(r.getReflectionName()).date(r.getDate()).state(r.getState()).teamId(r.getTeam().getId()).build()).collect(Collectors.toList());

        return result;
    }

    @Transactional
    public ReflectionResponseDto endReflection(Long teamId, Long reflectionId) {
        reflectionStateValidator.validate(reflectionId, ReflectionState.Progressing);
        Reflection reflection = reflectionRepository.findById(reflectionId).orElseThrow(() -> new CustomException(ResponseCode.GET_REFLECTION_FAIL));

        reflection.updateReflectionState(ReflectionState.Done);

        Reflection nextReflection = Reflection.builder().team(reflection.getTeam()).state(ReflectionState.SettingRequired).build();

        Team team = reflection.getTeam();
        team.updateRecentReflection(reflection);
        team.updateCurrentReflection(nextReflection);

        return ReflectionResponseDto.builder().id(reflection.getId()).reflectionName(reflection.getReflectionName()).date(reflection.getDate()).state(reflection.getState()).teamId(reflection.getTeam().getId()).build();
    }

    @Transactional
    public ReflectionUpdateResponseDto updateReflectionDetail(Long teamId, Long reflectionId, ReflectionUpdateRequestDto requestDto) {
        reflectionStateValidator.validate(reflectionId, ReflectionState.SettingRequired);
        reflectionStateValidator.validate(reflectionId, ReflectionState.Before);
        Reflection reflection = reflectionRepository.findById(reflectionId).orElseThrow(() -> new CustomException(ResponseCode.GET_REFLECTION_FAIL));
        reflection.updateReflectionName(requestDto.getReflectionName());
        reflection.updateReflectionDate(requestDto.getReflectionDate());
        reflectionRepository.save(reflection);

        return ReflectionUpdateResponseDto.builder().id(reflection.getId()).reflectionName(reflection.getReflectionName()).reflectionDate(reflection.getDate()).reflectionState(reflection.getState().toString()).teamId(reflection.getTeam().getId()).build();
    }

    @Transactional
    public ReflectionCurrentResponseDto getCurrentReflectionDetail(Long teamId) {
        Team team = teamRepository.findById(teamId).get();
        Long currentReflectionId = team.getCurrentReflection().getId();

        Reflection reflection = reflectionRepository.findById(currentReflectionId).orElseThrow(() -> new CustomException(ResponseCode.GET_REFLECTION_FAIL));

        List<String> keywordList = new ArrayList<>();

        List<Feedback> feedbackList = feedbackRepository.findAllByReflectionId(reflection.getId());
        for (int i = 0; i < feedbackList.size(); i++) {
            keywordList.add(feedbackList.get(i).getKeyword());
        }

        return ReflectionCurrentResponseDto.builder().id(reflection.getId()).reflectionName(reflection.getReflectionName()).reflectionDate(reflection.getDate()).reflectionStatus(reflection.getState().toString()).reflectionKeywords(keywordList).build();
    }

    public ReflectionResponseDto deleteReflectionDetail(Long reflectionId, Long teamId) {
        reflectionStateValidator.validate(reflectionId, ReflectionState.SettingRequired);
        reflectionStateValidator.validate(reflectionId, ReflectionState.Before);
        Reflection reflection = reflectionRepository.findById(reflectionId).orElseThrow(() -> new CustomException(ResponseCode.GET_REFLECTION_FAIL));

        reflection.deleteInfo();
        reflectionRepository.save(reflection);
        return ReflectionResponseDto.builder().id(reflection.getId()).reflectionName(reflection.getReflectionName()).date(reflection.getDate()).state(reflection.getState()).teamId(teamId).build();
    }
}
