package maddori.keygo.repository;

import maddori.keygo.domain.CssType;
import maddori.keygo.domain.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    public void deleteById(Long id);

    @Modifying(clearAutomatically = true)
    @Query(value = "update feedback f set f.from_id = null where f.from_id = :userId", nativeQuery = true)
    void fromUserSetNull(@Param("userId") Long userId);

    public List<Feedback> findAllByTypeAndReflectionId(CssType type, Long reflectionId);

    public List<Feedback> findAllByToUserAndFromUserIdAndReflectionId(Long memberId, Long userId, Long reflectionId);

    @Query("select f from Feedback f where f.toUser.id = :memberId and f.fromUser .id <> :userId and f.reflection.id = :reflectionId")
    public List<Feedback> findAllByToUserExceptFromUserIdAndReflectionId(
            @Param("memberId") Long memberId,
            @Param("userId") Long userId,
            @Param("reflectionId") Long reflectionId);
}
