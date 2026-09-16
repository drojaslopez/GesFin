package com.gesfin.domain.model;

import com.gesfin.domain.enums.Rol;

public class User {

    private Long id;
    private String nombre;
    private String email;
    private String password;
    private Rol rol;
    private Long familyGroupId;

    public User() {}

    public User(Long id, String nombre, String email, String password, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public Long getFamilyGroupId() { return familyGroupId; }
    public void setFamilyGroupId(Long familyGroupId) { this.familyGroupId = familyGroupId; }
}
