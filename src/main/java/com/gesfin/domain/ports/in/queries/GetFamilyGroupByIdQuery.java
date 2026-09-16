package com.gesfin.domain.ports.in.queries;

import com.gesfin.domain.model.FamilyGroup;

public interface GetFamilyGroupByIdQuery {

    FamilyGroup buscar(Long id);
}