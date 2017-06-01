package integration.com.services;

import com.entity.*;

import com.services.*;

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
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@EntityScan(basePackages = "com.entity")
@EnableJpaRepositories("com.repositories")
@ComponentScan(basePackages = "com.services")
@Transactional
@TestPropertySource("classpath:application-test.properties")
public class VoyageServiceIT {

    private VoyageService voyageService;
    private DriverService driverService;
    private BusService busService;
    private TicketService ticketService;

    @Test
    public void addVoyage() {
        //Given
        Voyage voyage = new Voyage("23334YY");

        //When
        Voyage result = voyageService.addVoyage(voyage);

        //Then
        Assert.assertNotNull(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addVoyageWithTheSameNumber() {
        //Given
        Voyage voyage = new Voyage("GG8888PP");

        //When
        voyageService.addVoyage(voyage);
        voyageService.addVoyage(voyage);

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addVoyageNull() {
        //When
        voyageService.addVoyage(null);

        //Then
        Assert.fail();
    }

    @Test
    public void addVoyageWithTheSameId() {

        Voyage voyage = new Voyage("GG8888PP");
        Integer voyageId = voyageService.addVoyage(voyage).getId();

        try {
            Voyage voyage1 = new Voyage("YY89898WW");
            voyage1.setId(voyageId);
            voyageService.addVoyage(voyage1);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage with id " + voyageId + " exist", e.getMessage());
        }
    }

    @Test
    public void addVoyageWithEmptyFields() {
        try {
            Voyage voyage = new Voyage();
            voyageService.addVoyage(voyage);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage field 'number' can't be null", e.getMessage());
        }
    }

    @Test
    public void addVoyageAndFindAll() {
        Voyage voyage = new Voyage("GG8888PP");
        Integer voyageId = voyageService.addVoyage(voyage).getId();

        Voyage voyage1 = new Voyage("JJ7767RR");
        Integer voyageId1 = voyageService.addVoyage(voyage1).getId();

        Voyage voyage2 = new Voyage("KK9999PP");
        Integer voyageId2 = voyageService.addVoyage(voyage2).getId();

        List<Voyage> voyages = voyageService.findAllVoyages();

        Assert.assertEquals("[Voyage{id=" + voyageId + ", number='GG8888PP', bus=null, tickets=null}, " +
                "Voyage{id=" + voyageId1 + ", number='JJ7767RR', bus=null, tickets=null}, " +
                "Voyage{id=" + voyageId2 + ", number='KK9999PP', bus=null, tickets=null}]", voyages.toString());
    }

    @Test
    public void findAllVoyagesInEmptyTable() {
        List<Voyage> voyages = voyageService.findAllVoyages();

        Assert.assertEquals("[]", voyages.toString());
    }

    @Test
    public void changeBusOnVoyage() {
        Voyage voyage = new Voyage("GG8888PP");
        Integer voyageId = voyageService.addVoyage(voyage).getId();

        Voyage voyage1 = new Voyage("JJ7767RR");
        Integer voyageId1 = voyageService.addVoyage(voyage1).getId();

        Bus bus = new Bus("EE7878II", "Joi");
        Integer busId = busService.addBus(bus).getId();

        Bus bus1 = new Bus("PP7777UU", "Inna");
        Integer busId1 = busService.addBus(bus1).getId();

        Bus bus2 = new Bus("WW6544GG", "Monty");
        Integer busId2 = busService.addBus(bus2).getId();

        voyageService.changeBusOnVoyage(voyageId, busId);
        voyageService.changeBusOnVoyage(voyageId1, busId1);

        try {
            voyageService.changeBusOnVoyage(voyageId, busId1);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Bus " + busId1 + " can't be on different Voyages", e.getMessage());

            Voyage dbVoyage = voyageService.changeBusOnVoyage(voyageId, busId2);

            Assert.assertEquals(new Bus("WW6544GG", "Monty"), dbVoyage.getBus());
        }
    }

    @Test
    public void changeBusWithDriverOnVoyage() {
        Voyage voyage = new Voyage("GG8888PP");
        Integer voyageId = voyageService.addVoyage(voyage).getId();

        Driver driver = new Driver("II6636UU", "Dima", "Polter");
        Integer driverId = driverService.addDriver(driver).getId();

        Bus bus = new Bus("EE7878II", "Joi");
        Integer busId = busService.addBus(bus).getId();

        busService.changeDriverOnBus(busId, driverId);

        Bus bus1 = new Bus("PP7777UU", "Inna");
        Integer busId1 = busService.addBus(bus1).getId();

        voyageService.changeBusOnVoyage(voyageId, busId);
        voyageService.changeBusOnVoyage(voyageId, busId1);

        Bus busResult = busService.findOneBus(busId);
        Bus busResult2 = busService.findOneBus(busId1);

        Assert.assertEquals(busResult.getDriver(), null);
        Assert.assertEquals(busResult2.getDriver(), driver);
    }

    @Test
    public void changeBusOnVoyageImportNull() {
        Assert.assertEquals(new Voyage(), voyageService.changeBusOnVoyage(null, null));
    }

    @Test
    public void changeBusOnVoyageImportNotCorrectId() {
        try {
            voyageService.changeBusOnVoyage(1, 0);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage id or Bus id can't be <= 0", e.getMessage());
        }
    }

    @Test
    public void changeBusOnVoyageImportNotExistId() {
        try {
            voyageService.changeBusOnVoyage(1, 1);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage with id 1 or Bus with id 1 not exist", e.getMessage());
        }
    }

    @Test
    public void addTicketsOnVoyageNullInput() {
        Assert.assertEquals(new Voyage(), voyageService.addTicketsOnVoyage(null, null));
    }

    @Test
    public void addTicketsOnVoyageImportNotCorrectId() {
        try {
            voyageService.addTicketsOnVoyage(-1, new HashSet<Ticket>());
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage id can't be <= 0", e.getMessage());
        }
    }

    @Test
    public void addTicketsOnVoyageImportNotExistId() {
        try {
            voyageService.addTicketsOnVoyage(1, new HashSet<Ticket>());
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage with id 1 not exist", e.getMessage());
        }
    }

    @Test
    public void findAllTicketsInEmptyTable() {
        List<Ticket> tickets = ticketService.findAllTickets();

        Assert.assertEquals("[]", tickets.toString());
    }

    @Test
    public void addTicketsOnVoyage() {
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++) {
            tickets.add(new Ticket(i, 2000));
        }

        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = voyageService.addVoyage(voyage).getId();

        Voyage dbVoyage = voyageService.addTicketsOnVoyage(bdVoyageID, tickets);

        Assert.assertEquals(4, dbVoyage.getTickets().size());
    }

    @Test
    public void addTicketsOnVoyageAddMore() {
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++) {
            tickets.add(new Ticket(i, 2000));
        }

        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = voyageService.addVoyage(voyage).getId();

        voyageService.addTicketsOnVoyage(bdVoyageID, tickets);

        Set<Ticket> tickets1 = new HashSet<>();
        for (int i = 5; i < 10; i++) {
            tickets1.add(new Ticket(i, 2000));
        }

        Voyage dbVoyage1 = voyageService.addTicketsOnVoyage(bdVoyageID, tickets1);
        Assert.assertEquals(9, dbVoyage1.getTickets().size());
    }

    @Test
    public void addTicketsOnVoyageAddMoreWithSamePlace() {
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++) {
            tickets.add(new Ticket(i, 2000));
        }

        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = voyageService.addVoyage(voyage).getId();

        voyageService.addTicketsOnVoyage(bdVoyageID, tickets);

        try {
            voyageService.addTicketsOnVoyage(bdVoyageID, tickets);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Two Tickets with same place can't be", e.getMessage());
        }
    }

    @Test
    public void addTicketsOnVoyageNoTickets() {
        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = voyageService.addVoyage(voyage).getId();

        try {
            voyageService.addTicketsOnVoyage(bdVoyageID, new HashSet<Ticket>());
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("No Tickets", e.getMessage());
        }
    }

    @Test
    public void sellTicketImportNull() {
        Assert.assertEquals(new Voyage(), voyageService.sellTicket(null, null));
    }

    @Test
    public void sellTicketImportNotCorrectId() {
        try {
            voyageService.sellTicket(1, 0);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage id or Ticket id can't be <= 0", e.getMessage());
        }
    }

    @Test
    public void sellTicketImportNotExistId() {
        try {
            voyageService.sellTicket(1, 1);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage with id 1 or Ticket with id 1 not exist", e.getMessage());
        }
    }

    @Test
    public void sellTicketImportSoldId() {
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++) {
            if (i == 1) {
                Ticket ticket = new Ticket(i, 2000);
                ticket.setPaid(true);

                tickets.add(ticket);

            }
            tickets.add(new Ticket(i, 2000));
        }

        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = voyageService.addVoyage(voyage).getId();

        Voyage dbVoyage = voyageService.addTicketsOnVoyage(bdVoyageID, tickets);

        Integer ticketId = null;

        for (Ticket ticket : dbVoyage.getTickets()) {
            if (ticket.isPaid()) {
                ticketId = ticket.getId();
                break;
            }
        }

        try {
            voyageService.sellTicket(bdVoyageID, ticketId);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("The Ticket with id " + ticketId + " is already sold", e.getMessage());
        }
    }

    @Test
    public void sellTicket() {
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++) {
            tickets.add(new Ticket(i, 2000));
        }

        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = voyageService.addVoyage(voyage).getId();

        Voyage dbVoyage = voyageService.addTicketsOnVoyage(bdVoyageID, tickets);

        Integer ticketId = dbVoyage.getTickets().iterator().next().getId();

        Assert.assertEquals(ticketService.findOneTicket(ticketId).isPaid(), false);

        voyageService.sellTicket(bdVoyageID, ticketId);

        Assert.assertEquals(ticketService.findOneTicket(ticketId).isPaid(), true);
    }

    @Test
    public void findOneVoyageNoEntity() {
        Assert.assertEquals(null, voyageService.findOneVoyage(1));
    }

    @Test
    public void findOneVoyageWithEntity() {
        Voyage voyage = new Voyage("YYY8498");
        Integer voyageId = voyageService.addVoyage(voyage).getId();
        Assert.assertEquals(voyage, voyageService.findOneVoyage(voyageId));
    }

    @Autowired
    public void setVoyageService(VoyageService voyageService) {
        this.voyageService = voyageService;
    }

    @Autowired
    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }

    @Autowired
    public void setBusService(BusService busService) {
        this.busService = busService;
    }

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }
}