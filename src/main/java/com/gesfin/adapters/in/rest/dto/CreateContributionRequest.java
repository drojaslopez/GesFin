package com.gesfin.adapters.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Solicitud para registrar un aporte de un integrante hacia una meta")
public record CreateContributionRequest(
        @Schema(description = "Identificador de la meta a la que se aporta", example = "1")
        @NotNull Long goalId,
        @Schema(description = "Identificador del integrante que realiza el aporte", example = "2")
        @NotNull Long userId,
        @Schema(description = "Monto aportado en la moneda local", example = "100000.00")
        @NotNull @DecimalMin(value = "0.01") BigDecimal monto
) {}