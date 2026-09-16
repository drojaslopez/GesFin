package com.gesfin.adapters.out.persistence.mapper;

import com.gesfin.adapters.out.persistence.entity.TransactionJpaEntity;
import com.gesfin.domain.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionEntityMapper {

    TransactionEntityMapper INSTANCE = Mappers.getMapper(TransactionEntityMapper.class);

    TransactionJpaEntity toEntity(Transaction domain);

    Transaction toDomain(TransactionJpaEntity entity);
}