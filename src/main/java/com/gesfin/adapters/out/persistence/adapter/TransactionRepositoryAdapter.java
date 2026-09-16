package com.gesfin.adapters.out.persistence.adapter;

import com.gesfin.adapters.out.persistence.mapper.TransactionEntityMapper;
import com.gesfin.adapters.out.persistence.repository.TransactionJpaRepository;
import com.gesfin.domain.model.Transaction;
import com.gesfin.domain.ports.out.TransactionRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {

    private final TransactionJpaRepository jpaRepository;
    private final TransactionEntityMapper mapper = TransactionEntityMapper.INSTANCE;

    public TransactionRepositoryAdapter(TransactionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Transaction guardar(Transaction transaction) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(transaction)));
    }

    @Override
    public Optional<Transaction> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Transaction> buscarPorFamilyGroupId(Long familyGroupId) {
        return jpaRepository.findByFamilyGroupId(familyGroupId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Transaction> buscarPorUserId(Long userId) {
        return jpaRepository.findByUserId(userId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void eliminar(Long id) {
        jpaRepository.deleteById(id);
    }
}