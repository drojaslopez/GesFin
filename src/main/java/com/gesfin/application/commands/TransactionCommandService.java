package com.gesfin.application.commands;

import com.gesfin.domain.ports.in.commands.RegisterTransactionCommand;
import com.gesfin.domain.enums.TipoTransaccion;
import com.gesfin.domain.model.Transaction;
import com.gesfin.domain.ports.out.TransactionRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransactionCommandService implements RegisterTransactionCommand {

    private final TransactionRepositoryPort transactionRepository;

    public TransactionCommandService(TransactionRepositoryPort transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public Transaction registrar(Long familyGroupId, Long userId, BigDecimal monto,
                                 TipoTransaccion tipo, String categoria, LocalDate fecha) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto de la transacción debe ser mayor a cero");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de transacción es obligatorio");
        }

        Transaction transaction = new Transaction();
        transaction.setFamilyGroupId(familyGroupId);
        transaction.setUserId(userId);
        transaction.setMonto(monto);
        transaction.setTipo(tipo);
        transaction.setCategoria(categoria);
        transaction.setFecha(fecha != null ? fecha : LocalDate.now());

        return transactionRepository.guardar(transaction);
    }
}