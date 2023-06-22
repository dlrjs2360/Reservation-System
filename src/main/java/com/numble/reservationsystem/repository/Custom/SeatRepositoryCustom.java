package com.numble.reservationsystem.repository.Custom;

import com.numble.reservationsystem.entity.domain.Seat;

public interface SeatRepositoryCustom {

     Seat findByIdCustom(Long seatId);

}
