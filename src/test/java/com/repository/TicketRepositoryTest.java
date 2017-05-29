package com.repository;

import com.entity.Ticket;

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
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@EntityScan(basePackages = "com.entity")
@EnableJpaRepositories("com.repository")
@Transactional
@TestPropertySource("classpath:application-test.properties")
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    public void addSetTickets(){
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 31; i++){
            tickets.add(new Ticket(i, 2000, false));
        }

        ticketRepository.save(tickets);

        Set<Ticket> ticketsDb = new HashSet<>((ArrayList<Ticket>)ticketRepository.findAll());

        Assert.assertEquals(tickets, ticketsDb);
    }

    @Test
    public void addTicket(){
        Ticket ticket = new Ticket(1, 30, false);
        Ticket dbTicket = ticketRepository.save(ticket);
        Assert.assertEquals(ticket, dbTicket);
    }

    @Test
    public void createTicketReturnId(){
        Ticket ticket = new Ticket(1, 30, false);
        ticket = ticketRepository.save(ticket);

        Assert.assertNotNull(ticket.getId());
    }

    @Test
    public void ticketAddNextTicket(){
        Ticket ticket = new Ticket(1, 30, false);
        ticket = ticketRepository.save(ticket);

        Ticket ticket1 = new Ticket(2, 34, false);
        ticket1 = ticketRepository.save(ticket1);

        Integer result = ticket1.getId();

        Assert.assertEquals(ticket.getId(), --result);
    }

    @Test
    public void addTicketWithTheSamePlace(){
        Ticket ticket = new Ticket(1, 30, false);
        ticket = ticketRepository.save(ticket);

        Ticket ticket2 = new Ticket(1, 30, false);
        ticket2 = ticketRepository.save(ticket2);

        Assert.assertEquals(ticket, ticket2);

        Assert.assertNotSame(ticket.getId(), ticket2.getId());
    }

    @Test
    public void addTicketWithTheSameId(){
        Ticket ticket = new Ticket(1, 30, false);
        ticket = ticketRepository.save(ticket);

        Ticket ticket2 = new Ticket(1, 30, false);
        ticket2.setId(ticket.getId());
        ticketRepository.save(ticket2);

        Assert.assertEquals(ticketRepository.findOne(ticket.getId()), ticket2);
    }

    @Test
    public void addTicketAndFindOneByPlace(){
        Ticket driver = new Ticket(1, 30, false);
        ticketRepository.save(driver);

        Assert.assertEquals(ticketRepository.findOneByPlace(1), driver);
    }
}