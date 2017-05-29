package com.service;

import com.entity.*;
import com.repository.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.context.annotation.Configuration;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.*;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = "com.entity")
@EnableJpaRepositories("com.repository")
@org.springframework.stereotype.Service
public class Service implements ReSTFulAPI {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private VoyageRepository voyageRepository;

    public Service() {
    }

    @Override
    public Driver addDriver(Driver driver) throws Exception{
        if(driver == null){
            return new Driver();
        }
        if(driver.getLicense() == null || driver.getName() == null || driver.getSurname() == null){
            throw new IllegalArgumentException("Driver fields can't be null");
        }
        if(driver.getId() != null && driverRepository.exists(driver.getId())){
            throw new IllegalArgumentException("Driver with id " + driver.getId() + " exist");
        }
        if(driverRepository.findOneByLicense(driver.getLicense()) != null){
            throw new IllegalArgumentException("Driver with license " + driver.getLicense() + " exist");
        }

        return driverRepository.save(driver);
    }

    @Override
    public Bus addBus(Bus bus) throws Exception{
        if(bus == null){
            return new Bus();
        }
        if(bus.getNumber() == null || bus.getModel() == null){
            throw new IllegalArgumentException("Bus fields 'number' and 'model' can't be null");
        }
        if(bus.getId() != null && busRepository.exists(bus.getId())){
            throw new IllegalArgumentException("Bus with id " + bus.getId() + " exist");
        }
        if(busRepository.findOneByNumber(bus.getNumber()) != null){
            throw new IllegalArgumentException("Bus with number " + bus.getNumber() + " exist");
        }

        return busRepository.save(bus);
    }

    @Override
    public Bus changeDriverOnBus(Integer busId, Integer driverId) throws Exception{
        if(busId == null || driverId == null){
            return new Bus();
        }
        if(busId <= 0 || driverId <= 0){
            throw new IllegalArgumentException("Bus id or Driver id can't be <= 0");
        }
        if(!driverRepository.exists(driverId) || !busRepository.exists(busId)){
            throw new IllegalArgumentException("Bus with id " + busId + " or Driver with id " + driverId + " not exist");
        }

        Bus bus = busRepository.findOne(busId);
        if(bus.getDriver() != null && bus.getDriver().getId().equals(driverId)){
            throw new IllegalArgumentException("Driver with id " + driverId + " already on Bus " + busId);
        }

        Driver driver = driverRepository.findOne(driverId);

        Set<Bus> buses = findAllBuses();
        for (Bus dbBus: buses ) {
            if(dbBus.getDriver() != null) {
                if (dbBus.getDriver().getId() == driverId && dbBus.getId() != busId) {
                    throw new IllegalArgumentException("Driver " + driverId + " can't be on different Buses");
                }
            }
        }

        bus.setDriver(driver);

        return busRepository.save(bus);
    }

    @Override
    public Voyage addVoyage(Voyage voyage) throws Exception {
        if(voyage == null){
            return new Voyage();
        }
        if(voyage.getNumber() == null){
            throw new IllegalArgumentException("Voyage field 'number' can't be null");
        }
        if(voyage.getId() != null && voyageRepository.exists(voyage.getId())){
            throw new IllegalArgumentException("Voyage with id " + voyage.getId() + " exist");
        }
        if(voyageRepository.findOneByNumber(voyage.getNumber()) != null){
            throw new IllegalArgumentException("Voyage with number " + voyage.getNumber() + " exist");
        }

        return voyageRepository.save(voyage);
    }

    @Override
    public Voyage changeBusOnVoyage(Integer voyageId, Integer busId) throws Exception{
        if(voyageId == null || busId == null){
            return new Voyage();
        }
        if(voyageId <= 0 || busId <= 0){
            throw new IllegalArgumentException("Voyage id or Bus id can't be <= 0");
        }
        if(!busRepository.exists(busId) || !voyageRepository.exists(voyageId)){
            throw new IllegalArgumentException("Voyage with id " + voyageId + " or Bus with id " + busId + " not exist");
        }

        Voyage voyage = voyageRepository.findOne(voyageId);
        if(voyage.getBus() != null && voyage.getBus().getId().equals(busId)){
            throw new IllegalArgumentException("Bus with id " + busId + " already on Voyage " + voyageId);
        }

        Bus bus = busRepository.findOne(busId);

        Set<Voyage> voyages = findAllVoyages();
        for (Voyage dbVoyage: voyages ) {
            if(dbVoyage.getBus() != null) {
                if (dbVoyage.getBus().getId() == busId && dbVoyage.getId() != voyageId) {
                    throw new IllegalArgumentException("Bus " + busId + " can't be on different Voyages");
                }
            }
        }

        if(voyage.getBus() != null) {
            if (voyage.getBus().getDriver() != null) {
                Driver driver = voyage.getBus().getDriver();
                voyage.getBus().setDriver(null);

                voyageRepository.save(voyage);

                bus.setDriver(driver);
            }
        }

        voyage.setBus(bus);

        return voyageRepository.save(voyage);
    }

    @Override
    public Voyage addTicketsOnVoyage(Integer voyageId, Set<Ticket> tickets) throws Exception {
        if(voyageId == null || tickets == null){
            return new Voyage();
        }
        if(voyageId <= 0){
            throw new IllegalArgumentException("Voyage id can't be <= 0");
        }
        if(!voyageRepository.exists(voyageId)){
            throw new IllegalArgumentException("Voyage with id " + voyageId + " not exist");
        }
        if(tickets.size() == 0){
            throw new IllegalArgumentException("No Tickets");
        }

        Voyage dbVoyage = voyageRepository.findOne(voyageId);

        for (Ticket ticket : tickets) {
            if(ticket != null && (ticket.getVoyage() != null || dbVoyage.getTickets() != null)) {
                for(Ticket voyageTicket: dbVoyage.getTickets()){
                    if (voyageTicket.getPlace().equals(ticket.getPlace())) {
                        throw new IllegalArgumentException("Two Tickets with same place can't be");
                    }
                }
            }
        }

        Set<Ticket> dbTickets = new HashSet<>((ArrayList<Ticket>)ticketRepository.save(tickets));
        for (Ticket ticket: dbTickets) {
            ticket.setVoyage(dbVoyage);
        }
        if(dbVoyage.getTickets() != null){
            dbVoyage.getTickets().addAll(tickets);
        } else {
            dbVoyage.setTickets(tickets);
        }

        ticketRepository.save(dbTickets);
        return voyageRepository.save(dbVoyage);
    }

    @Override
    public Voyage sellTicket(Integer voyageId, Integer ticketId) throws Exception {
        if(voyageId == null || ticketId == null){
            return new Voyage();
        }
        if(voyageId <= 0 || ticketId <= 0){
            throw new IllegalArgumentException("Voyage id or Ticket id can't be <= 0");
        }
        if(!voyageRepository.exists(voyageId) || !ticketRepository.exists(ticketId)){
            throw new IllegalArgumentException("Voyage with id " + voyageId + " or Ticket with id " + ticketId + " not exist");
        }

        List<Ticket> tickets = ticketRepository.findAllByVoyage_id(voyageId);

        for (Ticket ticket : tickets) {
            if(ticket.getId() == ticketId && ticket.isPaid()){
                throw new IllegalArgumentException("The Ticket with id " + ticketId + " is already sold");
            }
        }

        Voyage dbVoyage = voyageRepository.findOne(voyageId);

        Ticket dbTicket = ticketRepository.findOne(ticketId);
        dbTicket.setPaid(true);

        ticketRepository.save(dbTicket);

        return voyageRepository.save(dbVoyage);
    }

    @Override
    public Set<Driver> findAllDrivers() {
        return new LinkedHashSet<>((List<Driver>)driverRepository.findAll());
    }

    @Override
    public Set<Bus> findAllBuses() {
        return new LinkedHashSet<>((List<Bus>)busRepository.findAll());
    }

    @Override
    public Set<Ticket> findAllTickets() {
        return new LinkedHashSet<>((List<Ticket>)ticketRepository.findAll());
    }

    @Override
    public Set<Voyage> findAllVoyages() {
        return new LinkedHashSet<>((List<Voyage>)voyageRepository.findAll());
    }

    @Override
    public Driver findOneDriver(Integer id) {
        return driverRepository.findOne(id);
    }

    @Override
    public Bus findOneBus(Integer id) {
        return busRepository.findOne(id);
    }

    @Override
    public Ticket findOneTicket(Integer id) {
        return ticketRepository.findOne(id);
    }

    @Override
    public Voyage findOneVoyage(Integer id) {
        return voyageRepository.findOne(id);
    }
}
