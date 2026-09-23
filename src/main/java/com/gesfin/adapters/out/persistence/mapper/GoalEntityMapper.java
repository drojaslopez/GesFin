package com.gesfin.adapters.out.persistence.mapper;

import com.gesfin.adapters.out.persistence.entity.GoalJpaEntity;
import com.gesfin.adapters.out.persistence.entity.MemberQuotaJpaEntity;
import com.gesfin.domain.model.Goal;
import com.gesfin.domain.model.MemberQuota;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GoalEntityMapper {

    GoalEntityMapper INSTANCE = Mappers.getMapper(GoalEntityMapper.class);

    @Mapping(target = "cuotas", source = "cuotas")
    GoalJpaEntity toEntity(Goal domain);

    @Mapping(target = "cuotas", source = "cuotas")
    Goal toDomain(GoalJpaEntity entity);

    @Mapping(target = "id", ignore = true)
    MemberQuotaJpaEntity toQuotaEntity(MemberQuota domain);

    MemberQuota toQuotaDomain(MemberQuotaJpaEntity entity);
}