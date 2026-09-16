package com.gesfin.domain.model;

import com.gesfin.domain.enums.EstadoMeta;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Goal {

    private Long id;
    private Long familyGroupId;
    private String descripcion;
    private BigDecimal montoObjetivo;
    private LocalDate fechaLimite;
    private EstadoMeta estado;
    private List<MemberQuota> cuotas;

    public Goal() {
        this.cuotas = new ArrayList<>();
        this.estado = EstadoMeta.ACTIVA;
    }

    public Goal(Long id, Long familyGroupId, String descripcion,
                BigDecimal montoObjetivo, LocalDate fechaLimite) {
        this.id = id;
        this.familyGroupId = familyGroupId;
        this.descripcion = descripcion;
        this.montoObjetivo = montoObjetivo;
        this.fechaLimite = fechaLimite;
        this.estado = EstadoMeta.ACTIVA;
        this.cuotas = new ArrayList<>();
    }

    public BigDecimal calcularProgresoTotal() {
        if (montoObjetivo.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalAportado = cuotas.stream()
                .map(MemberQuota::getMontoAportado)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalAportado
                .multiply(BigDecimal.valueOf(100))
                .divide(montoObjetivo, 2, RoundingMode.HALF_UP);
    }

    public boolean estaCumplida() {
        return calcularProgresoTotal().compareTo(BigDecimal.valueOf(100)) >= 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getFamilyGroupId() { return familyGroupId; }
    public void setFamilyGroupId(Long familyGroupId) { this.familyGroupId = familyGroupId; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getMontoObjetivo() { return montoObjetivo; }
    public void setMontoObjetivo(BigDecimal montoObjetivo) { this.montoObjetivo = montoObjetivo; }

    public LocalDate getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(LocalDate fechaLimite) { this.fechaLimite = fechaLimite; }

    public EstadoMeta getEstado() { return estado; }
    public void setEstado(EstadoMeta estado) { this.estado = estado; }

    public List<MemberQuota> getCuotas() { return cuotas; }
    public void setCuotas(List<MemberQuota> cuotas) { this.cuotas = cuotas; }
}
