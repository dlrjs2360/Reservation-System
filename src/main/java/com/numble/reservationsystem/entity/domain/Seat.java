package com.numble.reservationsystem.entity.domain;

import com.numble.reservationsystem.entity.SeatState;
import com.numble.reservationsystem.entity.SeatType;
import com.numble.reservationsystem.entity.dto.seat.SeatUpdateRequestDto;
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
    private SeatState status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    private Concert concert;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Ticket ticket;
    /*
     * 1. 이미 등록된 좌석 번호로는 수정 불가능
     * 2. 이미 예매된 좌석이라면 삭제 불가능(BOOKED)
     * 3. 공연이 삭제되면 모든 좌석도 삭제되어야 함.
     * */
    public void update(SeatUpdateRequestDto updateRequestDto) {

        if (updateRequestDto.getType() != null) {
            this.type = updateRequestDto.getType();
        }
        if (updateRequestDto.getNumber() != null) {
            this.number = updateRequestDto.getNumber();
        }
        if (updateRequestDto.getStatus() != null) {
            this.status = updateRequestDto.getStatus();
        }
    }

    public void bookSeat() {
        this.status = SeatState.BOOKED;
    }

    public void cancelSeat(){
        this.status = SeatState.AVAILABLE;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

}
