package com.service;

import com.entity.*;
import com.repository.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@EntityScan(basePackages = "com.entity")
@EnableJpaRepositories("com.repository")
@ComponentScan(basePackages = "com.service")
@Transactional
@TestPropertySource("classpath:application-test.properties")
public class ServiceTest {

    @Autowired
    private Service service;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private VoyageRepository voyageRepository;

    @Test
    public void addDriver() throws Exception{
        Assert.assertNotNull(service.addDriver(new Driver("YIIIPPO", "Poll", "Banana")));
    }

    @Test
    public void addDriverWithTheSameLicense() throws Exception{
        try {
            service.addDriver(new Driver("YIIIPPO", "Poll", "Banana"));
            service.addDriver(new Driver("YIIIPPO", "Niki", "Lauda"));
        } catch (IllegalArgumentException e){
            Assert.assertEquals("Driver with license YIIIPPO exist", e.getMessage());
        }
    }

    @Test
    public void addDriverNull() throws Exception{
        Assert.assertEquals(new Driver(), service.addDriver(null));
    }

    @Test
    public void addDriveWithTheSameId() throws Exception{

        Driver driver = new Driver("RRRERR", "Joi", "Jingle");
        Integer driverId = service.addDriver(driver).getId();

        try {
            Driver driver2 = new Driver("TOTOTO", "Joi", "Jingle");
            driver2.setId(driverId);
            service.addDriver(driver2);
        } catch (IllegalArgumentException e){
            Assert.assertEquals("Driver with id " + driverId + " exist", e.getMessage());
        }
    }

    @Test
    public void addDriveWithEmptyFields() throws Exception{
        try {
            Driver driver = new Driver();
            service.addDriver(driver);
        } catch (IllegalArgumentException e){
            Assert.assertEquals("Driver fields can't be null", e.getMessage());
        }
    }

    @Test
    public void addDrivesAndFindAll() throws Exception{
        Driver driver = new Driver("RRRERR", "Joi", "Jingle");
        Integer driverId = service.addDriver(driver).getId();

        Driver driver2 = new Driver("TOTOTO", "Inna", "Ser");
        Integer driverId2 = service.addDriver(driver2).getId();

        Driver driver3 = new Driver("MO8980", "Monty", "Dill");
        Integer driverId3 = service.addDriver(driver3).getId();

        Set<Driver> drivers = service.findAllDrivers();

        Assert.assertEquals("[Driver{id=" + driverId + ", license='RRRERR', name='Joi', surname='Jingle'}, " +
                                    "Driver{id=" + driverId2 + ", license='TOTOTO', name='Inna', surname='Ser'}, " +
                                    "Driver{id=" + driverId3 + ", license='MO8980', name='Monty', surname='Dill'}]", drivers.toString());
    }

    @Test
    public void findAllDriversInEmptyTable() throws Exception{
        Set<Driver> drivers = service.findAllDrivers();

        Assert.assertEquals("[]", drivers.toString());
    }

    @Test
    public void addBus() throws Exception{
        Assert.assertNotNull(service.addBus(new Bus("GG8888PP", "Gin")));
    }

    @Test
    public void addBusWithTheSameNumber() throws Exception{
        try {
            service.addBus(new Bus("GG8888PP", "Gin"));
            service.addBus(new Bus("GG8888PP", "Harry"));
        } catch (IllegalArgumentException e){
            Assert.assertEquals("Bus with number GG8888PP exist", e.getMessage());
        }
    }

    @Test
    public void addBusNull() throws Exception{
        Assert.assertEquals(new Bus(), service.addBus(null));
    }

    @Test
    public void addBusWithTheSameId() throws Exception{

        Bus bus = new Bus("GG8888PP", "Gin");
        Integer busId = service.addBus(bus).getId();

        try {
            Bus bus1 = new Bus("YY89898WW", "Gor");
            bus1.setId(busId);
            service.addBus(bus1);
        } catch (IllegalArgumentException e){
            Assert.assertEquals("Bus with id " + busId + " exist", e.getMessage());
        }
    }

    @Test
    public void addBusWithEmptyFields() throws Exception{
        try {
            Bus bus = new Bus();
            service.addBus(bus);
        } catch (IllegalArgumentException e){
            Assert.assertEquals("Bus fields 'number' and 'model' can't be null", e.getMessage());
        }
    }

    @Test
    public void addBusesAndFindAll() throws Exception{
        Bus bus = new Bus("GG8888PP", "Gin");
        Integer busId = service.addBus(bus).getId();

        Bus bus1 = new Bus("JJ7767RR", "Pid");
        Integer busId1 = service.addBus(bus1).getId();

        Bus bus2 = new Bus("KK9999PP", "Due");
        Integer busId2 = service.addBus(bus2).getId();

        Set<Bus> buses = service.findAllBuses();

        Assert.assertEquals("[Bus{id=" + busId + ", number='GG8888PP', model='Gin', driver=null}, " +
                "Bus{id=" + busId1 + ", number='JJ7767RR', model='Pid', driver=null}, " +
                "Bus{id=" + busId2 + ", number='KK9999PP', model='Due', driver=null}]", buses.toString());
    }

    @Test
    public void findAllBusesInEmptyTable() throws Exception{
        Set<Bus> buses = service.findAllBuses();

        Assert.assertEquals("[]", buses.toString());
    }

    @Test
    public void changeDriverOnBus() throws Exception{
        Bus bus = new Bus("GG8888PP", "Gin");
        Integer busId = service.addBus(bus).getId();

        Bus bus1 = new Bus("JJ7767RR", "Pid");
        Integer busId1 = service.addBus(bus1).getId();

        Driver driver = new Driver("RRRERR", "Joi", "Jingle");
        Integer driverId = service.addDriver(driver).getId();

        Driver driver1 = new Driver("TOTOTO", "Inna", "Ser");
        Integer driverId1 = service.addDriver(driver1).getId();

        Driver driver2 = new Driver("MO8980", "Monty", "Dill");
        Integer driverId2 = service.addDriver(driver2).getId();

        service.changeDriverOnBus(busId, driverId);
        service.changeDriverOnBus(busId1, driverId1);

        try {
            service.changeDriverOnBus(busId, driverId1);
        } catch (IllegalArgumentException e){
            Assert.assertEquals("Driver " + driverId1 + " can't be on different Buses", e.getMessage());

            Bus dbBus = service.changeDriverOnBus(busId, driverId2);

            Assert.assertEquals(new Driver("MO8980", "Monty", "Dill"), dbBus.getDriver());
        }
    }

    @Test
    public void changeDriverOnBusImportNull() throws Exception{
        Assert.assertEquals(new Bus(), service.changeDriverOnBus(null, null));
    }

    @Test
    public void changeDriverOnBusImportNotCorrectId() throws Exception{
        try{
            service.changeDriverOnBus(1, 0);
        } catch (IllegalArgumentException e){
            Assert.assertEquals("Bus id or Driver id can't be <= 0", e.getMessage());
        }
    }

    @Test
    public void changeDriverOnBusImportNotExistId() throws Exception{
        try{
            service.changeDriverOnBus(1, 1);
        } catch (IllegalArgumentException e){
            Assert.assertEquals("Bus with id 1 or Driver with id 1 not exist", e.getMessage());
        }
    }

    @Test
    public void addVoyage() throws Exception{
        Assert.assertNotNull(service.addVoyage(new Voyage("23334YY")));
    }

    @Test
    public void addVoyageWithTheSameNumber() throws Exception{
        try {
            service.addVoyage(new Voyage("GG8888PP"));
            service.addVoyage(new Voyage("GG8888PP"));
        } catch (IllegalArgumentException e){
            Assert.assertEquals("Voyage with number GG8888PP exist", e.getMessage());
        }
    }

    @Test
    public void addVoyageNull() throws Exception{
        Assert.assertEquals(new Voyage(), service.addVoyage(null));
    }

    @Test
    public void addVoyageWithTheSameId() throws Exception{

        Voyage voyage = new Voyage("GG8888PP");
        Integer voyageId = service.addVoyage(voyage).getId();

        try {
            Voyage voyage1 = new Voyage("YY89898WW");
            voyage1.setId(voyageId);
            service.addVoyage(voyage1);
        } catch (IllegalArgumentException e){
            Assert.assertEquals("Voyage with id " + voyageId + " exist", e.getMessage());
        }
    }

    @Test
    public void addVoyageWithEmptyFields() throws Exception{
        try {
            Voyage voyage = new Voyage();
            service.addVoyage(voyage);
        } catch (IllegalArgumentException e){
            Assert.assertEquals("Voyage field 'number' can't be null", e.getMessage());
        }
    }

    @Test
    public void addVoyageAndFindAll() throws Exception{
        Voyage voyage = new Voyage("GG8888PP");
        Integer voyageId = service.addVoyage(voyage).getId();

        Voyage voyage1 = new Voyage("JJ7767RR");
        Integer voyageId1 = service.addVoyage(voyage1).getId();

        Voyage voyage2 = new Voyage("KK9999PP");
        Integer voyageId2 = service.addVoyage(voyage2).getId();

        Set<Voyage> voyages = service.findAllVoyages();

        Assert.assertEquals("[Voyage{id=" + voyageId + ", number='GG8888PP', bus=null, tickets=null}, " +
                "Voyage{id=" + voyageId1 + ", number='JJ7767RR', bus=null, tickets=null}, " +
                "Voyage{id=" + voyageId2 + ", number='KK9999PP', bus=null, tickets=null}]", voyages.toString());
    }

    @Test
    public void findAllVoyagesInEmptyTable() throws Exception{
        Set<Voyage> voyages = service.findAllVoyages();

        Assert.assertEquals("[]", voyages.toString());
    }

    @Test
    public void changeBusOnVoyage() throws Exception{
        Voyage voyage = new Voyage("GG8888PP");
        Integer voyageId = service.addVoyage(voyage).getId();

        Voyage voyage1 = new Voyage("JJ7767RR");
        Integer voyageId1 = service.addVoyage(voyage1).getId();

        Bus bus = new Bus("EE7878II", "Joi");
        Integer busId = service.addBus(bus).getId();

        Bus bus1 = new Bus("PP7777UU", "Inna");
        Integer busId1 = service.addBus(bus1).getId();

        Bus bus2 = new Bus("WW6544GG", "Monty");
        Integer busId2 = service.addBus(bus2).getId();

        service.changeBusOnVoyage(voyageId, busId);
        service.changeBusOnVoyage(voyageId1, busId1);

        try {
            service.changeBusOnVoyage(voyageId, busId1);
        } catch (IllegalArgumentException e){
            Assert.assertEquals("Bus " + busId1 + " can't be on different Voyages", e.getMessage());

            Voyage dbVoyage = service.changeBusOnVoyage(voyageId, busId2);

            Assert.assertEquals(new Bus("WW6544GG", "Monty"), dbVoyage.getBus());
        }
    }

    @Test
    public void changeBusWithDriverOnVoyage() throws Exception{
        Voyage voyage = new Voyage("GG8888PP");
        Integer voyageId = service.addVoyage(voyage).getId();

        Driver driver = new Driver("II6636UU", "Dima", "Polter");
        Integer driverId = service.addDriver(driver).getId();

        Bus bus = new Bus("EE7878II", "Joi");
        Integer busId = service.addBus(bus).getId();

        service.changeDriverOnBus(busId, driverId);

        Bus bus1 = new Bus("PP7777UU", "Inna");
        Integer busId1 = service.addBus(bus1).getId();

        service.changeBusOnVoyage(voyageId, busId);
        service.changeBusOnVoyage(voyageId, busId1);

        Bus busResult = busRepository.findOne(busId);
        Bus busResult2 = busRepository.findOne(busId1);

        Assert.assertEquals(busResult.getDriver(), null);
        Assert.assertEquals(busResult2.getDriver(), driver);
    }

    @Test
    public void changeBusOnVoyageImportNull() throws Exception{
        Assert.assertEquals(new Voyage(), service.changeBusOnVoyage(null, null));
    }

    @Test
    public void changeBusOnVoyageImportNotCorrectId() throws Exception{
        try{
            service.changeBusOnVoyage(1, 0);
        } catch (IllegalArgumentException e){
            Assert.assertEquals("Voyage id or Bus id can't be <= 0", e.getMessage());
        }
    }

    @Test
    public void changeBusOnVoyageImportNotExistId() throws Exception{
        try{
            service.changeBusOnVoyage(1, 1);
        } catch (IllegalArgumentException e){
            Assert.assertEquals("Voyage with id 1 or Bus with id 1 not exist", e.getMessage());
        }
    }

    @Test
    public void addTicketsOnVoyageNullInput() throws Exception {
        Assert.assertEquals(new Voyage(), service.addTicketsOnVoyage(null, null));
    }

    @Test
    public void addTicketsOnVoyageImportNotCorrectId() throws Exception{
        try{
            service.addTicketsOnVoyage(-1, new HashSet<Ticket>());
        } catch (IllegalArgumentException e){
            Assert.assertEquals("Voyage id can't be <= 0", e.getMessage());
        }
    }

    @Test
    public void addTicketsOnVoyageImportNotExistId() throws Exception{
        try{
            service.addTicketsOnVoyage(1,  new HashSet<Ticket>());
        } catch (IllegalArgumentException e){
            Assert.assertEquals("Voyage with id 1 not exist", e.getMessage());
        }
    }

    @Test
    public void findAllTicketsInEmptyTable() throws Exception{
        Set<Ticket> tickets = service.findAllTickets();

        Assert.assertEquals("[]", tickets.toString());
    }

    @Test
    public void addTicketsOnVoyage() throws Exception{
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++){
            tickets.add(new Ticket(i, 2000, false));
        }

        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = service.addVoyage(voyage).getId();

        Voyage dbVoyage = service.addTicketsOnVoyage(bdVoyageID, tickets);

        Assert.assertEquals(4, dbVoyage.getTickets().size());
    }

    @Test
    public void addTicketsOnVoyageAddMore() throws Exception{
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++){
            tickets.add(new Ticket(i, 2000, false));
        }

        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = service.addVoyage(voyage).getId();

        service.addTicketsOnVoyage(bdVoyageID, tickets);

        Set<Ticket> tickets1 = new HashSet<>();
        for (int i = 5; i < 10; i++){
            tickets1.add(new Ticket(i, 2000, false));
        }

        Voyage dbVoyage1 = service.addTicketsOnVoyage(bdVoyageID, tickets1);
        Assert.assertEquals(9, dbVoyage1.getTickets().size());
    }

    @Test
    public void addTicketsOnVoyageAddMoreWithSamePlace() throws Exception{
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++){
            tickets.add(new Ticket(i, 2000, false));
        }

        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = service.addVoyage(voyage).getId();

        service.addTicketsOnVoyage(bdVoyageID, tickets);

        try {
            service.addTicketsOnVoyage(bdVoyageID, tickets);
        } catch (IllegalArgumentException e){
            Assert.assertEquals("Two Tickets with same place can't be", e.getMessage());
        }
    }

    @Test
    public void addTicketsOnVoyageNoTickets() throws Exception{
        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = service.addVoyage(voyage).getId();

        try {
            service.addTicketsOnVoyage(bdVoyageID, new HashSet<Ticket>());
        } catch (IllegalArgumentException e){
            Assert.assertEquals("No Tickets", e.getMessage());
        }
    }

    @Test
    public void sellTicketImportNull() throws Exception{
        Assert.assertEquals(new Voyage(), service.sellTicket(null, null));
    }

    @Test
    public void sellTicketImportNotCorrectId() throws Exception{
        try{
            service.sellTicket(1, 0);
        } catch (IllegalArgumentException e){
            Assert.assertEquals("Voyage id or Ticket id can't be <= 0", e.getMessage());
        }
    }

    @Test
    public void sellTicketImportNotExistId() throws Exception{
        try{
            service.sellTicket(1, 1);
        } catch (IllegalArgumentException e){
            Assert.assertEquals("Voyage with id 1 or Ticket with id 1 not exist", e.getMessage());
        }
    }

    @Test
    public void sellTicketImportSoldId() throws Exception{
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++){
            if(i == 1){
                tickets.add(new Ticket(i, 2000, true));
            }
            tickets.add(new Ticket(i, 2000, false));
        }

        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = service.addVoyage(voyage).getId();

        Voyage dbVoyage = service.addTicketsOnVoyage(bdVoyageID, tickets);

        Integer ticketId = null;

        for (Ticket ticket : dbVoyage.getTickets()) {
            if(ticket.isPaid()){
                ticketId = ticket.getId();
                break;
            }
        }

        try{
            service.sellTicket(bdVoyageID, ticketId);
        } catch (IllegalArgumentException e){
            Assert.assertEquals("The Ticket with id " + ticketId + " is already sold", e.getMessage());
        }
    }

    @Test
    public void sellTicket() throws Exception{
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++){
            tickets.add(new Ticket(i, 2000, false));
        }

        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = service.addVoyage(voyage).getId();

        Voyage dbVoyage = service.addTicketsOnVoyage(bdVoyageID, tickets);

        Integer ticketId = dbVoyage.getTickets().iterator().next().getId();

        Assert.assertEquals(ticketRepository.findOne(ticketId).isPaid(), false);

        service.sellTicket(bdVoyageID, ticketId);

        Assert.assertEquals(ticketRepository.findOne(ticketId).isPaid(), true);

    }

    @Test
    public void findOneDriverNoEntity(){
        Assert.assertEquals(null, service.findOneDriver(1));
    }

    @Test
    public void findOneDriverWithEntity(){
        Driver driver = new Driver("HHd777", "Joi", "Tirb");
        Integer driverId = driverRepository.save(driver).getId();
        Assert.assertEquals(driver, service.findOneDriver(driverId));
    }

    @Test
    public void findOneBusNoEntity(){
        Assert.assertEquals(null, service.findOneBus(1));
    }

    @Test
    public void findOneBusWithEntity(){
        Bus bus = new Bus("HHd777", "uU898");
        Integer busId = busRepository.save(bus).getId();
        Assert.assertEquals(bus, service.findOneBus(busId));
    }

    @Test
    public void findOneTicketNoEntity(){
        Assert.assertEquals(null, service.findOneTicket(1));
    }

    @Test
    public void findOneTicketWithEntity(){
        Ticket ticket = new Ticket(1, 90);
        Integer ticketId = ticketRepository.save(ticket).getId();
        Assert.assertEquals(ticket, service.findOneTicket(ticketId));
    }

    @Test
    public void findOneVoyageNoEntity(){
        Assert.assertEquals(null, service.findOneVoyage(1));
    }

    @Test
    public void findOneVoyageWithEntity(){
        Voyage voyage = new Voyage("YYY8498");
        Integer voyageId = voyageRepository.save(voyage).getId();
        Assert.assertEquals(voyage, service.findOneVoyage(voyageId));
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public TicketRepository getTicketRepository() {
        return ticketRepository;
    }

    public void setTicketRepository(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public BusRepository getBusRepository() {
        return busRepository;
    }

    public void setBusRepository(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public DriverRepository getDriverRepository() {
        return driverRepository;
    }

    public void setDriverRepository(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public VoyageRepository getVoyageRepository() {
        return voyageRepository;
    }

    public void setVoyageRepository(VoyageRepository voyageRepository) {
        this.voyageRepository = voyageRepository;
    }
}