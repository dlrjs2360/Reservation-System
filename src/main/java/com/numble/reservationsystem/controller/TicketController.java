package com.numble.reservationsystem.controller;

import com.numble.reservationsystem.entity.dto.ticket.TicketRequestDto;
import com.numble.reservationsystem.entity.dto.ticket.TicketResponseDto;
import com.numble.reservationsystem.service.TicketService;
import io.swagger.annotations.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ticket")
public class TicketController {

    private final TicketService ticketService;

    // 공연 예매(/ticket)
    @PostMapping
    public ResponseEntity<TicketResponseDto> book(@RequestBody TicketRequestDto ticketRequestDto,
        @ApiIgnore
        Authentication authentication) {
        return ResponseEntity.status(200).body(ticketService.makeTicket(ticketRequestDto,
            authentication.getName()));
    }

    // 예매 취소(/ticket/{id})
    @PatchMapping("/{ticketId}")
    public ResponseEntity<TicketResponseDto> cancel(@PathVariable Long ticketId,
        @ApiIgnore Authentication authentication) {
        return ResponseEntity.status(200).body(ticketService.cancel(ticketId,
            authentication.getName()));
    }

    // 회원별 예매 내역 목록 조회(/ticket/user/{userId})
    @GetMapping("/myTickets")
    public ResponseEntity<List<TicketResponseDto>> findByUserId(@ApiIgnore Authentication authentication) {
        return ResponseEntity.status(200).body(ticketService.findByUserID(authentication.getName()));
    }



    // 예매 내역 상세 조회(/ticket/{ticketId})

}
