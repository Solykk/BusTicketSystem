package com.services;

import com.entity.Ticket;
import com.entity.Voyage;

import java.util.List;
import java.util.Set;

public interface VoyageService {

    Voyage addVoyage(Voyage voyage);
    Voyage changeBusOnVoyage(Integer voyageId,  Integer busId);
    Voyage addTicketsOnVoyage(Integer voyageId, Set<Ticket> tickets);
    Voyage sellTicket(Integer voyageId, Integer ticketId);

    Voyage findOneVoyage(Integer id);
    List<Voyage> findAllVoyages();

}
