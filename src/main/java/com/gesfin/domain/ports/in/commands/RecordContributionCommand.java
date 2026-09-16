package com.gesfin.domain.ports.in.commands;

import com.gesfin.domain.model.Contribution;

import java.math.BigDecimal;

public interface RecordContributionCommand {

    Contribution registrar(Long goalId, Long userId, BigDecimal monto);
}