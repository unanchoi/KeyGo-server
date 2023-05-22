package maddori.keygo.service;

import lombok.RequiredArgsConstructor;
import maddori.keygo.common.exception.CustomException;
import maddori.keygo.common.response.ResponseCode;
import maddori.keygo.domain.ReflectionState;
import maddori.keygo.domain.entity.Reflection;
import maddori.keygo.repository.ReflectionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static maddori.keygo.domain.ReflectionState.Progressing;

@Service
@RequiredArgsConstructor
public class ReflectionValidationService{

    private final ReflectionRepository reflectionRepository;

    public void validateState(Reflection reflection, List<ReflectionState> state) {
        if (!state.contains(reflection.getState())) {
            throw new CustomException(ResponseCode.REFLECTION_STATUS_ERROR);
        }
    }

    public void updateState(Reflection reflection) {
        if (reflection.getDate() == null) return;
        if (LocalDateTime.now().isAfter(reflection.getDate()) || LocalDateTime.now().isEqual(reflection.getDate())) {
            reflection.updateReflectionState(Progressing);
            reflectionRepository.save(reflection);
        }
    }

    public void validateRequestTime(LocalDateTime date) {
        if (LocalDateTime.now().isAfter(date)) {
            throw new CustomException(ResponseCode.UPDATE_REFLECTION_TIME_FAIL);
        }
    }
}
