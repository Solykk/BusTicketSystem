package com.entity;

import org.junit.Assert;
import org.junit.Test;

public class DriverTest {

    @Test
    public void newDriver(){
        Driver driver = new Driver();

        Assert.assertEquals(driver.getId(), null);
        Assert.assertEquals(driver.getLicense(), null);
        Assert.assertEquals(driver.getName(), null);
        Assert.assertEquals(driver.getSurname(), null);
    }

    @Test
    public void newDriverWithConstructor(){
        Driver driver = new Driver("UYT6575", "Bob", "Nio");

        Assert.assertEquals(driver.getId(), null);
        Assert.assertEquals(driver.getLicense(), "UYT6575");
        Assert.assertEquals(driver.getName(), "Bob");
        Assert.assertEquals(driver.getSurname(), "Nio");
    }

    @Test
    public void newDriverWithConstructorWithSetId(){
        Driver driver = new Driver("UYT6575", "Bob", "Nio");

        driver.setId(1);

        Assert.assertEquals(driver.getId(), new Integer(1));
        Assert.assertEquals(driver.getLicense(), "UYT6575");
        Assert.assertEquals(driver.getName(), "Bob");
        Assert.assertEquals(driver.getSurname(), "Nio");
    }

    @Test
    public void newDriverWithConstructorWithSetIdWithSetLicense(){
        Driver driver = new Driver("UYT6575", "Bob", "Nio");

        driver.setId(1);
        driver.setLicense("RRTD898");

        Assert.assertEquals(driver.getId(), new Integer(1));
        Assert.assertEquals(driver.getLicense(), "RRTD898");
        Assert.assertEquals(driver.getName(), "Bob");
        Assert.assertEquals(driver.getSurname(), "Nio");
    }

    @Test
    public void newDriverWithConstructorSetAll(){
        Driver driver = new Driver();

        driver.setId(1);
        driver.setLicense("RRTD898");
        driver.setName("Bob");
        driver.setSurname("Wop");

        Assert.assertEquals(driver.getId(), new Integer(1));
        Assert.assertEquals(driver.getLicense(), "RRTD898");
        Assert.assertEquals(driver.getName(), "Bob");
        Assert.assertEquals(driver.getSurname(), "Wop");
    }

    @Test
    public void twoDriversEquals(){
        Driver driver = new Driver("UYT6575", "Bob", "Nio");
        Driver driver1 = new Driver("UYT6575", "Bob", "Nio");

        Assert.assertEquals(driver, driver1);
    }

    @Test
    public void twoDriversEqualsOneWithId(){
        Driver driver = new Driver("UYT6575", "Bob", "Nio");
        driver.setId(1);

        Driver driver1 = new Driver("UYT6575", "Bob", "Nio");

        Assert.assertEquals(driver, driver1);
    }

    @Test
    public void twoDriversHashCode(){
        Driver driver = new Driver("UYT6575", "Bob", "Nio");
        Driver driver1 = new Driver("UYT6575", "Bob", "Nio");

        Assert.assertTrue(driver.hashCode() == driver1.hashCode());
    }

    @Test
    public void emptyDriverToString(){
        Driver driver = new Driver();
        String expected = "Driver{id=null, license='null', name='null', surname='null'}";

        Assert.assertEquals(expected, driver.toString());
    }

    @Test
    public void newDriverWithConstructorToString(){
        Driver driver = new Driver("UYT6575", "Bob", "Nio");
        String expected = "Driver{id=null, license='UYT6575', name='Bob', surname='Nio'}";

        Assert.assertEquals(expected, driver.toString());
    }

    @Test
    public void newDriverWithConstructorWithSetIdToString(){
        Driver driver = new Driver("UYT6575", "Bob", "Nio");
        driver.setId(1);

        String expected = "Driver{id=1, license='UYT6575', name='Bob', surname='Nio'}";

        Assert.assertEquals(expected, driver.toString());
    }
}