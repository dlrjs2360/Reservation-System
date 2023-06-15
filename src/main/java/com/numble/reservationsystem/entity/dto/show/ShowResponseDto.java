package com.numble.reservationsystem.entity.dto.show;

import com.numble.reservationsystem.entity.Category;
import com.numble.reservationsystem.entity.CurState;
import com.numble.reservationsystem.entity.domain.Show;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDate date;
    private Long price;
    private String location;
    private CurState curState;
    private Category category;
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
            .category(show.getCategory())
            .createdAt(show.getCreatedAt())
            .updatedAt(show.getUpdatedAt())
            .build();
    }
}
