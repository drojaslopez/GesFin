package com.gesfin.domain.ports.in.commands;

import com.gesfin.domain.enums.Rol;
import com.gesfin.domain.model.User;

public interface CreateUserCommand {

    User crear(String nombre, String email, String password, Rol rol);
}