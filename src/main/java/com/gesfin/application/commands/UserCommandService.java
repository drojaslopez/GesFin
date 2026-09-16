package com.gesfin.application.commands;

import com.gesfin.domain.ports.in.commands.CreateUserCommand;
import com.gesfin.domain.enums.Rol;
import com.gesfin.domain.model.User;
import com.gesfin.domain.ports.out.UserRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserCommandService implements CreateUserCommand {

    private final UserRepositoryPort userRepository;

    public UserCommandService(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User crear(String nombre, String email, String password, Rol rol) {
        userRepository.buscarPorEmail(email).ifPresent(existente -> {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + email);
        });

        User user = new User();
        user.setNombre(nombre);
        user.setEmail(email);
        user.setPassword(password);
        user.setRol(rol);

        return userRepository.guardar(user);
    }
}