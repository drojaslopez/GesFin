package com.gesfin.adapters.out.persistence.mapper;

import com.gesfin.adapters.out.persistence.entity.FamilyGroupJpaEntity;
import com.gesfin.adapters.out.persistence.entity.UserJpaEntity;
import com.gesfin.domain.model.FamilyGroup;
import com.gesfin.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FamilyGroupEntityMapper {

    FamilyGroupEntityMapper INSTANCE = Mappers.getMapper(FamilyGroupEntityMapper.class);

    @Mapping(target = "miembros", source = "miembros")
    FamilyGroupJpaEntity toEntity(FamilyGroup domain);

    @Mapping(target = "miembros", source = "miembros")
    FamilyGroup toDomain(FamilyGroupJpaEntity entity);

    default UserJpaEntity mapUserToEntity(User user) {
        if (user == null) {
            return null;
        }
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(user.getId());
        entity.setNombre(user.getNombre());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setRol(user.getRol());
        return entity;
    }

    default User mapUserToDomain(UserJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new User(entity.getId(), entity.getNombre(), entity.getEmail(),
                entity.getPassword(), entity.getRol());
    }

    default List<User> mapMembersToDomain(FamilyGroupJpaEntity entity) {
        return entity.getMiembros().stream()
                .map(this::mapUserToDomain)
                .toList();
    }

    default List<UserJpaEntity> mapMembersToEntity(FamilyGroup domain) {
        return domain.getMiembros().stream()
                .map(this::mapUserToEntity)
                .toList();
    }
}