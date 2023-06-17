package com.numble.reservationsystem.entity;

public enum SeatType {
    VIP((long) 1.3), NORMAL((long) 1.0);

    SeatType(Long percent) {
    }
}
