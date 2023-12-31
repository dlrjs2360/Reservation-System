package com.numble.reservationsystem.repository.Impl;

import static com.numble.reservationsystem.entity.domain.QConcert.concert;
import static com.querydsl.jpa.JPAExpressions.select;

import com.numble.reservationsystem.entity.ConcertState;
import com.numble.reservationsystem.entity.domain.Concert;
import com.numble.reservationsystem.exception.CustomException;
import com.numble.reservationsystem.exception.handler.ErrorCode;
import com.numble.reservationsystem.repository.Custom.ConcertRepositoryCustom;
import com.numble.reservationsystem.repository.Support.Querydsl4RepositorySupport;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Slf4j
@Transactional(readOnly = true)
public class ConcertRepositoryImpl extends Querydsl4RepositorySupport implements
    ConcertRepositoryCustom {

    public ConcertRepositoryImpl() { super(Concert.class);}

    @Override
    public List<Concert> findAllByConcertState(ConcertState concertState) {
        return selectFrom(concert)
            .where(concert.concertState.eq(concertState))
            .fetch();
    }

    @Override
    public Concert findByIdCustom(Long concertId) {
        return Optional.ofNullable(selectFrom(concert)
            .where(concert.id.eq(concertId))
            .fetchFirst())
            .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_EXISTS));
    }
}
