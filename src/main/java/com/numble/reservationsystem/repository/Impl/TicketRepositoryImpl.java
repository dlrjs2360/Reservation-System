package com.numble.reservationsystem.repository.Impl;

import static com.numble.reservationsystem.entity.domain.QTicket.ticket;

import com.numble.reservationsystem.entity.domain.Ticket;
import com.numble.reservationsystem.exception.CustomException;
import com.numble.reservationsystem.exception.handler.ErrorCode;
import com.numble.reservationsystem.repository.Custom.TicketRepositoryCustom;
import com.numble.reservationsystem.repository.Support.Querydsl4RepositorySupport;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Repository
public class TicketRepositoryImpl extends Querydsl4RepositorySupport implements
    TicketRepositoryCustom {
    public TicketRepositoryImpl(){ super(Ticket.class);}

    @Override
    public Ticket findByIdCustom(Long ticketId) {
        return Optional.ofNullable(selectFrom(ticket)
                .where(ticket.id.eq(ticketId))
                .fetchFirst())
            .orElseThrow(() -> new CustomException(ErrorCode.TICKET_NOT_EXISTS));
    }

    @Override
    public List<Ticket> findByUserCustom(String userEmail) {
        return selectFrom(ticket)
            .where(ticket.user.email.eq(userEmail))
            .fetchJoin()
            .fetch();
    }
}
