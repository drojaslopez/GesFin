package com.gesfin.adapters.in.rest.dto.read;

import com.gesfin.domain.enums.EstadoMeta;
import com.gesfin.domain.model.Goal;
import com.gesfin.domain.model.MemberQuota;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record GoalReadDto(
        Long id,
        Long familyGroupId,
        String descripcion,
        BigDecimal montoObjetivo,
        LocalDate fechaLimite,
        EstadoMeta estado,
        BigDecimal progreso,
        List<QuotaReadDto> cuotas
) {
    public record QuotaReadDto(Long userId, BigDecimal montoAsignado, BigDecimal montoAportado, BigDecimal montoRestante) {
        public static QuotaReadDto from(MemberQuota cuota) {
            return new QuotaReadDto(cuota.getUserId(), cuota.getMontoAsignado(), cuota.getMontoAportado(), cuota.getMontoRestante());
        }
    }

    public static GoalReadDto from(Goal goal) {
        return new GoalReadDto(
                goal.getId(),
                goal.getFamilyGroupId(),
                goal.getDescripcion(),
                goal.getMontoObjetivo(),
                goal.getFechaLimite(),
                goal.getEstado(),
                goal.calcularProgresoTotal(),
                goal.getCuotas().stream().map(QuotaReadDto::from).toList()
        );
    }
}