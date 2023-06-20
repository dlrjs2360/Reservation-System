package com.numble.reservationsystem.entity.dto.ticket;

import com.numble.reservationsystem.entity.TicketState;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketRequestDto {

    private Long concertId;
    private List<Long> seatIdList;
    private TicketState ticketState;
}
