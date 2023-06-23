package com.numble.reservationsystem.repository.Impl;

import static com.numble.reservationsystem.entity.domain.QSeat.seat;

import com.numble.reservationsystem.entity.domain.Seat;
import com.numble.reservationsystem.exception.CustomException;
import com.numble.reservationsystem.exception.handler.ErrorCode;
import com.numble.reservationsystem.repository.Custom.SeatRepositoryCustom;
import com.numble.reservationsystem.repository.Support.Querydsl4RepositorySupport;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Repository
public class SeatRepositoryImpl extends Querydsl4RepositorySupport implements SeatRepositoryCustom {

    public SeatRepositoryImpl() {
        super(Seat.class);
    }

    @Override
    public Seat findByIdCustom(Long seatId) {
        return Optional.ofNullable(selectFrom(seat)
                .where(seat.id.eq(seatId))
                .fetchFirst())
            .orElseThrow(() -> new CustomException(ErrorCode.SEAT_NOT_EXISTS));
    }
}
