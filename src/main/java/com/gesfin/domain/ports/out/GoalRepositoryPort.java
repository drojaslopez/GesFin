package com.gesfin.domain.ports.out;

import com.gesfin.domain.model.Goal;

import java.util.List;
import java.util.Optional;

public interface GoalRepositoryPort {

    Goal guardar(Goal goal);
    Optional<Goal> buscarPorId(Long id);
    List<Goal> buscarPorFamilyGroupId(Long familyGroupId);
    void eliminar(Long id);
}
