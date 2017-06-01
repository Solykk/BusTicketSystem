package unit.com.services;

import com.entity.Driver;
import com.repositories.*;

import com.services.RequestValidator;
import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RequestValidatorTest {

    @Mock
    private DriverRepository driverRepository = mock(DriverRepository.class);

    @Mock
    private BusRepository busRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private VoyageRepository voyageRepository;

    @InjectMocks
    private RequestValidator requestValidator = mock(RequestValidator.class);

    @Test
    public void driverRequestValidatorDiverNull(){
        //Given
        doThrow(new IllegalArgumentException("Driver can't be null"))
                .when(requestValidator)
                .driverRequestValidator(null);

        //When
        try {
            requestValidator.driverRequestValidator(null);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Driver can't be null", e.getMessage());
        }
    }

    @Test
    public void driverRequestValidatorDriverFieldNull(){
        //Given
        doThrow(new IllegalArgumentException("Driver fields can't be null"))
                .when(requestValidator)
                .driverRequestValidator(new Driver());

        //When
        try {
            requestValidator.driverRequestValidator(new Driver());

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Driver fields can't be null", e.getMessage());
        }
    }

    @Test
    public void driverRequestValidatorExistDriver(){
        //Given
        Driver driver = new Driver("AKSE", "Niki", "Lauda");
        driver.setId(1);

        when(driverRepository.exists(1)).thenReturn(true);

        doCallRealMethod().when(requestValidator).driverRequestValidator(driver);

        //When
        try {
            requestValidator.driverRequestValidator(driver);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Driver with id " + driver.getId() + " exist", e.getMessage());
        }
    }
}