package com.numble.reservationsystem.repository;


import com.numble.reservationsystem.entity.domain.Coupon;
import com.numble.reservationsystem.entity.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
