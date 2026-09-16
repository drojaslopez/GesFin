package com.gesfin.adapters.out.persistence.adapter;

import com.gesfin.adapters.out.persistence.mapper.FamilyGroupEntityMapper;
import com.gesfin.adapters.out.persistence.repository.FamilyGroupJpaRepository;
import com.gesfin.domain.model.FamilyGroup;
import com.gesfin.domain.ports.out.FamilyGroupRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FamilyGroupRepositoryAdapter implements FamilyGroupRepositoryPort {

    private final FamilyGroupJpaRepository jpaRepository;
    private final FamilyGroupEntityMapper mapper = FamilyGroupEntityMapper.INSTANCE;

    public FamilyGroupRepositoryAdapter(FamilyGroupJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public FamilyGroup guardar(FamilyGroup familyGroup) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(familyGroup)));
    }

    @Override
    public Optional<FamilyGroup> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        jpaRepository.deleteById(id);
    }
}