package com.numble.reservationsystem.entity.dto.seat;

import com.numble.reservationsystem.entity.SeatStatus;
import com.numble.reservationsystem.entity.SeatType;
import com.numble.reservationsystem.entity.domain.Seat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SeatResponseDto {
    private Long id;
    private SeatType type;
    private String number;
    private SeatStatus status;
    private String showTitle;

    public static SeatResponseDto of(Seat seat) {
        return SeatResponseDto.builder()
            .id(seat.getId())
            .type(seat.getType())
            .number(seat.getNumber())
            .status(seat.getStatus())
            .showTitle(seat.getConcert().getTitle())
            .build();
    }
}
