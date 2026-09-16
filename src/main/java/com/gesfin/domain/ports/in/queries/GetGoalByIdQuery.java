package com.gesfin.domain.ports.in.queries;

import com.gesfin.domain.model.Goal;

public interface GetGoalByIdQuery {

    Goal buscar(Long id);
}