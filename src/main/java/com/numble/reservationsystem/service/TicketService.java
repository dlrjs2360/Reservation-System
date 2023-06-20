package com.numble.reservationsystem.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

        Gson gson = new Gson();
        List<Long> seatRequestList = gson.fromJson(ticketRequestDto.getSeatIdList(),
            new TypeToken<List<Long>>() {}.getType());

        List<Seat> seatList = seatRequestList.stream()
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

    @Transactional
    public TicketResponseDto cancel(Long ticketId, String userEmail) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        // 권한 예외처리 필요
        for (Seat seat : ticket.getSeatList()) {
            seat.cancelSeat();
        }
        ticket.cancel();
        return TicketResponseDto.of(ticket);
    }

    @Transactional
    public List<TicketResponseDto> findByUserID(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        List<Ticket> ticketList = ticketRepository.findByUser(user);
        return ticketList.stream()
            .map(TicketResponseDto::of)
            .collect(Collectors.toList());
    }

}
