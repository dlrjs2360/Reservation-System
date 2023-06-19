package com.numble.reservationsystem.controller;

import com.numble.reservationsystem.entity.CurState;
import com.numble.reservationsystem.entity.dto.show.ShowRequestDto;
import com.numble.reservationsystem.entity.dto.show.ShowResponseDto;
import com.numble.reservationsystem.service.ShowService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class ShowController {

    private final ShowService showService;

    @GetMapping("/{showid}")
    public ResponseEntity<ShowResponseDto> findShow(@PathVariable Long showId, @ApiIgnore
    Authentication authentication) {
        return ResponseEntity.status(200).body(showService.getDetail(showId,
            authentication.getName()));
    }

    @GetMapping("/list/{curstate}")
    public ResponseEntity<List<ShowResponseDto>> findAllByState(@PathVariable CurState curstate) {
        return ResponseEntity.status(200).body(showService.getListByState(curstate));
    }

    @PostMapping("/")
    public ResponseEntity<ShowResponseDto> registerShow(@RequestBody ShowRequestDto requestDto,
        @ApiIgnore Authentication authentication) {
        return ResponseEntity.status(200).body(showService.register(requestDto,
            authentication.getName()));
    }

    @PatchMapping("/")
    public ResponseEntity<ShowResponseDto> deleteShow(@RequestBody ShowRequestDto requestDto,
        @ApiIgnore Authentication authentication) {
        return ResponseEntity.status(200).body(showService.update(requestDto,
            authentication.getName()));
    }


}
