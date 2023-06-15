package com.numble.reservationsystem.entity.domain;

import com.numble.reservationsystem.entity.BaseEntity;
import com.numble.reservationsystem.entity.Category;
import com.numble.reservationsystem.entity.CurState;
import com.numble.reservationsystem.entity.dto.ShowRequestDto;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Table(name = "show")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Show extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endTime;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CurState curState;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "operator_id")
    private User user;

    public static Show toEntity(ShowRequestDto requestDto) {
        return Show.builder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .startTime(requestDto.getStartTime())
            .endTime(requestDto.getEndTime())
            .date(requestDto.getDate())
            .price(requestDto.getPrice())
            .location(requestDto.getLocation())
            .curState(requestDto.getCurState())
            .build();
    }

    public void update(ShowRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.startTime = requestDto.getStartTime();
        this.endTime = requestDto.getEndTime();
        this.date = requestDto.getDate();
        this.price = requestDto.getPrice();
        this.location = requestDto.getLocation();
    }
}
