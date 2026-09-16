package com.gesfin.adapters.in.rest.dto;

import com.gesfin.domain.enums.TipoTransaccion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Solicitud para registrar una transacción (ingreso o gasto) del grupo familiar")
public record CreateTransactionRequest(
        @Schema(description = "Identificador del grupo familiar al que pertenece la transacción", example = "1")
        @NotNull Long familyGroupId,
        @Schema(description = "Identificador del integrante que realizó la transacción", example = "1")
        @NotNull Long userId,
        @Schema(description = "Monto de la transacción en la moneda local", example = "85000.00")
        @NotNull @DecimalMin(value = "0.01") BigDecimal monto,
        @Schema(description = "Tipo de transacción: INGRESO o GASTO", example = "GASTO")
        @NotNull TipoTransaccion tipo,
        @Schema(description = "Categoría del movimiento (ej. Supermercado, Transporte, Sueldo)", example = "Supermercado")
        @NotBlank String categoria,
        @Schema(description = "Fecha del movimiento (formato YYYY-MM-DD). Si no se envía, se usa la fecha actual", example = "2026-09-16")
        LocalDate fecha
) {}