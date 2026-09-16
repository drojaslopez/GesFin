package com.gesfin.domain.ports.out;

import com.gesfin.domain.model.Contribution;

import java.util.List;
import java.util.Optional;

public interface ContributionRepositoryPort {

    Contribution guardar(Contribution contribution);
    Optional<Contribution> buscarPorId(Long id);
    List<Contribution> buscarPorGoalId(Long goalId);
    List<Contribution> buscarPorUserId(Long userId);
    void eliminar(Long id);
}
