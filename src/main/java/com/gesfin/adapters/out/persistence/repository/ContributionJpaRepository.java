package com.gesfin.adapters.out.persistence.repository;

import com.gesfin.adapters.out.persistence.entity.ContributionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContributionJpaRepository extends JpaRepository<ContributionJpaEntity, Long> {

    List<ContributionJpaEntity> findByGoalId(Long goalId);

    List<ContributionJpaEntity> findByUserId(Long userId);
}
