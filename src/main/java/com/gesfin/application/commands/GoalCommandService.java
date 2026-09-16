package com.gesfin.application.commands;

import com.gesfin.domain.ports.in.commands.CalculateQuotasCommand;
import com.gesfin.domain.ports.in.commands.CreateGoalCommand;
import com.gesfin.domain.model.FamilyGroup;
import com.gesfin.domain.model.Goal;
import com.gesfin.domain.model.MemberQuota;
import com.gesfin.domain.ports.out.FamilyGroupRepositoryPort;
import com.gesfin.domain.ports.out.GoalRepositoryPort;
import com.gesfin.domain.ports.strategy.CalculateQuotasStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class GoalCommandService implements CreateGoalCommand, CalculateQuotasCommand {

    private final GoalRepositoryPort goalRepository;
    private final FamilyGroupRepositoryPort familyGroupRepository;
    private final CalculateQuotasStrategy strategy;

    public GoalCommandService(GoalRepositoryPort goalRepository,
                              FamilyGroupRepositoryPort familyGroupRepository,
                              CalculateQuotasStrategy strategy) {
        this.goalRepository = goalRepository;
        this.familyGroupRepository = familyGroupRepository;
        this.strategy = strategy;
    }

    @Override
    @Transactional
    public Goal crear(Long familyGroupId, String descripcion, BigDecimal montoObjetivo, LocalDate fechaLimite) {
        FamilyGroup grupo = familyGroupRepository.buscarPorId(familyGroupId)
                .orElseThrow(() -> new IllegalArgumentException("Grupo familiar no encontrado: " + familyGroupId));

        List<Long> userIds = grupo.getMiembros().stream()
                .map(u -> u.getId())
                .toList();

        List<MemberQuota> cuotas = strategy.calcularCuotas(montoObjetivo, userIds);

        Goal goal = new Goal();
        goal.setFamilyGroupId(familyGroupId);
        goal.setDescripcion(descripcion);
        goal.setMontoObjetivo(montoObjetivo);
        goal.setFechaLimite(fechaLimite);
        goal.setCuotas(cuotas);

        return goalRepository.guardar(goal);
    }

    @Override
    @Transactional
    public Goal calcularYAsignarCuotas(Long goalId) {
        Goal goal = goalRepository.buscarPorId(goalId)
                .orElseThrow(() -> new IllegalArgumentException("Meta no encontrada: " + goalId));

        FamilyGroup grupo = familyGroupRepository.buscarPorId(goal.getFamilyGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Grupo familiar no encontrado: " + goal.getFamilyGroupId()));

        List<Long> userIds = grupo.getMiembros().stream()
                .map(u -> u.getId())
                .toList();

        List<MemberQuota> cuotas = strategy.calcularCuotas(goal.getMontoObjetivo(), userIds);
        goal.setCuotas(cuotas);

        return goalRepository.guardar(goal);
    }
}