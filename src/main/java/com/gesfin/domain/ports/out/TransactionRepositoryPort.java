package com.gesfin.domain.ports.out;

import com.gesfin.domain.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepositoryPort {

    Transaction guardar(Transaction transaction);
    Optional<Transaction> buscarPorId(Long id);
    List<Transaction> buscarPorFamilyGroupId(Long familyGroupId);
    List<Transaction> buscarPorUserId(Long userId);
    void eliminar(Long id);
}
