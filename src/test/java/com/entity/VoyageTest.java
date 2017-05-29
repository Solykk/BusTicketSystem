package com.entity;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class VoyageTest {

    @Test
    public void newVoyage(){
        Voyage voyage = new Voyage();

        Assert.assertEquals(voyage.getId(), null);
        Assert.assertEquals(voyage.getNumber(), null);
        Assert.assertEquals(voyage.getBus(), null);
        Assert.assertEquals(voyage.getTickets(), null);
    }

    @Test
    public void newVoyageWithConstructor(){
        Voyage voyage = new Voyage("FF7777JJ", new Bus());

        Assert.assertEquals(voyage.getId(), null);
        Assert.assertEquals(voyage.getNumber(), "FF7777JJ");
        Assert.assertEquals(voyage.getBus(), new Bus());
        Assert.assertEquals(voyage.getTickets(), null);
    }

    @Test
    public void newVoyageWithConstructorWithoutBus(){
        Voyage voyage = new Voyage("FF7777JJ");

        Assert.assertEquals(voyage.getId(), null);
        Assert.assertEquals(voyage.getNumber(), "FF7777JJ");
        Assert.assertEquals(voyage.getBus(), null);
        Assert.assertEquals(voyage.getTickets(), null);
    }

    @Test
    public void newVoyageWithConstructorWithSetId(){
        Voyage voyage = new Voyage("AH7788IO");

        voyage.setId(1);

        Assert.assertEquals(voyage.getId(), new Integer(1));
        Assert.assertEquals(voyage.getNumber(), "AH7788IO");
        Assert.assertEquals(voyage.getBus(), null);
        Assert.assertEquals(voyage.getTickets(), null);
    }

    @Test
    public void newVoyageWithConstructorWithSetIdWithSetBus(){
        Voyage voyage = new Voyage("AH7788IO");

        voyage.setId(1);
        voyage.setBus(new Bus());

        Assert.assertEquals(voyage.getId(), new Integer(1));
        Assert.assertEquals(voyage.getNumber(), "AH7788IO");
        Assert.assertEquals(voyage.getBus(), new Bus());
        Assert.assertEquals(voyage.getTickets(), null);
    }

    @Test
    public void twoVoyagesEquals(){
        Voyage voyage = new Voyage("AH7788IO");
        Voyage voyage2 = new Voyage("AH7788IO");

        Assert.assertEquals(voyage, voyage2);
    }

    @Test
    public void twoVoyageEqualsOneWithId(){
        Voyage voyage = new Voyage("AH7788IO");
        voyage.setId(1);

        Voyage voyage1 = new Voyage("AH7788IO");

        Assert.assertEquals(voyage, voyage1);
    }

    @Test
    public void twoVoyagesNoEqualsOneWithBus(){
        Voyage voyage = new Voyage("AH7788IO");
        voyage.setBus(new Bus());

        Voyage voyage1 = new Voyage("AH7788IO");

        Assert.assertNotEquals(voyage, voyage1);
    }

    @Test
    public void twoVoyagesHashCode(){
        Voyage voyage = new Voyage("AH7788IO", new Bus());
        Voyage voyage1 = new Voyage("AH7788IO", new Bus());

        Assert.assertTrue(voyage.hashCode() == voyage1.hashCode());
    }

    @Test
    public void twoVoyagesHashCodeOneWithoutBus(){
        Voyage voyage = new Voyage("AH7788IO", new Bus("YRYYR", "Tom"));
        Voyage voyage1 = new Voyage("AH7788IO");

        Assert.assertTrue(voyage.hashCode() != voyage1.hashCode());
    }

    @Test
    public void emptyVoyageToString(){
        Voyage voyage = new Voyage();
        String expected = "Voyage{id=null, number='null', bus=null, tickets=null}";

        Assert.assertEquals(expected, voyage.toString());
    }

    @Test
    public void newVoyageWithConstructorWithoutBusToString(){
        Voyage voyage = new Voyage("AH7788IO");
        String expected = "Voyage{id=null, number='AH7788IO', bus=null, tickets=null}";

        Assert.assertEquals(expected, voyage.toString());
    }

    @Test
    public void newVoyageWithConstructorWithBusToString(){
        Voyage voyage = new Voyage("AH7788IO", new Bus());
        String expected = "Voyage{id=null, number='AH7788IO', bus=Bus{id=null, number='null', model='null', driver=null}, tickets=null}";

        Assert.assertEquals(expected, voyage.toString());
    }

    @Test
    public void newVoyageWithConstructorWithSetIdToString(){
        Voyage voyage = new Voyage("AH7788IO");
        voyage.setId(1);

        String expected = "Voyage{id=1, number='AH7788IO', bus=null, tickets=null}";

        Assert.assertEquals(expected, voyage.toString());
    }

    @Test
    public void newVoyageWithFullConstructor(){
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 11; i++){
            tickets.add(new Ticket(i, 2000, false));
        }

        Voyage voyage = new Voyage("AH7788IO", new Bus());
        voyage.setId(1);
        voyage.setTickets(tickets);

        Assert.assertEquals(10, voyage.getTickets().size());
    }

    @Test
    public void newVoyageWithFullConstructorPaidOneTicket(){
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 12; i++){
            tickets.add(new Ticket(i, 2000, false));
        }

        Voyage voyage = new Voyage("AH7788IO", new Bus());
        voyage.setId(1);
        voyage.setTickets(tickets);

        voyage.getTickets().iterator().next().setPaid(true);

        Assert.assertEquals(11, voyage.getTickets().size());
    }
}