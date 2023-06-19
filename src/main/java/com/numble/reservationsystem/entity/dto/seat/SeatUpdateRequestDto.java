package com.numble.reservationsystem.entity.dto.seat;

import com.numble.reservationsystem.entity.SeatStatus;
import com.numble.reservationsystem.entity.SeatType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeatUpdateRequestDto {
    private Long id;
    private SeatType type;
    private String number;
    private SeatStatus status;
}
