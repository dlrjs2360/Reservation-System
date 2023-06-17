package com.numble.reservationsystem.service;

import com.numble.reservationsystem.entity.SeatStatus;
import com.numble.reservationsystem.entity.SeatType;
import com.numble.reservationsystem.entity.domain.Seat;
import com.numble.reservationsystem.entity.domain.Show;
import com.numble.reservationsystem.entity.dto.Seat.SeatRegisterDto;
import com.numble.reservationsystem.entity.dto.Seat.SeatResponseDto;
import com.numble.reservationsystem.repository.SeatRepository;
import com.numble.reservationsystem.repository.ShowRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SeatService {

    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;

    public List<SeatResponseDto> registerSeats(SeatRegisterDto seatRegisterDto) {
        // 공연 관리자만 가능해야 함.
        Map<String, SeatType> seatTypeInfo = seatRegisterDto.getSeatTypeInfo();
        Show show = showRepository.findById(seatRegisterDto.getShowId()).orElseThrow();
        List<SeatResponseDto> seatList = new ArrayList<>();

        // 로직 분리 필요
        for (Entry<String, Long> entry : seatRegisterDto.getSeatCount().entrySet()) {
            for (int i = 1; i <= entry.getValue(); i++) {
                // 좌석 생성 로직
                Seat seat = Seat.builder()
                    .type(seatTypeInfo.get(entry.getKey()))
                    .number(entry.getKey() + String.valueOf(i))
                    .status(SeatStatus.AVAILABLE)
                    .show(show)
                    .build();
                // 좌석 저장
                seatRepository.save(seat);
                // 좌석 리스트 저장
                seatList.add(SeatResponseDto.of(seat));
            }

        }

        return seatList;
    }


}
