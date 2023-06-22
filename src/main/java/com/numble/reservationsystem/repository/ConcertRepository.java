package com.numble.reservationsystem.repository;

import com.numble.reservationsystem.entity.domain.Concert;
import com.numble.reservationsystem.repository.Custom.ConcertRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRepository extends JpaRepository<Concert, Long>, ConcertRepositoryCustom {


}

