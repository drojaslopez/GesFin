package com.gesfin.adapters.in.rest.dto;

import com.gesfin.domain.enums.Rol;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Solicitud para registrar un usuario en la plataforma")
public record CreateUserRequest(
        @Schema(description = "Nombre completo del usuario", example = "Daniel Pérez")
        @NotBlank String nombre,
        @Schema(description = "Correo electrónico único del usuario", example = "daniel@gesfin.com")
        @NotBlank @Email String email,
        @Schema(description = "Contraseña de acceso del usuario", example = "clave_segura_123")
        @NotBlank String password,
        @Schema(description = "Rol del usuario: ADMINISTRADOR (gestiona grupo y metas) o MIEMBRO (aporta y registra movimientos)", example = "ADMINISTRADOR")
        @NotNull Rol rol
) {}