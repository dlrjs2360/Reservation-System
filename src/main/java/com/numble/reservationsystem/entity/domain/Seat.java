package com.numble.reservationsystem.entity.domain;

import com.numble.reservationsystem.entity.SeatStatus;
import com.numble.reservationsystem.entity.SeatType;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "seat")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id ")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatType type;

    @Column(nullable = false)
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "show_id")
    private Show show;

}
