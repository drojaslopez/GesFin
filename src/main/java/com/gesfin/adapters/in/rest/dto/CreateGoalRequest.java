package com.gesfin.adapters.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Solicitud para crear una meta de ahorro en un grupo familiar")
public record CreateGoalRequest(
        @Schema(description = "Identificador del grupo familiar al que pertenece la meta", example = "1")
        @NotNull Long familyGroupId,
        @Schema(description = "Descripción de la meta (ej. tipo de ahorro, destino)", example = "Viaje familiar a la costa")
        @NotBlank String descripcion,
        @Schema(description = "Monto total objetivo de la meta en la moneda local", example = "1200000.00")
        @NotNull @DecimalMin(value = "0.01") BigDecimal montoObjetivo,
        @Schema(description = "Fecha límite para cumplir la meta (formato YYYY-MM-DD)", example = "2027-03-15")
        LocalDate fechaLimite
) {}