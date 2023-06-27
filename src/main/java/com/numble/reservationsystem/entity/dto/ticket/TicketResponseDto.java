package com.numble.reservationsystem.entity.dto.ticket;

import com.numble.reservationsystem.entity.TicketState;
import com.numble.reservationsystem.entity.domain.Ticket;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TicketResponseDto {

    private Long id;
    private String userEmail;
    private String concertTitle;
    private List<String> seatList;
    private TicketState ticketState;

    public static TicketResponseDto of(Ticket ticket) {
        List<String> seatStringList = ticket.getSeatList().stream()
            .map(seat -> seat.getNumber())
            .collect(Collectors.toList());

        return TicketResponseDto.builder()
            .id(ticket.getId())
            .userEmail(ticket.getUser().getEmail())
            .concertTitle(ticket.getConcert().getTitle())
            .seatList(seatStringList)
            .ticketState(ticket.getTicketState())
            .build();
    }
}
