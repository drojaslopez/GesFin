package com.gesfin.domain.ports.in.queries;

import com.gesfin.domain.model.User;

public interface GetUserByIdQuery {

    User buscar(Long id);
}