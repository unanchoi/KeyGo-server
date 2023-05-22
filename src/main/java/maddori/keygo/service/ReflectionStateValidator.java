package maddori.keygo.service;

import lombok.RequiredArgsConstructor;
import maddori.keygo.common.exception.CustomException;
import maddori.keygo.common.response.ResponseCode;
import maddori.keygo.domain.ReflectionState;
import maddori.keygo.domain.entity.Reflection;
import maddori.keygo.repository.ReflectionRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReflectionStateValidator {

    private final ReflectionRepository reflectionRepository;

    public void validate(Long reflectionId, ReflectionState state) {

        Reflection reflection = reflectionRepository.findById(reflectionId)
                .orElseThrow(() -> new CustomException(ResponseCode.GET_REFLECTION_FAIL));

        if (reflection.getState() != state) {
            throw new CustomException("회고 상태가 일치하지 않습니다.");
        }
    }
}
