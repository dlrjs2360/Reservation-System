package com.numble.reservationsystem.entity.dto.Seat;

import com.numble.reservationsystem.entity.SeatType;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeatRegisterDto {

    private Long showId;
    private Map<String, Long> seatCount;
    private Map<String, SeatType> seatTypeInfo;
}
