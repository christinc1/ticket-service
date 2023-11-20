package com.chris.learn.springcloud.ticketservice.repository;

import com.chris.learn.springcloud.ticketservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
