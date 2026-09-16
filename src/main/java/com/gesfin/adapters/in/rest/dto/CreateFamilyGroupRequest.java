package com.gesfin.adapters.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Schema(description = "Solicitud para crear un grupo familiar")
public record CreateFamilyGroupRequest(
        @Schema(description = "Nombre del grupo familiar", example = "Familia Pérez")
        @NotBlank String nombre,
        @Schema(description = "Lista de identificadores de los usuarios que integrarán el grupo", example = "[1, 2, 3]")
        @NotEmpty List<Long> miembroIds
) {}