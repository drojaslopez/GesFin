package com.gesfin.domain.ports.in.commands;

import com.gesfin.domain.model.Goal;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CreateGoalCommand {

    Goal crear(Long familyGroupId, String descripcion, BigDecimal montoObjetivo, LocalDate fechaLimite);
}