package com.numble.reservationsystem.entity.dto.ticket;

import com.numble.reservationsystem.entity.TicketState;
import java.io.Serializable;
import java.util.LinkedHashMap;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketRequestDto {

    private Long concertId;
    private String seatIdList;
    private TicketState ticketState;

}
