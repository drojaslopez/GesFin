package com.gesfin.application.queries;

import com.gesfin.domain.ports.in.queries.GetTransactionsByFamilyGroupQuery;
import com.gesfin.domain.model.Transaction;
import com.gesfin.domain.ports.out.TransactionRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionQueryService implements GetTransactionsByFamilyGroupQuery {

    private final TransactionRepositoryPort transactionRepository;

    public TransactionQueryService(TransactionRepositoryPort transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> buscarPorFamilyGroupId(Long familyGroupId) {
        return transactionRepository.buscarPorFamilyGroupId(familyGroupId);
    }
}