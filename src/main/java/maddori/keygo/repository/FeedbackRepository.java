package maddori.keygo.repository;

import maddori.keygo.domain.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    public void deleteById(Long id);

    @Modifying(clearAutomatically = true)
    @Query(value = "update feedback f set f.from_id = null where f.from_id = :userId", nativeQuery = true)
    void fromUserSetNull(@Param("userId") Long userId);
}
