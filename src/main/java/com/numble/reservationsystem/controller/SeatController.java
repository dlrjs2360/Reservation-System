package com.numble.reservationsystem.controller;

import com.numble.reservationsystem.entity.dto.seat.SeatRegisterDto;
import com.numble.reservationsystem.entity.dto.seat.SeatResponseDto;
import com.numble.reservationsystem.service.SeatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seat")
public class SeatController {

    private final SeatService seatService;

    @PostMapping
    public ResponseEntity<List<SeatResponseDto>> registerSeatList(
        @RequestBody SeatRegisterDto seatRegisterDto, @ApiIgnore
    Authentication authentication) {
        return ResponseEntity.status(200).body(seatService.registerSeatList(seatRegisterDto,
            authentication.getName()));
    }

    @GetMapping("/{seatId}")
    public ResponseEntity<SeatResponseDto> findSeat(@PathVariable Long seatId) {
        return ResponseEntity.status(200).body(seatService.getSeatInfo(seatId));
    }

    @GetMapping("/{concertId}/all")
    public ResponseEntity<List<SeatResponseDto>> findSeatsByConcert(@PathVariable Long concertId) {
        return ResponseEntity.status(200).body(seatService.findSeatsByConcertId(concertId));
    }

}
