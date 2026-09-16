package com.gesfin.adapters.in.rest.dto.read;

import com.gesfin.domain.model.Contribution;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContributionReadDto(
        Long id,
        Long goalId,
        Long userId,
        BigDecimal monto,
        LocalDate fecha
) {
    public static ContributionReadDto from(Contribution c) {
        return new ContributionReadDto(c.getId(), c.getGoalId(), c.getUserId(), c.getMonto(), c.getFecha());
    }
}