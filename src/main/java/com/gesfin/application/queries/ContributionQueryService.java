package com.gesfin.application.queries;

import com.gesfin.domain.ports.in.queries.GetContributionsByGoalQuery;
import com.gesfin.domain.model.Contribution;
import com.gesfin.domain.ports.out.ContributionRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContributionQueryService implements GetContributionsByGoalQuery {

    private final ContributionRepositoryPort contributionRepository;

    public ContributionQueryService(ContributionRepositoryPort contributionRepository) {
        this.contributionRepository = contributionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contribution> buscarPorGoalId(Long goalId) {
        return contributionRepository.buscarPorGoalId(goalId);
    }
}