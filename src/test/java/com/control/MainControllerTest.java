package com.control;

import com.entity.*;

import com.service.Service;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.context.annotation.ComponentScan;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@EntityScan(basePackages = "com.entity")
@EnableJpaRepositories("com.repository")
@ComponentScan(basePackages = "com.service, com.control")
@Transactional
@TestPropertySource("classpath:application-test.properties")
public class MainControllerTest {

    @Autowired
    private MainController mainController;

    @Autowired
    private Service service;

    @Test
    public void addDriver(){
        ResponseEntity<?> expected = new ResponseEntity<Driver>(new Driver("HHFH33", "Bob", "Pupkon"),HttpStatus.OK);
        Assert.assertEquals(expected, mainController.addDriver(new Driver("HHFH33", "Bob", "Pupkon")));
    }

    @Test
    public void addDriverError(){
        mainController.addDriver(new Driver("HHFH33", "Bob", "Pupkon"));
        ResponseEntity<?> expected = new ResponseEntity<String>("Driver with license HHFH33 exist", HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, mainController.addDriver(new Driver("HHFH33", "Bob", "Pupkon")));
    }

    @Test
    public void addBus(){
        ResponseEntity<?> expected = new ResponseEntity<Bus>(new Bus("FF0090OO", "Ferrari"),HttpStatus.OK);
        Assert.assertEquals(expected, mainController.addBus(new Bus("FF0090OO", "Ferrari")));
    }

    @Test
    public void addBusError(){
        mainController.addBus(new Bus("FF0090OO", "Ferrari"));
        ResponseEntity<?> expected = new ResponseEntity<String>("Bus with number FF0090OO exist", HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, mainController.addBus(new Bus("FF0090OO", "Ferrari")));
    }

    @Test
    public void addVoyage(){
        ResponseEntity<?> expected = new ResponseEntity<Voyage>(new Voyage("123R"),HttpStatus.OK);
        Assert.assertEquals(expected, mainController.addVoyage(new Voyage("123R")));
    }

    @Test
    public void addVoyageError(){
        mainController.addVoyage(new Voyage("123R"));
        ResponseEntity<?> expected = new ResponseEntity<String>("Voyage with number 123R exist", HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, mainController.addVoyage(new Voyage("123R")));
    }

    @Test
    public void addDriverOnBus() throws Exception{
        Driver driver = service.addDriver(new Driver("HHFH33", "Bob", "Pupkon"));
        Bus bus = service.addBus(new Bus("FF0090OO", "Ferrari"));

        ResponseEntity<?> expected = new ResponseEntity<Bus>(new Bus("FF0090OO", "Ferrari",
                new Driver("HHFH33", "Bob", "Pupkon")),HttpStatus.OK);
        Assert.assertEquals(expected, mainController.changeDriverOnBus(bus.getId(), driver.getId()));
    }

    @Test
    public void addDriverOnBusError() throws Exception{
        Driver driver = service.addDriver(new Driver("HHFH33", "Bob", "Pupkon"));
        Bus bus = service.addBus(new Bus("FF0090OO", "Ferrari"));
        mainController.changeDriverOnBus(bus.getId(), driver.getId());
        ResponseEntity<?> expected = new ResponseEntity<String>("Driver with id "
                + driver.getId() + " already on Bus " + bus.getId(), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, mainController.changeDriverOnBus(bus.getId(), driver.getId()));
    }

    @Test
    public void changeBusOnVoyage() throws Exception{
        Bus bus = service.addBus(new Bus("FF0090OO", "Ferrari"));
        Voyage voyage = service.addVoyage(new Voyage("YYY6"));

        ResponseEntity<?> expected = new ResponseEntity<Voyage>(new Voyage("YYY6",
                new Bus("FF0090OO", "Ferrari")),HttpStatus.OK);
        Assert.assertEquals(expected, mainController.changeBusOnVoyage(voyage.getId(), bus.getId()));
    }

    @Test
    public void changeBusOnVoyageError() throws Exception{
        Bus bus = service.addBus(new Bus("FF0090OO", "Ferrari"));
        Voyage voyage = service.addVoyage(new Voyage("YYY6"));
        mainController.changeBusOnVoyage(voyage.getId(), bus.getId());

        ResponseEntity<?> expected = new ResponseEntity<String>("Bus with id " + bus.getId()
                + " already on Voyage " + voyage.getId(), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, mainController.changeBusOnVoyage(voyage.getId(), bus.getId()));
    }

    @Test
    public void addTicketOnVoyage() throws Exception{
        Voyage voyage = service.addVoyage(new Voyage("YYY6"));

        Set<Ticket> tickets = new HashSet<>();
        tickets.add(new Ticket(1, 2000));

        Voyage expectedVoyage = new Voyage("YYY6");
        expectedVoyage.setId(voyage.getId());
        expectedVoyage.setTickets(tickets);

        ResponseEntity<?> expected = new ResponseEntity<Voyage>(expectedVoyage, HttpStatus.OK);
        Assert.assertEquals(expected, mainController.addTicketOnVoyage(voyage.getId(), new Ticket(1, 2000)));
    }

    @Test
    public void addTicketOnVoyageError() throws Exception{
        Voyage voyage = service.addVoyage(new Voyage("YYY6"));

        mainController.addTicketOnVoyage(voyage.getId(), new Ticket(1, 2000));

        ResponseEntity<?> expected = new ResponseEntity<String>("Two Tickets with same place can't be",
                HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, mainController.addTicketOnVoyage(voyage.getId(), new Ticket(1, 2000)));
    }

    @Test
    public void addTicketsOnVoyage() throws Exception{
        Voyage voyage = service.addVoyage(new Voyage("YYY6"));

        ArrayList<Ticket> tickets = new ArrayList<>();
        for (int i = 1; i < 5; i++){
            tickets.add(new Ticket(i, 2000, false));
        }

        Voyage expectedVoyage = new Voyage("YYY6");
        expectedVoyage.setId(voyage.getId());
        expectedVoyage.setTickets(new HashSet<Ticket>(tickets));

        ResponseEntity<?> expected = new ResponseEntity<Voyage>(expectedVoyage, HttpStatus.OK);
        Assert.assertEquals(expected, mainController.addTicketsOnVoyage(voyage.getId(), tickets));
    }

    @Test
    public void addTicketsOnVoyageError() throws Exception{
        Voyage voyage = service.addVoyage(new Voyage("YYY6"));

        ArrayList<Ticket> tickets = new ArrayList<>();
        for (int i = 1; i < 5; i++){
            tickets.add(new Ticket(i, 2000, false));
        }

        mainController.addTicketsOnVoyage(voyage.getId(), tickets);

        ResponseEntity<?> expected = new ResponseEntity<String>("Two Tickets with same place can't be",
                HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, mainController.addTicketsOnVoyage(voyage.getId(), tickets));
    }

    @Test
    public void sellTickets() throws Exception{
        Voyage voyage = service.addVoyage(new Voyage("YYY6"));
        ArrayList<Ticket> tickets = new ArrayList<>();
        for (int i = 1; i < 5; i++){
            tickets.add(new Ticket(i, 2000));
        }

        mainController.addTicketsOnVoyage(voyage.getId(), tickets);

        Voyage dbVoyage  = service.findOneVoyage(voyage.getId());

        Integer ticketId = dbVoyage.getTickets().iterator().next().getId();

        Ticket ticket = service.findOneTicket(ticketId);

        ArrayList<Ticket> ticketsRes = new ArrayList<>();
        for (int i = 1; i < 5; i++){
            if(i ==  ticket.getPlace()){
                ticketsRes.add(new Ticket(i, 2000, true));
            } else {
                ticketsRes.add(new Ticket(i, 2000));
            }
        }

        Voyage expectedVoyage = new Voyage("YYY6");
        expectedVoyage.setId(voyage.getId());
        expectedVoyage.setTickets(new HashSet<Ticket>(ticketsRes));

        ResponseEntity<?> expected = new ResponseEntity<Voyage>(expectedVoyage, HttpStatus.OK);
        Assert.assertEquals(expected, mainController.sellTicket(voyage.getId(), ticketId));
    }

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}