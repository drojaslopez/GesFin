package com.gesfin.domain.ports.in.queries;

import com.gesfin.domain.model.Transaction;

import java.util.List;

public interface GetTransactionsByFamilyGroupQuery {

    List<Transaction> buscarPorFamilyGroupId(Long familyGroupId);
}