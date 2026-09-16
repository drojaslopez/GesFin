package com.gesfin.adapters.out.persistence.entity;

import com.gesfin.domain.enums.EstadoMeta;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "metas")
public class GoalJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long familyGroupId;

    private String descripcion;

    @Column(nullable = false)
    private BigDecimal montoObjetivo;

    private LocalDate fechaLimite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMeta estado;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "goal_id")
    private List<MemberQuotaJpaEntity> cuotas = new ArrayList<>();

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

    public List<MemberQuotaJpaEntity> getCuotas() { return cuotas; }
    public void setCuotas(List<MemberQuotaJpaEntity> cuotas) { this.cuotas = cuotas; }
}
