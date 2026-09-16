package com.gesfin.domain.ports.in.queries;

import com.gesfin.domain.model.Goal;

import java.util.List;

public interface GetGoalsByFamilyGroupQuery {

    List<Goal> buscarPorFamilyGroupId(Long familyGroupId);
}