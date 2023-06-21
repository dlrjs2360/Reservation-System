package com.numble.reservationsystem.service;


import com.numble.reservationsystem.entity.ConcertState;
import com.numble.reservationsystem.entity.UserRole;
import com.numble.reservationsystem.entity.domain.Concert;
import com.numble.reservationsystem.entity.domain.User;
import com.numble.reservationsystem.entity.dto.concert.ConcertRequestDto;
import com.numble.reservationsystem.entity.dto.concert.ConcertResponseDto;
import com.numble.reservationsystem.repository.ConcertRepository;
import com.numble.reservationsystem.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final UserRepository userRepository;

    @Transactional
    public ConcertResponseDto register(ConcertRequestDto requestDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        Concert concert = concertRepository.save(Concert.toEntity(requestDto,user));
        return ConcertResponseDto.of(concert);
    }

    @Transactional
    public ConcertResponseDto update(ConcertRequestDto requestDto, String userEmail) {
        Concert concert = concertRepository.findById(requestDto.getId()).orElseThrow();
        if (!concert.checkEmail(userEmail)) {
            log.info("작성자 불일치 익셉션");
        }
        concert.update(requestDto);
        return ConcertResponseDto.of(concert);
    }

    public ConcertResponseDto getDetail(Long showId, String userEmail) {
        Concert concert = concertRepository.findById(showId).orElseThrow();
        User user = userRepository.findByEmail(userEmail);
        if (concert.getConcertState().equals(ConcertState.DELETED) && user.getRole().equals(UserRole.ADMIN) && !concert.checkEmail(userEmail)) {
            log.info("삭제된 컨텐츠는 작성자와 관리자를 제외하면 열람 불가");
        }
        return ConcertResponseDto.of(concert);
    }

    public List<ConcertResponseDto> getListByState(ConcertState concertState) {
        return concertRepository.findAllByConcertState(concertState)
            .stream()
            .map(ConcertResponseDto::of)
            .collect(Collectors.toList());
    }





}

