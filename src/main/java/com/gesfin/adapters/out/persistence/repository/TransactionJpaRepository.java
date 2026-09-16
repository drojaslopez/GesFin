package com.gesfin.adapters.out.persistence.repository;

import com.gesfin.adapters.out.persistence.entity.TransactionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionJpaRepository extends JpaRepository<TransactionJpaEntity, Long> {

    List<TransactionJpaEntity> findByFamilyGroupId(Long familyGroupId);

    List<TransactionJpaEntity> findByUserId(Long userId);
}
