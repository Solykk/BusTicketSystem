package com.services;

import com.entity.Ticket;

import java.util.List;

public interface TicketService {

    Ticket findOneTicket(Integer id);
    List<Ticket> findAllTickets();

}
