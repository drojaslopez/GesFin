package com.gesfin.application.queries;

import com.gesfin.domain.ports.in.queries.GetUserByIdQuery;
import com.gesfin.domain.model.User;
import com.gesfin.domain.ports.out.UserRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserQueryService implements GetUserByIdQuery {

    private final UserRepositoryPort userRepository;

    public UserQueryService(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public User buscar(Long id) {
        return userRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));
    }
}