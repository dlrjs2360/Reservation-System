package com.numble.reservationsystem.service;


import com.numble.reservationsystem.entity.CurState;
import com.numble.reservationsystem.entity.domain.Show;
import com.numble.reservationsystem.entity.dto.ShowRequestDto;
import com.numble.reservationsystem.entity.dto.ShowResponseDto;
import com.numble.reservationsystem.repository.ShowRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShowService {

    private final ShowRepository showRepository;

    @Transactional
    public ShowResponseDto register(ShowRequestDto requestDto) {
        Show show = showRepository.save(Show.toEntity(requestDto));
        return ShowResponseDto.of(show);
    }

    @Transactional
    public ShowResponseDto update(ShowRequestDto requestDto) {
        Show show = showRepository.findById(requestDto.getId()).orElseThrow();
        show.update(requestDto);
        return ShowResponseDto.of(show);
    }

    public ShowResponseDto getDetail(Long showId) {
        Show show = showRepository.findById(showId).orElseThrow();
        return ShowResponseDto.of(show);
    }

    public List<ShowResponseDto> getListByState(CurState curState) {
        return showRepository.findAllByCurState(curState)
            .stream()
            .map(ShowResponseDto::of)
            .collect(Collectors.toList());
    }




}

