package com.numble.reservationsystem.entity.dto.seat;

import com.numble.reservationsystem.entity.domain.Seat;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SeatRegisterResponseDto {

    private Long concertId;
    private List<SeatResponseDto> registeredSeats;

    public static SeatRegisterResponseDto of(Seat seat,List<SeatResponseDto> registeredSeats) {
        return SeatRegisterResponseDto.builder()
            .concertId(seat.getId())
            .registeredSeats(registeredSeats)
            .build();
    }
}


