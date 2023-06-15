package com.numble.reservationsystem.repository;

import com.numble.reservationsystem.entity.CurState;
import com.numble.reservationsystem.entity.domain.Show;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findAllByCurState(CurState curState);
}

