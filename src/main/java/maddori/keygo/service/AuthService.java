package maddori.keygo.service;

import lombok.RequiredArgsConstructor;
import maddori.keygo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
}
