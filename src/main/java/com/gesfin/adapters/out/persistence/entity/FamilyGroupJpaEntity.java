package com.gesfin.adapters.out.persistence.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grupos_familiares")
public class FamilyGroupJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "familyGroup")
    private List<UserJpaEntity> miembros = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<UserJpaEntity> getMiembros() { return miembros; }
    public void setMiembros(List<UserJpaEntity> miembros) { this.miembros = miembros; }
}
