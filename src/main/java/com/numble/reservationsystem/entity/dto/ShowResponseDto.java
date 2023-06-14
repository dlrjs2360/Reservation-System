package com.numble.reservationsystem.entity.dto;

import com.numble.reservationsystem.entity.CurState;
import com.numble.reservationsystem.entity.domain.Show;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ShowResponseDto {
    private Long id;
    private String title;
    private String content;
    private String startTime;
    private String endTime;
    private Date date;
    private Long price;
    private String location;
    private CurState curState;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ShowResponseDto of(Show show) {
        return ShowResponseDto.builder()
            .id(show.getId())
            .title(show.getTitle())
            .content(show.getContent())
            .startTime(show.getStartTime())
            .endTime(show.getEndTime())
            .date(show.getDate())
            .price(show.getPrice())
            .location(show.getLocation())
            .curState(show.getCurState())
            .category(show.getCategory().getName())
            .createdAt(show.getCreatedAt())
            .updatedAt(show.getUpdatedAt())
            .build();
    }
}