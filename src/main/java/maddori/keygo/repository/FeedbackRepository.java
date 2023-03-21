package maddori.keygo.repository;

import maddori.keygo.domain.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    public void deleteById(Long id);
}
