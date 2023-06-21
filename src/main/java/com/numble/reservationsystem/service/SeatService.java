package com.numble.reservationsystem.service;

import com.numble.reservationsystem.entity.SeatState;
import com.numble.reservationsystem.entity.SeatType;
import com.numble.reservationsystem.entity.UserRole;
import com.numble.reservationsystem.entity.domain.Seat;
import com.numble.reservationsystem.entity.domain.Concert;
import com.numble.reservationsystem.entity.domain.User;
import com.numble.reservationsystem.entity.dto.seat.SeatRegisterDto;
import com.numble.reservationsystem.entity.dto.seat.SeatResponseDto;
import com.numble.reservationsystem.entity.dto.seat.SeatUpdateRequestDto;
import com.numble.reservationsystem.repository.SeatRepository;
import com.numble.reservationsystem.repository.ConcertRepository;
import com.numble.reservationsystem.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SeatService {

    private final SeatRepository seatRepository;
    private final ConcertRepository concertRepository;
    private final UserRepository userRepository;

    /*
     * 좌석 생성 기능
     * 1. 좌석 번호는 문자형태
     * 2. 등록한 시점에는 예약가능 상태? -> 공연이 오픈되어야 예약 가능 상태가 된다.
     * 3. 후에 좌석 예약시에는 AVAILABLE만 예약이 가능하다.
     * */
    @Transactional
    public List<SeatResponseDto> registerSeatList(SeatRegisterDto seatRegisterDto, String userEmail) {
        // 공연 관리자만 가능해야 함.
        User user = userRepository.findByEmail(userEmail);
        Concert concert = concertRepository.findById(seatRegisterDto.getConcertId()).orElseThrow();

        if (user.getRole().equals(UserRole.USER)) {
            log.info("관리자 및 공연 등록자만 공연 좌석 등록 가능");
        }

        if (!user.getRole().equals(UserRole.ADMIN) && !concert.checkEmail(userEmail)) {
            log.info("공연 등록자와 등록자를 제외하면 좌석 등록 불가능");
        }

        Map<String, SeatType> seatTypeInfo = seatRegisterDto.getSeatTypeInfo();
        List<SeatResponseDto> seatList = new ArrayList<>();

        // 로직 분리 필요
        for (Entry<String, Long> entry : seatRegisterDto.getSeatCount().entrySet()) {
            for (int i = 1; i <= entry.getValue(); i++) {
                // 좌석 생성 로직
                Seat seat = Seat.builder()
                    .type(seatTypeInfo.get(entry.getKey()))
                    .number(entry.getKey() + String.valueOf(i))
                    .status(SeatState.AVAILABLE)
                    .concert(concert)
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
     * 1. 이미 등록된 좌석 번호로는 좌석 번호는 수정 불가능
     * 2. 좌석 타입 변경 가능
     * 3. 공연 ID 변경 불가능
     * 4. 공연 삭제도 포함
     *      a. 이미 예매된 좌석 즉, Status가 BOOKED면 삭제 불가능
     *      b. 공연이 삭제되면 모든 좌석도 삭제되어야 함.
     * 5. 수정로직은 Transactional을 통한 더티체킹을 사용한다.
     * */
    @Transactional
    public SeatResponseDto updateSeat(SeatUpdateRequestDto updateRequestDto, String userEmail) {
        Seat seat = seatRepository.findById(updateRequestDto.getId()).orElseThrow();
        User user = userRepository.findByEmail(userEmail);
        if (!user.getRole().equals(UserRole.ADMIN) && !seat.getConcert().checkEmail(userEmail)) {
            log.info("관리자 또는 공연 등록자만 업데이트 가능");
        }
        if (updateRequestDto.getStatus().equals(SeatState.FORBIDDEN) && seat.getStatus().equals(
            SeatState.BOOKED)) {
            log.info("예매된 좌석은 비공개처리 불가능");
        }
        seat.update(updateRequestDto);
        return SeatResponseDto.of(seat);
    }

    /*
    * 좌석 조회 기능
    * */
    public SeatResponseDto getSeatInfo(Long seadId) {
        return SeatResponseDto.of(seatRepository.findById(seadId).orElseThrow());
    }

    public List<SeatResponseDto> findSeatsByConcertId(Long concertId) {
        Concert concert = concertRepository.findById(concertId).orElseThrow();
        return seatRepository.findAll().stream()
            .filter(s -> s.getConcert().equals(concert))
            .map(SeatResponseDto::of)
            .collect(Collectors.toList());
    }

}
