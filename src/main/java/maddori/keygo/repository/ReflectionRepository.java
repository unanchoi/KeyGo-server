package maddori.keygo.repository;

import maddori.keygo.domain.entity.Reflection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReflectionRepository extends JpaRepository<Reflection, Long> {

    public List<Reflection> findReflectionsByTeam_Id(Long teamId);
}
