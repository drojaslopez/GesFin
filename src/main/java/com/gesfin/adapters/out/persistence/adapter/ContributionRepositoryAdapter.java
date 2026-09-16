package com.gesfin.adapters.out.persistence.adapter;

import com.gesfin.adapters.out.persistence.mapper.ContributionEntityMapper;
import com.gesfin.adapters.out.persistence.repository.ContributionJpaRepository;
import com.gesfin.domain.model.Contribution;
import com.gesfin.domain.ports.out.ContributionRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ContributionRepositoryAdapter implements ContributionRepositoryPort {

    private final ContributionJpaRepository jpaRepository;
    private final ContributionEntityMapper mapper = ContributionEntityMapper.INSTANCE;

    public ContributionRepositoryAdapter(ContributionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Contribution guardar(Contribution contribution) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(contribution)));
    }

    @Override
    public Optional<Contribution> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Contribution> buscarPorGoalId(Long goalId) {
        return jpaRepository.findByGoalId(goalId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Contribution> buscarPorUserId(Long userId) {
        return jpaRepository.findByUserId(userId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void eliminar(Long id) {
        jpaRepository.deleteById(id);
    }
}