package com.gesfin.domain.model;

import java.math.BigDecimal;

public class MemberQuota {

    private Long userId;
    private BigDecimal montoAsignado;
    private BigDecimal montoAportado;

    public MemberQuota() {
        this.montoAsignado = BigDecimal.ZERO;
        this.montoAportado = BigDecimal.ZERO;
    }

    public MemberQuota(Long userId, BigDecimal montoAsignado) {
        this.userId = userId;
        this.montoAsignado = montoAsignado;
        this.montoAportado = BigDecimal.ZERO;
    }

    public BigDecimal getMontoRestante() {
        return montoAsignado.subtract(montoAportado);
    }

    public boolean estaCumplida() {
        return montoAportado.compareTo(montoAsignado) >= 0;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public BigDecimal getMontoAsignado() { return montoAsignado; }
    public void setMontoAsignado(BigDecimal montoAsignado) { this.montoAsignado = montoAsignado; }

    public BigDecimal getMontoAportado() { return montoAportado; }
    public void setMontoAportado(BigDecimal montoAportado) { this.montoAportado = montoAportado; }
}
