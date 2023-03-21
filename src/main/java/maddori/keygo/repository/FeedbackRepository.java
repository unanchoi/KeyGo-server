package maddori.keygo.repository;

import maddori.keygo.domain.CssType;
import maddori.keygo.domain.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    public void deleteById(Long id);

    public List<Feedback> findAllByTypeAndReflectionId(CssType type, Long reflectionId);
}
