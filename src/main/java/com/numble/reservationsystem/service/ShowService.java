package com.numble.reservationsystem.service;


import com.numble.reservationsystem.entity.CurState;
import com.numble.reservationsystem.entity.UserRole;
import com.numble.reservationsystem.entity.domain.Show;
import com.numble.reservationsystem.entity.domain.User;
import com.numble.reservationsystem.entity.dto.show.ShowRequestDto;
import com.numble.reservationsystem.entity.dto.show.ShowResponseDto;
import com.numble.reservationsystem.repository.ShowRepository;
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
public class ShowService {

    private final ShowRepository showRepository;
    private final UserRepository userRepository;

    @Transactional
    public ShowResponseDto register(ShowRequestDto requestDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        Show show = showRepository.save(Show.toEntity(requestDto,user));
        return ShowResponseDto.of(show);
    }

    @Transactional
    public ShowResponseDto update(ShowRequestDto requestDto, String userEmail) {
        Show show = showRepository.findById(requestDto.getId()).orElseThrow();
        if (!show.checkEmail(userEmail)) {
            log.info("작성자 불일치 익셉션");
        }
        show.update(requestDto);
        return ShowResponseDto.of(show);
    }

    public ShowResponseDto getDetail(Long showId, String userEmail) {
        Show show = showRepository.findById(showId).orElseThrow();
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if (show.getCurState().equals(CurState.DELETED) && user.getRole().equals(UserRole.ADMIN) && !show.checkEmail(userEmail)) {
            log.info("삭제된 컨텐츠는 작성자와 관리자를 제외하면 열람 불가");
        }
        return ShowResponseDto.of(show);
    }

    public List<ShowResponseDto> getListByState(CurState curState) {
        return showRepository.findAllByCurState(curState)
            .stream()
            .map(ShowResponseDto::of)
            .collect(Collectors.toList());
    }





}

