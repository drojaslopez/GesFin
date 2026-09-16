package com.gesfin.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Contribution {

    private Long id;
    private Long goalId;
    private Long userId;
    private BigDecimal monto;
    private LocalDate fecha;

    public Contribution() {}

    public Contribution(Long id, Long goalId, Long userId, BigDecimal monto, LocalDate fecha) {
        this.id = id;
        this.goalId = goalId;
        this.userId = userId;
        this.monto = monto;
        this.fecha = fecha;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getGoalId() { return goalId; }
    public void setGoalId(Long goalId) { this.goalId = goalId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}
