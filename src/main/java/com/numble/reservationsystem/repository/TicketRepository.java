package com.numble.reservationsystem.repository;


import com.numble.reservationsystem.entity.domain.Ticket;
import com.numble.reservationsystem.entity.domain.User;
import com.numble.reservationsystem.entity.dto.ticket.TicketResponseDto;
import com.numble.reservationsystem.repository.Custom.TicketRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long>, TicketRepositoryCustom {

    List<Ticket> findByUser(User user);
}
