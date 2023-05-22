package maddori.keygo.service;

import lombok.RequiredArgsConstructor;
import maddori.keygo.common.exception.CustomException;
import maddori.keygo.common.response.ResponseCode;
import maddori.keygo.domain.ReflectionState;
import maddori.keygo.domain.entity.Reflection;
import maddori.keygo.repository.ReflectionRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReflectionStateValidator {

    private final ReflectionRepository reflectionRepository;

    public void validate(Long reflectionId, List<ReflectionState> state) {

        Reflection reflection = reflectionRepository.findById(reflectionId)
                .orElseThrow(() -> new CustomException(ResponseCode.GET_REFLECTION_FAIL));

        if (!state.contains(reflection.getState())) {
            throw new CustomException(ResponseCode.REFLECTION_STATUS_ERROR);
        }

    }
}
