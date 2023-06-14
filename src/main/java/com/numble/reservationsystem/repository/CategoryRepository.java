package com.numble.reservationsystem.repository;


import com.numble.reservationsystem.entity.domain.Category;
import com.numble.reservationsystem.entity.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
