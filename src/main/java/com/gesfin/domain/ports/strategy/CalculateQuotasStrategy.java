package com.gesfin.domain.ports.strategy;

import com.gesfin.domain.model.MemberQuota;

import java.math.BigDecimal;
import java.util.List;

public interface CalculateQuotasStrategy {

    List<MemberQuota> calcularCuotas(BigDecimal montoObjetivo, List<Long> userIds);
}
