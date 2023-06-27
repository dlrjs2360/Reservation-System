package com.numble.reservationsystem.entity.dto.concert;

import com.numble.reservationsystem.entity.Category;
import com.numble.reservationsystem.entity.ConcertState;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ConcertRequestDto {
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
}
