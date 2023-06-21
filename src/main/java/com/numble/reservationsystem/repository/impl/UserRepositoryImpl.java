package com.numble.reservationsystem.repository.impl;

import static com.numble.reservationsystem.entity.domain.QUser.user;
import static com.querydsl.jpa.JPAExpressions.selectFrom;

import com.numble.reservationsystem.entity.domain.User;
import com.numble.reservationsystem.exception.CustomException;
import com.numble.reservationsystem.exception.handler.ErrorCode;
import com.numble.reservationsystem.repository.UserRepositoryCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Slf4j
@Transactional(readOnly = true)
public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {

    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public User findByEmail(String email) {
        return selectFrom(user)
            .where(user.email.eq(email))
            .fetch()
            .stream()
            .findFirst()
            .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_EXISTS));
    }
}
