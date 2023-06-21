package com.numble.reservationsystem.entity.domain;

import com.numble.reservationsystem.entity.BaseEntity;
import com.numble.reservationsystem.entity.Category;
import com.numble.reservationsystem.entity.ConcertState;
import com.numble.reservationsystem.entity.dto.concert.ConcertRequestDto;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "concert")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Concert extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_id")
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
    private ConcertState concertState;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Concert toEntity(ConcertRequestDto requestDto, User user) {

        return Concert.builder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .startTime(requestDto.getStartTime())
            .endTime(requestDto.getEndTime())
            .date(requestDto.getDate())
            .price(requestDto.getPrice())
            .location(requestDto.getLocation())
            .category(requestDto.getCategory())
            .concertState(requestDto.getConcertState())
            .user(user)
            .build();
    }

    public void update(ConcertRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.category = requestDto.getCategory();
        this.startTime = requestDto.getStartTime();
        this.endTime = requestDto.getEndTime();
        this.date = requestDto.getDate();
        this.price = requestDto.getPrice();
        this.location = requestDto.getLocation();
    }

    public boolean checkEmail(String userEmail) {
        return this.user.getEmail().equals(userEmail);
    }
}
