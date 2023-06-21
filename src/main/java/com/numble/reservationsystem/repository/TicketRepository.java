package com.numble.reservationsystem.repository;


import com.numble.reservationsystem.entity.domain.Ticket;
import com.numble.reservationsystem.entity.domain.User;
import com.numble.reservationsystem.entity.dto.ticket.TicketResponseDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByUser(User user);
}
