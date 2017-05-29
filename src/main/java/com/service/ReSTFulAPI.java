package com.service;

import com.entity.*;

import java.util.Set;

public interface ReSTFulAPI {

    Driver addDriver(Driver driver) throws Exception;
    Bus addBus(Bus bus) throws Exception;
    Bus changeDriverOnBus(Integer busId, Integer driverId) throws Exception;
    Voyage addVoyage(Voyage voyage) throws Exception;
    Voyage changeBusOnVoyage(Integer voyageId,  Integer busId) throws Exception;
    Voyage addTicketsOnVoyage(Integer voyageId, Set<Ticket> tickets) throws Exception;
    Voyage sellTicket(Integer voyageId, Integer ticketId) throws Exception;

    Set<Driver> findAllDrivers();
    Set<Bus> findAllBuses();
    Set<Ticket> findAllTickets();
    Set<Voyage> findAllVoyages();

    Driver findOneDriver(Integer id);
    Bus findOneBus(Integer id);
    Ticket findOneTicket(Integer id);
    Voyage findOneVoyage(Integer id);

}
