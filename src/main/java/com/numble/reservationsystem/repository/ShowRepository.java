package com.numble.reservationsystem.repository;

import com.numble.reservationsystem.entity.domain.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long> {
}