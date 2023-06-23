package com.numble.reservationsystem.repository.Custom;

import com.numble.reservationsystem.entity.domain.Ticket;
import java.util.List;

public interface TicketRepositoryCustom {

    Ticket findByIdCustom(Long ticketId);

    List<Ticket> findByUserCustom(String userEmail);
}
