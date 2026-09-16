package com.gesfin.application.commands;

import com.gesfin.domain.ports.in.commands.CreateFamilyGroupCommand;
import com.gesfin.domain.model.FamilyGroup;
import com.gesfin.domain.model.User;
import com.gesfin.domain.ports.out.FamilyGroupRepositoryPort;
import com.gesfin.domain.ports.out.UserRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FamilyGroupCommandService implements CreateFamilyGroupCommand {

    private final FamilyGroupRepositoryPort familyGroupRepository;
    private final UserRepositoryPort userRepository;

    public FamilyGroupCommandService(FamilyGroupRepositoryPort familyGroupRepository,
                                     UserRepositoryPort userRepository) {
        this.familyGroupRepository = familyGroupRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public FamilyGroup crear(String nombre, List<Long> miembroIds) {
        FamilyGroup grupo = new FamilyGroup();
        grupo.setNombre(nombre);

        List<User> miembros = new ArrayList<>();
        for (Long id : miembroIds) {
            userRepository.buscarPorId(id).ifPresent(miembros::add);
        }

        FamilyGroup guardado = familyGroupRepository.guardar(grupo);

        for (User miembro : miembros) {
            miembro.setFamilyGroupId(guardado.getId());
            userRepository.guardar(miembro);
        }

        FamilyGroup resultado = new FamilyGroup(guardado.getId(), guardado.getNombre(), miembros);
        return resultado;
    }
}