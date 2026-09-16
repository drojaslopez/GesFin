package com.gesfin.application.queries;

import com.gesfin.domain.ports.in.queries.GetFamilyGroupByIdQuery;
import com.gesfin.domain.model.FamilyGroup;
import com.gesfin.domain.ports.out.FamilyGroupRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FamilyGroupQueryService implements GetFamilyGroupByIdQuery {

    private final FamilyGroupRepositoryPort familyGroupRepository;

    public FamilyGroupQueryService(FamilyGroupRepositoryPort familyGroupRepository) {
        this.familyGroupRepository = familyGroupRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public FamilyGroup buscar(Long id) {
        return familyGroupRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Grupo familiar no encontrado: " + id));
    }
}