package com.gesfin.adapters.in.rest.dto.read;

import com.gesfin.domain.enums.TipoTransaccion;
import com.gesfin.domain.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionReadDto(
        Long id,
        Long familyGroupId,
        Long userId,
        BigDecimal monto,
        TipoTransaccion tipo,
        String categoria,
        LocalDate fecha
) {
    public static TransactionReadDto from(Transaction t) {
        return new TransactionReadDto(t.getId(), t.getFamilyGroupId(), t.getUserId(), t.getMonto(), t.getTipo(), t.getCategoria(), t.getFecha());
    }
}