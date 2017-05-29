package com.entity;

import org.junit.Assert;
import org.junit.Test;

public class BusTest {

    @Test
    public void newBus(){
        Bus bus = new Bus();

        Assert.assertEquals(bus.getId(), null);
        Assert.assertEquals(bus.getNumber(), null);
        Assert.assertEquals(bus.getModel(), null);
        Assert.assertEquals(bus.getDriver(), null);
    }

    @Test
    public void newBusWithConstructor(){
        Bus bus = new Bus("FF7777JJ", "RebBull", new Driver());

        Assert.assertEquals(bus.getId(), null);
        Assert.assertEquals(bus.getNumber(), "FF7777JJ");
        Assert.assertEquals(bus.getModel(), "RebBull");
        Assert.assertEquals(bus.getDriver(), new Driver());
    }

    @Test
    public void newBusWithConstructorWithoutDriver(){
        Bus bus = new Bus("FF7777JJ", "RebBull");

        Assert.assertEquals(bus.getId(), null);
        Assert.assertEquals(bus.getNumber(), "FF7777JJ");
        Assert.assertEquals(bus.getModel(), "RebBull");
        Assert.assertEquals(bus.getDriver(), null);
    }

    @Test
    public void newBusWithConstructorWithSetId(){
        Bus bus = new Bus("AH7788IO", "Ferrari");

        bus.setId(1);

        Assert.assertEquals(bus.getId(), new Integer(1));
        Assert.assertEquals(bus.getNumber(), "AH7788IO");
        Assert.assertEquals(bus.getModel(), "Ferrari");
        Assert.assertEquals(bus.getDriver(), null);
    }

    @Test
    public void newBusWithConstructorWithSetIdWithSetNumber(){
        Bus bus = new Bus("AH7788IO", "Ferrari");

        bus.setId(1);
        bus.setNumber("AA5554JJ");

        Assert.assertEquals(bus.getId(), new Integer(1));
        Assert.assertEquals(bus.getNumber(), "AA5554JJ");
        Assert.assertEquals(bus.getModel(), "Ferrari");
        Assert.assertEquals(bus.getDriver(), null);
    }

    @Test
    public void newBusWithConstructorSetDriver(){
        Bus bus = new Bus("AH7788IO", "Ferrari");

        bus.setId(1);
        bus.setDriver(new Driver());
        Assert.assertEquals(bus.getId(), new Integer(1));
        Assert.assertEquals(bus.getNumber(), "AH7788IO");
        Assert.assertEquals(bus.getModel(), "Ferrari");
        Assert.assertEquals(bus.getDriver(), new Driver());
    }

    @Test
    public void twoBusesEquals(){
        Bus bus = new Bus("AH7788IO", "Ferrari");
        Bus bus2 = new Bus("AH7788IO", "Ferrari");

        Assert.assertEquals(bus, bus2);
    }

    @Test
    public void twoBusesEqualsOneWithId(){
        Bus bus = new Bus("AH7788IO", "Ferrari");
        bus.setId(1);

        Bus bus1 = new Bus("AH7788IO", "Ferrari");

        Assert.assertEquals(bus, bus1);
    }

    @Test
    public void twoBusesNoEqualsOneWithDriver(){
        Bus bus = new Bus("AH7788IO", "Ferrari");
        bus.setDriver(new Driver());

        Bus bus1 = new Bus("AH7788IO", "Ferrari");

        Assert.assertNotEquals(bus, bus1);
    }

    @Test
    public void twoBusesHashCode(){
        Bus bus = new Bus("AH7788IO", "Ferrari", new Driver());
        Bus bus1 = new Bus("AH7788IO", "Ferrari", new Driver());

        Assert.assertTrue(bus.hashCode() == bus1.hashCode());
    }

    @Test
    public void twoBusesHashCodeOneWithoutDriver(){
        Bus bus = new Bus("AH7788IO", "Ferrari", new Driver("YRYYR", "Tom", "Por"));
        Bus bus1 = new Bus("AH7788IO", "Ferrari");

        Assert.assertTrue(bus.hashCode() != bus1.hashCode());
    }

    @Test
    public void emptyBusToString(){
        Bus bus = new Bus();
        String expected = "Bus{id=null, number='null', model='null', driver=null}";

        Assert.assertEquals(expected, bus.toString());
    }

    @Test
    public void newBusWithConstructorWithoutDriverToString(){
        Bus bus = new Bus("AH7788IO", "Ferrari");
        String expected = "Bus{id=null, number='AH7788IO', model='Ferrari', driver=null}";

        Assert.assertEquals(expected, bus.toString());
    }

    @Test
    public void newBusWithConstructorWithDriverToString(){
        Bus bus = new Bus("AH7788IO", "Ferrari", new Driver());
        String expected = "Bus{id=null, number='AH7788IO', model='Ferrari', driver=Driver{id=null, license='null', name='null', surname='null'}}";

        Assert.assertEquals(expected, bus.toString());
    }

    @Test
    public void newDriverWithConstructorWithSetIdToString(){
        Bus bus = new Bus("AH7788IO", "Ferrari");
        bus.setId(1);

        String expected = "Bus{id=1, number='AH7788IO', model='Ferrari', driver=null}";

        Assert.assertEquals(expected, bus.toString());
    }
}