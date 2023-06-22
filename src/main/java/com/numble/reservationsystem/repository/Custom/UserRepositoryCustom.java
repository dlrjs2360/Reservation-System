package com.numble.reservationsystem.repository.Custom;

import com.numble.reservationsystem.entity.domain.User;

public interface UserRepositoryCustom {

    User findByEmail(String email);

    boolean existsByEmail(String email);
}
