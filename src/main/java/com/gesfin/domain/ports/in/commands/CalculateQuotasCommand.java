package com.gesfin.domain.ports.in.commands;

import com.gesfin.domain.model.Goal;

public interface CalculateQuotasCommand {

    Goal calcularYAsignarCuotas(Long goalId);
}