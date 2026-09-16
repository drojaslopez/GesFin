package com.gesfin.adapters.out.persistence.adapter;

import com.gesfin.adapters.out.persistence.mapper.GoalEntityMapper;
import com.gesfin.adapters.out.persistence.repository.GoalJpaRepository;
import com.gesfin.domain.model.Goal;
import com.gesfin.domain.ports.out.GoalRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GoalRepositoryAdapter implements GoalRepositoryPort {

    private final GoalJpaRepository jpaRepository;
    private final GoalEntityMapper mapper = GoalEntityMapper.INSTANCE;

    public GoalRepositoryAdapter(GoalJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Goal guardar(Goal goal) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(goal)));
    }

    @Override
    public Optional<Goal> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Goal> buscarPorFamilyGroupId(Long familyGroupId) {
        return jpaRepository.findByFamilyGroupId(familyGroupId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void eliminar(Long id) {
        jpaRepository.deleteById(id);
    }
}