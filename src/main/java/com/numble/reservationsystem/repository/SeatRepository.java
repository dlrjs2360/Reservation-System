package com.numble.reservationsystem.repository;


import com.numble.reservationsystem.entity.domain.Seat;
import com.numble.reservationsystem.entity.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {

}
