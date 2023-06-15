package com.numble.reservationsystem.entity.dto;

import com.numble.reservationsystem.entity.Category;
import com.numble.reservationsystem.entity.CurState;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class ShowRequestDto {
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
}
