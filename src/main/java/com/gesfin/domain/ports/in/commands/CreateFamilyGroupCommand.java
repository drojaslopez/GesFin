package com.gesfin.domain.ports.in.commands;

import com.gesfin.domain.model.FamilyGroup;

import java.util.List;

public interface CreateFamilyGroupCommand {

    FamilyGroup crear(String nombre, List<Long> miembroIds);
}