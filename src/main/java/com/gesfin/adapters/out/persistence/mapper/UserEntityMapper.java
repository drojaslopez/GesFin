package com.gesfin.adapters.out.persistence.mapper;

import com.gesfin.adapters.out.persistence.entity.FamilyGroupJpaEntity;
import com.gesfin.adapters.out.persistence.entity.UserJpaEntity;
import com.gesfin.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserEntityMapper {

    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    default UserJpaEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setEmail(domain.getEmail());
        entity.setPassword(domain.getPassword());
        entity.setRol(domain.getRol());
        if (domain.getFamilyGroupId() != null) {
            FamilyGroupJpaEntity fg = new FamilyGroupJpaEntity();
            fg.setId(domain.getFamilyGroupId());
            entity.setFamilyGroup(fg);
        }
        return entity;
    }

    default User toDomain(UserJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        User user = new User(entity.getId(), entity.getNombre(), entity.getEmail(),
                entity.getPassword(), entity.getRol());
        if (entity.getFamilyGroup() != null) {
            user.setFamilyGroupId(entity.getFamilyGroup().getId());
        }
        return user;
    }
}