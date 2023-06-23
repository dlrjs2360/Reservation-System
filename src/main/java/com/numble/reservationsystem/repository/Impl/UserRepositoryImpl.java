package com.numble.reservationsystem.repository.Impl;

import static com.numble.reservationsystem.entity.domain.QUser.user;

import com.numble.reservationsystem.entity.domain.User;
import com.numble.reservationsystem.exception.CustomException;
import com.numble.reservationsystem.exception.handler.ErrorCode;
import com.numble.reservationsystem.repository.Custom.UserRepositoryCustom;
import com.numble.reservationsystem.repository.Support.Querydsl4RepositorySupport;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Repository
public class UserRepositoryImpl extends Querydsl4RepositorySupport implements UserRepositoryCustom {

    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public User findByEmail(String email) {
        return Optional.ofNullable(
                selectFrom(user)
                    .where(user.email.eq(email))
                    .fetchOne())
            .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_EXISTS));
    }

    @Override
    public boolean existsByEmail(String email) {
        return Optional.ofNullable(
            selectFrom(user)
                .where(user.email.eq(email))
                .fetchOne()
        ).isPresent();
    }
}
