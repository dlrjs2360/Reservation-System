package com.numble.reservationsystem.repository;


import com.numble.reservationsystem.entity.domain.Point;
import com.numble.reservationsystem.entity.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
}
