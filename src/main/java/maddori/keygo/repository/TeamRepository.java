package maddori.keygo.repository;

import maddori.keygo.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByInvitationCode(String invitationCode);
}
