package com.repository;

import com.entity.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@EntityScan(basePackages = "com.entity")
@EnableJpaRepositories("com.repository")
@Transactional
@TestPropertySource("classpath:application-test.properties")
public class VoyageRepositoryTest {

    @Autowired
    private VoyageRepository voyageRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BusRepository busRepository;

    @Test
    public void addVoyage(){
        Voyage voyage = new Voyage("Tt83883");
        Voyage dbVoyage = voyageRepository.save(voyage);
        Assert.assertEquals(voyage, dbVoyage);
    }

    @Test
    public void updateBusOnVoyage(){

        Voyage voyage = new Voyage("Tt83883");

        voyageRepository.save(voyage);

        Voyage result = voyageRepository.findOneByNumber("Tt83883");

        Assert.assertNull(result.getBus());

        Bus bus = new Bus("AA1234AA", "Porsche");
        bus = busRepository.save(bus);

        result.setBus(bus);

        Voyage secondResult = voyageRepository.save(result);

        Assert.assertEquals(bus, secondResult.getBus());
    }

    @Test
    public void addBusAddVoyagesDeleteBus(){
        Bus bus = new Bus("AA1234AA", "Porsche");
        Bus bus1 = new Bus("HH5555CC", "Ferrari");

        Bus dbDriver = busRepository.save(bus);
        busRepository.save(bus1);

        Voyage voyage = new Voyage("EEE8889");

        Voyage voyage1 = new Voyage("REW8989");

        Voyage dbVoyage = voyageRepository.save(voyage);
        Voyage dbVoyage1 = voyageRepository.save(voyage1);

        dbVoyage.setBus(bus);
        dbVoyage1.setBus(bus1);

        dbVoyage = voyageRepository.save(dbVoyage);
        voyageRepository.save(dbVoyage1);

        dbVoyage.setBus(null);

        dbVoyage = voyageRepository.save(dbVoyage);

        busRepository.delete(dbDriver.getId());

        Assert.assertEquals(dbVoyage.getBus(), null);
    }

    @Test
    public void createVoyageReturnId(){
        Voyage voyage = new Voyage("EW78787");
        Voyage dbVoyage = voyageRepository.save(voyage);

        Assert.assertNotNull(dbVoyage.getId());
    }

    @Test
    public void voyageAddNextVoyage(){
        Voyage voyage = new Voyage("EW78787");
        voyage = voyageRepository.save(voyage);

        Voyage voyage1 = new Voyage("PP66757");
        voyage1 = voyageRepository.save(voyage1);

        Integer result = voyage1.getId();

        Assert.assertEquals(voyage.getId(), --result);
    }

    @Test
    public void addVoyageWithTheSameNumber(){
        Voyage voyage = new Voyage("EW78787");
        voyage = voyageRepository.save(voyage);

        Voyage voyage1 = new Voyage("EW78787");
        voyage1 = voyageRepository.save(voyage1);

        Assert.assertEquals(voyage, voyage1);

        Assert.assertNotSame(voyage.getId(), voyage1.getId());
    }

    @Test
    public void addVoyageWithTheSameId(){
        Voyage voyage = new Voyage("EW78787");
        voyage = voyageRepository.save(voyage);

        Voyage voyage1 = new Voyage("DD2323");
        voyage1.setId(voyage.getId());
        voyage1 = voyageRepository.save(voyage1);

        Assert.assertEquals(voyageRepository.findOne(voyage.getId()), voyage1);
    }

    @Test
    public void addVoyageAndFindOneByNumber(){
        Voyage voyage = new Voyage("EW78787");
        voyage = voyageRepository.save(voyage);

        Assert.assertEquals(voyageRepository.findOneByNumber("EW78787"), voyage);
    }

    @Test
    public void addVoyageAddBusAndChangeBus(){
        Voyage voyage = new Voyage("EW78787", new Bus("DD87878TT", "PPP"));
        voyageRepository.save(voyage);

        Voyage dbVoyage = voyageRepository.findOneByNumber("EW78787");

        Assert.assertEquals(dbVoyage, voyage);

        Bus bus =  new Bus("BBVSV009", "Donald");
        dbVoyage.setBus(bus);
        voyageRepository.save(dbVoyage);

        Assert.assertEquals(dbVoyage.getBus(), bus);

        ArrayList<Bus> buses = (ArrayList<Bus>)busRepository.findAll();

        Assert.assertTrue(buses.size() == 2);
    }

    @Test
    public void addVoyageSetNumberSetBus(){
        Voyage voyage = new Voyage();
        voyage.setNumber("2344TT");
        voyage.setBus(new Bus("hdjh", "djjdj", new Driver("4254452", "Tom", "Potter")));

        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++){
            tickets.add(new Ticket(i, 2000, false));
        }

        voyage.setTickets(tickets);

        voyageRepository.save(voyage);

        Assert.assertEquals(voyageRepository.findOneByNumber("2344TT"), voyage);

    }

    @Test
    public void UpdateVoyageTicketsTestGetTickets(){
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++){
            tickets.add(new Ticket(i, 2000, false));
        }
        ticketRepository.save(tickets);

        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = voyageRepository.save(voyage).getId();

        Set<Ticket> tickets1 = new HashSet<>((ArrayList<Ticket>)ticketRepository.findAll());
        tickets1.iterator().next().setPaid(true);

        Voyage voyage1 = voyageRepository.findOne(bdVoyageID);

        voyage1.setTickets(tickets1);
        voyageRepository.save(voyage1);

        Assert.assertEquals(new HashSet<Ticket> (voyageRepository.findOne(bdVoyageID).getTickets()),
                new HashSet<>((ArrayList<Ticket>)ticketRepository.findAll()));

    }

    @Test
    public void createNewVoyageWithNewBussAndWithNewTickets(){
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++){
            tickets.add(new Ticket(i, 2000, false));
        }

        Voyage voyage = new Voyage("JJD009", new Bus("KKK", "ydyud"));
        voyage.setTickets(tickets);
        Integer bdVoyageID = voyageRepository.save(voyage).getId();

        Assert.assertNotEquals(new HashSet<Ticket> (voyageRepository.findOne(bdVoyageID).getTickets()),
                new HashSet<>((ArrayList<Ticket>)ticketRepository.findAll()));

    }

    @Test
    public void findOneTicketByVoyageId()throws Exception{
        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = voyageRepository.save(voyage).getId();

        Ticket ticket = new Ticket(1, 2000, false);
        ticket.setVoyage(voyage);

        Integer bdTicketID = ticketRepository.save(ticket).getId();
        List<Ticket> ticketsList = ticketRepository.findAllByVoyage_id(bdVoyageID);
        Ticket ticket1 = ticketsList.get(0);

        Assert.assertEquals("Ticket{id=" + bdTicketID + ", place=1, price=2000, isPaid=false}", ticket1.toString());
    }
}