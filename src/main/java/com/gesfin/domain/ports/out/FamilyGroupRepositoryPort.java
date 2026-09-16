package com.gesfin.domain.ports.out;

import com.gesfin.domain.model.FamilyGroup;

import java.util.Optional;

public interface FamilyGroupRepositoryPort {

    FamilyGroup guardar(FamilyGroup familyGroup);
    Optional<FamilyGroup> buscarPorId(Long id);
    void eliminar(Long id);
}