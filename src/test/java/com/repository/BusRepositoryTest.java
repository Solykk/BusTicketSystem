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

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@EntityScan(basePackages = "com.entity")
@EnableJpaRepositories("com.repository")
@Transactional
@TestPropertySource("classpath:application-test.properties")
public class BusRepositoryTest {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Test
    public void addBus(){
        Bus bus = new Bus("AA1234AA", "Porsche");
        Bus dbBus = busRepository.save(bus);
        Assert.assertEquals(bus, dbBus);
    }

    @Test
    public void updateDriverOnBus(){

        Bus bus = new Bus();
        bus.setNumber("Try");
        bus.setModel("Catch");

        busRepository.save(bus);

        Bus result = busRepository.findOneByNumber("Try");

        Assert.assertNull(result.getDriver());

        Driver driver = new Driver("OHHdk", "Hop", "Rock");
        driver = driverRepository.save(driver);

        result.setDriver(driver);

        Bus secondResult = busRepository.save(result);

        Assert.assertEquals(driver, secondResult.getDriver());
    }

    @Test
    public void addDriverAddBusesDeleteDriver(){
        Driver driver = new Driver("OKD1234567", "Harry", "Potter");
        Driver driver1 = new Driver("IUD9987989", "Tom", "King");

        Driver dbDriver = driverRepository.save(driver);
        driverRepository.save(driver1);

        Bus bus = new Bus();
        bus.setNumber("AA9999BB");
        bus.setModel("Ferrari");

        Bus bus1 = new Bus();
        bus1.setNumber("KK7777OO");
        bus1.setModel("Lada");

        Bus dbBus = busRepository.save(bus);
        Bus dbBus1 = busRepository.save(bus1);

        dbBus.setDriver(driver);
        dbBus1.setDriver(driver1);

        dbBus = busRepository.save(dbBus);
        busRepository.save(dbBus1);

        dbBus.setDriver(null);

        dbBus = busRepository.save(dbBus);

        driverRepository.delete(dbDriver.getId());

        System.out.println(bus.toString());

        Assert.assertEquals(dbBus.getDriver(), null);
    }

    @Test
    public void createBusReturnId(){
        Bus bus = new Bus("AA1234AA", "Porsche");
        Bus dbBus = busRepository.save(bus);

        Assert.assertNotNull(dbBus.getId());
    }

    @Test
    public void busAddNextBus(){
        Bus bus = new Bus("AA1234AA", "RedBull");
        bus = busRepository.save(bus);

        Bus bus1 = new Bus("BB1555CA", "Tor");
        bus1 = busRepository.save(bus1);

        Integer result = bus1.getId();

        Assert.assertEquals(bus.getId(), --result);
    }

    @Test
    public void addBusWithTheSameNumber(){
        Bus bus = new Bus("AA1234AA", "RedBull");
        bus = busRepository.save(bus);

        Bus bus1 = new Bus("AA1234AA", "RedBull");
        bus1 = busRepository.save(bus1);

        Assert.assertEquals(bus, bus1);

        Assert.assertNotSame(bus.getId(), bus1.getId());
    }

    @Test
    public void addBusWithTheSameId(){
        Bus bus = new Bus("AA1234AA", "RedBull");
        bus = busRepository.save(bus);

        Bus bus1 = new Bus("CC4343TT", "Tot");
        bus1.setId(bus.getId());
        busRepository.save(bus1);

        Assert.assertEquals(busRepository.findOne(bus.getId()), bus1);
    }

    @Test
    public void addBusAndFindOneByNumber(){
        Bus bus = new Bus("AA1234AA", "RedBull");
        busRepository.save(bus);

        Assert.assertEquals(busRepository.findOneByNumber("AA1234AA"), bus);
    }

    @Test
    public void addBusAddDriverAndChangeDriver(){
        Bus bus = new Bus("AA1234AA", "RedBull", new Driver("TTE93993", "Rock", "Roll"));
        busRepository.save(bus);

        Bus dbBus = busRepository.findOneByNumber("AA1234AA");

        Assert.assertEquals(dbBus, bus);

        Driver driver =  new Driver("BBVSV009", "Donald", "Duck");
        dbBus.setDriver(driver);
        busRepository.save(dbBus);

        Assert.assertEquals(dbBus.getDriver(), driver);

        ArrayList<Driver> drivers = (ArrayList<Driver>)driverRepository.findAll();

        Assert.assertTrue(drivers.size() == 2);
    }
}