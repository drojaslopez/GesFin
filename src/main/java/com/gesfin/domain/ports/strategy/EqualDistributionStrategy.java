package com.gesfin.domain.ports.strategy;

import com.gesfin.domain.model.MemberQuota;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
public class EqualDistributionStrategy implements CalculateQuotasStrategy {

    @Override
    public List<MemberQuota> calcularCuotas(BigDecimal montoObjetivo, List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            throw new IllegalArgumentException("La lista de usuarios no puede estar vacía");
        }
        if (montoObjetivo == null || montoObjetivo.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto objetivo debe ser mayor a cero");
        }

        BigDecimal montoPorMiembro = montoObjetivo
                .divide(BigDecimal.valueOf(userIds.size()), 2, RoundingMode.HALF_UP);

        List<MemberQuota> cuotas = new ArrayList<>();
        for (Long userId : userIds) {
            cuotas.add(new MemberQuota(userId, montoPorMiembro));
        }
        return cuotas;
    }
}
