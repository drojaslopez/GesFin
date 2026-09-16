package com.gesfin.domain.ports.in.commands;

import com.gesfin.domain.enums.TipoTransaccion;
import com.gesfin.domain.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RegisterTransactionCommand {

    Transaction registrar(Long familyGroupId, Long userId, BigDecimal monto,
                          TipoTransaccion tipo, String categoria, LocalDate fecha);
}