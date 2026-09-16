package com.gesfin.adapters.in.rest.dto.read;

import com.gesfin.domain.enums.Rol;
import com.gesfin.domain.model.User;

public record UserReadDto(
        Long id,
        String nombre,
        String email,
        Rol rol,
        Long familyGroupId
) {
    public static UserReadDto from(User user) {
        return new UserReadDto(user.getId(), user.getNombre(), user.getEmail(), user.getRol(), user.getFamilyGroupId());
    }
}