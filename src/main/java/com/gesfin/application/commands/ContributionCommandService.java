package com.gesfin.application.commands;

import com.gesfin.domain.ports.in.commands.RecordContributionCommand;
import com.gesfin.domain.enums.EstadoMeta;
import com.gesfin.domain.model.Contribution;
import com.gesfin.domain.model.Goal;
import com.gesfin.domain.model.MemberQuota;
import com.gesfin.domain.ports.out.ContributionRepositoryPort;
import com.gesfin.domain.ports.out.GoalRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ContributionCommandService implements RecordContributionCommand {

    private final ContributionRepositoryPort contributionRepository;
    private final GoalRepositoryPort goalRepository;

    public ContributionCommandService(ContributionRepositoryPort contributionRepository,
                                      GoalRepositoryPort goalRepository) {
        this.contributionRepository = contributionRepository;
        this.goalRepository = goalRepository;
    }

    @Override
    @Transactional
    public Contribution registrar(Long goalId, Long userId, BigDecimal monto) {
        Goal goal = goalRepository.buscarPorId(goalId)
                .orElseThrow(() -> new IllegalArgumentException("Meta no encontrada: " + goalId));

        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del aporte debe ser mayor a cero");
        }

        MemberQuota cuota = goal.getCuotas().stream()
                .filter(c -> c.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("El usuario no tiene una cuota asignada en esta meta"));

        cuota.setMontoAportado(cuota.getMontoAportado().add(monto));

        if (goal.estaCumplida()) {
            goal.setEstado(EstadoMeta.CUMPLIDA);
        }
        goalRepository.guardar(goal);

        Contribution contribution = new Contribution();
        contribution.setGoalId(goalId);
        contribution.setUserId(userId);
        contribution.setMonto(monto);
        contribution.setFecha(LocalDate.now());

        return contributionRepository.guardar(contribution);
    }
}