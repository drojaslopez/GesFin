package com.gesfin.domain.ports.out;

import com.gesfin.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {

    User guardar(User user);
    Optional<User> buscarPorId(Long id);
    Optional<User> buscarPorEmail(String email);
    List<User> buscarPorFamilyGroupId(Long familyGroupId);
    void eliminar(Long id);
    List<User> listarTodos();
}
