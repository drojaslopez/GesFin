package com.gesfin.domain.ports.in.queries;

import com.gesfin.domain.model.Contribution;

import java.util.List;

public interface GetContributionsByGoalQuery {

    List<Contribution> buscarPorGoalId(Long goalId);
}