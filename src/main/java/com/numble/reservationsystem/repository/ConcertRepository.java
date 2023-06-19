package com.numble.reservationsystem.repository;

import com.numble.reservationsystem.entity.ConcertState;
import com.numble.reservationsystem.entity.domain.Concert;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

    List<Concert> findAllByConcertState(ConcertState concertState);
}

