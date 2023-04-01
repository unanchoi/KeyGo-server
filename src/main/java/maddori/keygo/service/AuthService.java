package maddori.keygo.service;

import lombok.RequiredArgsConstructor;
import maddori.keygo.common.exception.CustomException;
import maddori.keygo.repository.FeedbackRepository;
import maddori.keygo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static maddori.keygo.common.response.ResponseCode.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.delete(userRepository.findById(userId).orElseThrow(() ->
                                                    new CustomException(USER_NOT_EXIST)));
        feedbackRepository.fromUserSetNull(userId);
    }
}
