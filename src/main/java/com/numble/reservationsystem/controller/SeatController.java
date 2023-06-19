package com.numble.reservationsystem.controller;

import com.numble.reservationsystem.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seat")
public class SeatController {

    private final SeatService seatService;
}
