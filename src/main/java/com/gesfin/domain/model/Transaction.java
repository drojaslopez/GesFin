package com.gesfin.domain.model;

import com.gesfin.domain.enums.TipoTransaccion;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {

    private Long id;
    private Long familyGroupId;
    private Long userId;
    private BigDecimal monto;
    private TipoTransaccion tipo;
    private String categoria;
    private LocalDate fecha;

    public Transaction() {}

    public Transaction(Long id, Long familyGroupId, Long userId, BigDecimal monto,
                       TipoTransaccion tipo, String categoria, LocalDate fecha) {
        this.id = id;
        this.familyGroupId = familyGroupId;
        this.userId = userId;
        this.monto = monto;
        this.tipo = tipo;
        this.categoria = categoria;
        this.fecha = fecha;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getFamilyGroupId() { return familyGroupId; }
    public void setFamilyGroupId(Long familyGroupId) { this.familyGroupId = familyGroupId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public TipoTransaccion getTipo() { return tipo; }
    public void setTipo(TipoTransaccion tipo) { this.tipo = tipo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}
