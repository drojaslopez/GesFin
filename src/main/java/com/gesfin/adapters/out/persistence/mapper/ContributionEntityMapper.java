package com.gesfin.adapters.out.persistence.mapper;

import com.gesfin.adapters.out.persistence.entity.ContributionJpaEntity;
import com.gesfin.domain.model.Contribution;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContributionEntityMapper {

    ContributionEntityMapper INSTANCE = Mappers.getMapper(ContributionEntityMapper.class);

    ContributionJpaEntity toEntity(Contribution domain);

    Contribution toDomain(ContributionJpaEntity entity);
}