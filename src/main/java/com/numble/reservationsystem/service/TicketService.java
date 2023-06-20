package com.numble.reservationsystem.service;

import com.numble.reservationsystem.entity.SeatState;
import com.numble.reservationsystem.entity.domain.Concert;
import com.numble.reservationsystem.entity.domain.Seat;
import com.numble.reservationsystem.entity.domain.Ticket;
import com.numble.reservationsystem.entity.domain.User;
import com.numble.reservationsystem.entity.TicketState;
import com.numble.reservationsystem.entity.dto.ticket.TicketRequestDto;
import com.numble.reservationsystem.entity.dto.ticket.TicketResponseDto;
import com.numble.reservationsystem.repository.ConcertRepository;
import com.numble.reservationsystem.repository.SeatRepository;
import com.numble.reservationsystem.repository.TicketRepository;
import com.numble.reservationsystem.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TicketService {

    private final SeatRepository seatRepository;

    private final ConcertRepository concertRepository;

    private final UserRepository userRepository;

    private final TicketRepository ticketRepository;

    @Transactional
    public TicketResponseDto makeTicket(TicketRequestDto ticketRequestDto, String userEmail) {

        User user = userRepository.findByEmail(userEmail).orElseThrow();
        Concert concert = concertRepository.findById(ticketRequestDto.getConcertId()).orElseThrow();

        List<Seat> seatList = ticketRequestDto.getSeatIdList().stream()
            .map(seatRepository::findById)
            .map(Optional::orElseThrow)
            .collect(Collectors.toList());

        for (Seat seat : seatList) {
            if (!seat.getStatus().equals(SeatState.AVAILABLE)) {
                log.info("예매 가능한 좌석이 아닙니다.");
            }
            seat.bookSeat();
        }

        Ticket ticket = Ticket.builder()
            .ticketState(TicketState.ALIVE)
            .user(user)
            .concert(concert)
            .seatList(seatList)
            .build();

        ticketRepository.save(ticket);

        return TicketResponseDto.of(ticket);
    }
}
