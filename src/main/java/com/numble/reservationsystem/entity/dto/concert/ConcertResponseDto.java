package com.numble.reservationsystem.entity.dto.concert;

import com.numble.reservationsystem.entity.Category;
import com.numble.reservationsystem.entity.ConcertState;
import com.numble.reservationsystem.entity.domain.Concert;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ConcertResponseDto {
    private Long id;
    private String title;
    private String content;
    private String startTime;
    private String endTime;
    private LocalDate date;
    private Long price;
    private String location;
    private ConcertState concertState;
    private Category category;
    private String operatorEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ConcertResponseDto of(Concert concert) {
        return ConcertResponseDto.builder()
            .id(concert.getId())
            .title(concert.getTitle())
            .content(concert.getContent())
            .startTime(concert.getStartTime())
            .endTime(concert.getEndTime())
            .date(concert.getDate())
            .price(concert.getPrice())
            .location(concert.getLocation())
            .concertState(concert.getConcertState())
            .category(concert.getCategory())
            .operatorEmail(concert.getUser().getEmail())
            .createdAt(concert.getCreatedAt())
            .updatedAt(concert.getUpdatedAt())
            .build();
    }
}
