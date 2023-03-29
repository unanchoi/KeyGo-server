package maddori.keygo.repository;

import maddori.keygo.domain.ReflectionState;
import maddori.keygo.domain.entity.Reflection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReflectionRepository extends JpaRepository<Reflection, Long> {

    public List<Reflection> findReflectionsByStateAndTeam_Id(ReflectionState state, Long teamId);

    public Optional<Reflection> findById(Long reflectionId);
}
