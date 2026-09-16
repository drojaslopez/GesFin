package com.gesfin.application.queries;

import com.gesfin.domain.ports.in.queries.GetGoalByIdQuery;
import com.gesfin.domain.ports.in.queries.GetGoalsByFamilyGroupQuery;
import com.gesfin.domain.model.Goal;
import com.gesfin.domain.ports.out.GoalRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoalQueryService implements GetGoalByIdQuery, GetGoalsByFamilyGroupQuery {

    private final GoalRepositoryPort goalRepository;

    public GoalQueryService(GoalRepositoryPort goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Goal buscar(Long id) {
        return goalRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Meta no encontrada: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Goal> buscarPorFamilyGroupId(Long familyGroupId) {
        return goalRepository.buscarPorFamilyGroupId(familyGroupId);
    }
}