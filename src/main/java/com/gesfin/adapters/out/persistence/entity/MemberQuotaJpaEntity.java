package com.gesfin.adapters.out.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cuotas_miembro")
public class MemberQuotaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private BigDecimal montoAsignado;

    @Column(nullable = false)
    private BigDecimal montoAportado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public BigDecimal getMontoAsignado() { return montoAsignado; }
    public void setMontoAsignado(BigDecimal montoAsignado) { this.montoAsignado = montoAsignado; }

    public BigDecimal getMontoAportado() { return montoAportado; }
    public void setMontoAportado(BigDecimal montoAportado) { this.montoAportado = montoAportado; }
}
