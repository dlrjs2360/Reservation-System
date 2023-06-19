package com.numble.reservationsystem.controller;

import com.numble.reservationsystem.entity.ConcertState;
import com.numble.reservationsystem.entity.dto.concert.ConcertRequestDto;
import com.numble.reservationsystem.entity.dto.concert.ConcertResponseDto;
import com.numble.reservationsystem.service.ConcertService;
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
@RequestMapping("/api/show")
public class ConcertController {

    private final ConcertService concertService;

    @GetMapping("/{concertId}")
    public ResponseEntity<ConcertResponseDto> findConcert(@PathVariable Long concertId, @ApiIgnore
    Authentication authentication) {
        return ResponseEntity.status(200).body(concertService.getDetail(concertId,
            authentication.getName()));
    }

    @GetMapping("/list/{concertState}")
    public ResponseEntity<List<ConcertResponseDto>> findAllByState(@PathVariable ConcertState concertState) {
        return ResponseEntity.status(200).body(concertService.getListByState(concertState));
    }

    @PostMapping("/")
    public ResponseEntity<ConcertResponseDto> registerConcert(@RequestBody ConcertRequestDto requestDto,
        @ApiIgnore Authentication authentication) {
        return ResponseEntity.status(200).body(concertService.register(requestDto,
            authentication.getName()));
    }

    @PatchMapping("/")
    public ResponseEntity<ConcertResponseDto> deleteConcert(@RequestBody ConcertRequestDto requestDto,
        @ApiIgnore Authentication authentication) {
        return ResponseEntity.status(200).body(concertService.update(requestDto,
            authentication.getName()));
    }

}
