package com.entity;

import org.junit.Assert;
import org.junit.Test;

public class TicketTest {

    @Test
    public void newTicket(){
        Ticket ticket = new Ticket();

        Assert.assertEquals(ticket.getId(), null);
        Assert.assertEquals(ticket.getPlace(), null);
        Assert.assertEquals(ticket.getPrice(), null);
        Assert.assertEquals(ticket.isPaid(), false);
        Assert.assertEquals(ticket.getVoyage(), null);
    }

    @Test
    public void newTicketWithConstructor(){
        Ticket ticket = new Ticket(1, 30, false);

        Assert.assertEquals(ticket.getId(), null);
        Assert.assertEquals(ticket.getPlace(), new Integer(1));
        Assert.assertEquals(ticket.getPrice(), new Integer(30));
        Assert.assertEquals(ticket.isPaid(), false);
        Assert.assertEquals(ticket.getVoyage(), null);
    }

    @Test
    public void newTicketWithConstructorWithSetId(){
        Ticket ticket = new Ticket(1, 30, false);

        ticket.setId(1);

        Assert.assertEquals(ticket.getId(), new Integer(1));
        Assert.assertEquals(ticket.getPlace(), new Integer(1));
        Assert.assertEquals(ticket.getPrice(), new Integer(30));
        Assert.assertEquals(ticket.isPaid(), false);
        Assert.assertEquals(ticket.getVoyage(), null);
    }

    @Test
    public void newTicketWithConstructorWithSetIdWithSetIsPaid(){
        Ticket ticket = new Ticket(1, 30, false);

        ticket.setId(1);
        ticket.setPaid(true);

        Assert.assertEquals(ticket.getId(), new Integer(1));
        Assert.assertEquals(ticket.getPlace(), new Integer(1));
        Assert.assertEquals(ticket.getPrice(), new Integer(30));
        Assert.assertEquals(ticket.isPaid(), true);
        Assert.assertEquals(ticket.getVoyage(), null);
    }

    @Test
    public void newTicketWithConstructorSetAll(){
        Ticket ticket = new Ticket();

        ticket.setId(1);
        ticket.setPaid(true);
        ticket.setPrice(78);
        ticket.setPlace(2);

        Assert.assertEquals(ticket.getId(), new Integer(1));
        Assert.assertEquals(ticket.getPlace(), new Integer(2));
        Assert.assertEquals(ticket.getPrice(), new Integer(78));
        Assert.assertEquals(ticket.isPaid(), true);
        Assert.assertEquals(ticket.getVoyage(), null);
    }

    @Test
    public void twoTicketEquals(){
        Ticket ticket = new Ticket(1, 30, false);
        Ticket ticket1 = new Ticket(1, 30, false);

        Assert.assertEquals(ticket, ticket1);
    }

    @Test
    public void twoTicketNotEquals(){
        Ticket ticket = new Ticket(1, 30, false);
        Ticket ticket1 = new Ticket(1, 30, true);

        Assert.assertNotEquals(ticket, ticket1);
    }

    @Test
    public void twoTicketEqualsOneWithId(){
        Ticket ticket = new Ticket(1, 30, false);
        ticket.setId(1);

        Ticket ticket1 = new Ticket(1, 30, false);

        Assert.assertEquals(ticket, ticket1);
    }

    @Test
    public void twoTicketHashCode(){
        Ticket ticket = new Ticket(1, 30, false);
        Ticket ticket1 = new Ticket(1, 30, false);

        Assert.assertTrue(ticket.hashCode() == ticket1.hashCode());
    }

    @Test
    public void emptyTicketToString(){
        Ticket ticket= new Ticket();
        String expected = "Ticket{id=null, place=null, price=null, isPaid=false}";

        Assert.assertEquals(expected, ticket.toString());
    }

    @Test
    public void newTicketWithConstructorToString(){
        Ticket ticket = new Ticket(1, 30, false);
        String expected = "Ticket{id=null, place=1, price=30, isPaid=false}";

        Assert.assertEquals(expected, ticket.toString());
    }

    @Test
    public void newTicketWithConstructorWithSetIdToString(){
        Ticket ticket = new Ticket(1, 30, false);
        ticket.setId(1);

        String expected = "Ticket{id=1, place=1, price=30, isPaid=false}";

        Assert.assertEquals(expected, ticket.toString());
    }
}