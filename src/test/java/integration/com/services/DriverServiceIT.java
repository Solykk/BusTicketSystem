package integration.com.services;

import com.entity.Driver;

import com.services.DriverService;

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

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@EntityScan(basePackages = "com.entity")
@EnableJpaRepositories("com.repositories")
@ComponentScan(basePackages = "com.services")
@Transactional
@TestPropertySource("classpath:application-test.properties")
public class DriverServiceIT {

    private DriverService driverService;

    @Test
    public void addDriver() {
        Assert.assertNotNull(driverService.addDriver(new Driver("YIIIPPO", "Poll", "Banana")));
    }

    @Test
    public void addDriverWithTheSameLicense() {
        try {
            driverService.addDriver(new Driver("YIIIPPO", "Poll", "Banana"));
            driverService.addDriver(new Driver("YIIIPPO", "Niki", "Lauda"));
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Driver with license YIIIPPO exist", e.getMessage());
        }
    }

    @Test
    public void addDriverNull() {
        Assert.assertEquals(new Driver(), driverService.addDriver(null));
    }

    @Test
    public void addDriveWithTheSameId() {

        Driver driver = new Driver("RRRERR", "Joi", "Jingle");
        Integer driverId = driverService.addDriver(driver).getId();

        try {
            Driver driver2 = new Driver("TOTOTO", "Joi", "Jingle");
            driver2.setId(driverId);
            driverService.addDriver(driver2);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Driver with id " + driverId + " exist", e.getMessage());
        }
    }

    @Test
    public void addDriveWithEmptyFields() {
        try {
            Driver driver = new Driver();
            driverService.addDriver(driver);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Driver fields can't be null", e.getMessage());
        }
    }

    @Test
    public void addDrivesAndFindAll() {
        Driver driver = new Driver("RRRERR", "Joi", "Jingle");
        Integer driverId = driverService.addDriver(driver).getId();

        Driver driver2 = new Driver("TOTOTO", "Inna", "Ser");
        Integer driverId2 = driverService.addDriver(driver2).getId();

        Driver driver3 = new Driver("MO8980", "Monty", "Dill");
        Integer driverId3 = driverService.addDriver(driver3).getId();

        List<Driver> drivers = driverService.findAllDrivers();

        Assert.assertEquals("[Driver{id=" + driverId + ", license='RRRERR', name='Joi', surname='Jingle'}, " +
                "Driver{id=" + driverId2 + ", license='TOTOTO', name='Inna', surname='Ser'}, " +
                "Driver{id=" + driverId3 + ", license='MO8980', name='Monty', surname='Dill'}]", drivers.toString());
    }

    @Test
    public void findAllDriversInEmptyTable() {
       List<Driver> drivers = driverService.findAllDrivers();

        Assert.assertEquals("[]", drivers.toString());
    }

    @Test
    public void findOneDriverNoEntity() {
        Assert.assertEquals(null, driverService.findOneDriver(1));
    }

    @Test
    public void findOneDriverWithEntity() {
        Driver driver = new Driver("HHd777", "Joi", "Tirb");
        Integer driverId = driverService.addDriver(driver).getId();
        Assert.assertEquals(driver, driverService.findOneDriver(driverId));
    }

    @Autowired
    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }
}