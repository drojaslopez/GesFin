package com.gesfin.adapters.in.rest.dto.read;

import com.gesfin.domain.model.FamilyGroup;
import com.gesfin.domain.model.User;

import java.util.List;

public record FamilyGroupReadDto(
        Long id,
        String nombre,
        List<UserReadDto> miembros
) {
    public static FamilyGroupReadDto from(FamilyGroup grupo) {
        return new FamilyGroupReadDto(
                grupo.getId(),
                grupo.getNombre(),
                grupo.getMiembros().stream().map(UserReadDto::from).toList()
        );
    }
}