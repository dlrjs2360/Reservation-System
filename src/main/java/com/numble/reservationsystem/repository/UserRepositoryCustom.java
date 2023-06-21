package com.numble.reservationsystem.repository;

import com.numble.reservationsystem.entity.domain.User;

public interface UserRepositoryCustom {

    User findByEmail(String email);

}
