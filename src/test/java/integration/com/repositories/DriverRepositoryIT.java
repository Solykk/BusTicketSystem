package integration.com.repositories;

import com.entity.Driver;
import com.repositories.DriverRepository;

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

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@EntityScan(basePackages = "com.entity")
@EnableJpaRepositories("com.repositories")
@Transactional
@TestPropertySource("classpath:application-test.properties")
public class DriverRepositoryIT {

    private DriverRepository driverRepository;

    @Test
    public void addDriver(){
        Driver driver = new Driver("OKD1234567", "Harry", "Potter");
        Driver dbDriver = driverRepository.save(driver);
        Assert.assertEquals(driver, dbDriver);
    }

    @Test
    public void createDriveReturnId(){
        Driver driver = new Driver("OHHdk", "Hop", "Rock");
        driver = driverRepository.save(driver);

        Assert.assertNotNull(driver.getId());
    }

    @Test
    public void driverAddNextDriver(){
        Driver driver = new Driver("OHHdk", "Hop", "Rock");
        driver = driverRepository.save(driver);

        Driver driver1 = new Driver("YYUSF", "Por", "Sop");
        driver1 = driverRepository.save(driver1);

        Integer result = driver1.getId();

        Assert.assertEquals(driver.getId(), --result);
    }

    @Test
    public void addDriveWithTheSameLicense(){
        Driver driver = new Driver("RRRERR", "Joi", "Jingle");
        driver = driverRepository.save(driver);

        Driver driver2 = new Driver("RRRERR", "Joi", "Jingle");
        driver2 = driverRepository.save(driver2);

        Assert.assertEquals(driver, driver2);

        Assert.assertNotSame(driver.getId(), driver2.getId());
    }

    @Test
    public void addDriveWithTheSameId(){
        Driver driver = new Driver("RRRERR", "Joi", "Jingle");
        driver = driverRepository.save(driver);

        Driver driver2 = new Driver("TOTOTO", "Joi", "Jingle");
        driver2.setId(driver.getId());
        driverRepository.save(driver2);

        Assert.assertEquals(driverRepository.findOne(driver.getId()), driver2);
    }

    @Test
    public void addDriveAndFindOneByLicense(){
        Driver driver = new Driver("RRRERR", "Joi", "Jingle");
        driverRepository.save(driver);

        Assert.assertEquals(driverRepository.findOneByLicense("RRRERR"), driver);
    }

    public DriverRepository getDriverRepository() {
        return driverRepository;
    }

    @Autowired
    public void setDriverRepository(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }
}