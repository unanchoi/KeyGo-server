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
    @Query("update Feedback f set f.fromUser = null where f.fromUser.id = :userId")
    void fromUserSetNull(@Param("userId") Long userId);

    public List<Feedback> findAllByTypeAndReflectionId(CssType type, Long reflectionId);

    public List<Feedback> findAllByToUserIdAndFromUserIdAndReflectionId(Long memberId, Long userId, Long reflectionId);

    @Query("select f from Feedback f where f.toUser.id = :memberId and (f.fromUser = null or f.fromUser.id <> :userId) and f.reflection.id = :reflectionId")
    public List<Feedback> findAllByToUserExceptFromUserIdAndReflectionId(
            @Param("memberId") Long memberId,
            @Param("userId") Long userId,
            @Param("reflectionId") Long reflectionId);

    public List<Feedback> findAllByReflectionId(Long relfectionId);
}
