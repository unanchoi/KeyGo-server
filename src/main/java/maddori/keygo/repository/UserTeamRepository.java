package maddori.keygo.repository;

import maddori.keygo.domain.entity.Team;
import maddori.keygo.domain.entity.User;
import maddori.keygo.domain.entity.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


import java.util.List;

public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {
    List<UserTeam> findUserTeamsByUserId(Long userId);

    Optional<UserTeam> findUserTeamByUserIdAndTeamId(Long userId, Long teamId);

}
