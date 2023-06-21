package com.numble.reservationsystem.repository;

import com.numble.reservationsystem.entity.ConcertState;
import com.numble.reservationsystem.entity.domain.Concert;
import java.util.List;

public interface ConcertRepositoryCustom {
    List<Concert> findAllByConcertState(ConcertState concertState);

    Concert findByIdCustom(Long concertId);
}
