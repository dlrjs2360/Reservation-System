package com.numble.reservationsystem.repository.impl;

import static com.numble.reservationsystem.entity.domain.QConcert.concert;
import static com.numble.reservationsystem.entity.domain.QSeat.seat;
import static com.querydsl.jpa.JPAExpressions.select;

import com.numble.reservationsystem.entity.ConcertState;
import com.numble.reservationsystem.entity.domain.Concert;
import com.numble.reservationsystem.exception.CustomException;
import com.numble.reservationsystem.exception.handler.ErrorCode;
import com.numble.reservationsystem.repository.ConcertRepositoryCustom;
import com.numble.reservationsystem.repository.support.Querydsl4RepositorySupport;
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
        return select(concert)
            .where(concert.concertState.eq(concertState))
            .fetch();
    }

    @Override
    public Concert findByIdCustom(Long concertId) {
        return Optional.ofNullable(select(concert)
            .where(concert.id.eq(concertId))
            .fetchFirst())
            .orElseThrow(() -> new CustomException(ErrorCode.Concert_NOT_EXISTS));
    }
}
