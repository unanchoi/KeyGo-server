package maddori.keygo.service;

import lombok.RequiredArgsConstructor;
import maddori.keygo.repository.ReflectionRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReflectionStateValidator {

    private final ReflectionRepository reflectionRepository;
}
