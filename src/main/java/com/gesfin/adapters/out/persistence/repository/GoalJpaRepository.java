package com.gesfin.adapters.out.persistence.repository;

import com.gesfin.adapters.out.persistence.entity.GoalJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalJpaRepository extends JpaRepository<GoalJpaEntity, Long> {

    List<GoalJpaEntity> findByFamilyGroupId(Long familyGroupId);
}
