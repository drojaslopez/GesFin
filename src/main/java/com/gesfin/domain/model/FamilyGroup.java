package com.gesfin.domain.model;

import java.util.ArrayList;
import java.util.List;

public class FamilyGroup {

    private Long id;
    private String nombre;
    private List<User> miembros;

    public FamilyGroup() {
        this.miembros = new ArrayList<>();
    }

    public FamilyGroup(Long id, String nombre, List<User> miembros) {
        this.id = id;
        this.nombre = nombre;
        this.miembros = miembros != null ? miembros : new ArrayList<>();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<User> getMiembros() { return miembros; }
    public void setMiembros(List<User> miembros) { this.miembros = miembros; }

    public int getCantidadMiembros() { return miembros.size(); }
}
