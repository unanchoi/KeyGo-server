package maddori.keygo.service;

import lombok.RequiredArgsConstructor;
import maddori.keygo.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    public void delete(Long TeamId, Long reflectionId, Long feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }

}


