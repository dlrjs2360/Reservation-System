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

    /*
     * 좌석 생성 기능
     * 1. 좌석 번호는 문자형태
     * 2. 등록한 시점에는 예약가능 상태? -> 공연이 오픈되어야 예약 가능 상태가 된다.
     * 3. 후에 좌석 예약시에는 AVAILABLE만 예약이 가능하다.
     * */
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
                    .status(SeatStatus.FORBIDDEN)
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

    /*
     * 좌석 수정 기능
     * 1. 이미 좌석 번호가 있으면 좌석 번호는 수정 불가능
     * 2. 좌석 타입 변경 가능
     * 3. 공연 ID 변경 불가능
     * */

    /*
     * 좌석 삭제 기능
     * 1. 이미 예매된 좌석 즉, Status가 BOOKED면 삭제 불가능
     * 2. 공연이 삭제되면 모든 좌석도 삭제되어야 함.
     * */



    /*
    * 좌석 조회 기능
    * */
    public SeatResponseDto getSeatRepository(Long seadId) {
        return SeatResponseDto.of(seatRepository.findById(seadId).orElseThrow());
    }
}
