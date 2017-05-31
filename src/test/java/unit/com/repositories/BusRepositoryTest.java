package unit.com.repositories;

import com.entity.Bus;

import com.repositories.BusRepository;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BusRepositoryTest {

    private BusRepository busRepository = mock(BusRepository.class);

    @Test
    public void addBus() {
        //Given
        Bus dbBus = new Bus("AA1234AA", "Porsche");
        dbBus.setId(1);

        //When
        when(busRepository.save(new Bus("AA1234AA", "Porsche"))).thenReturn(dbBus);

        //Then
        Assert.assertEquals(
                busRepository.save(new Bus("AA1234AA", "Porsche")).getId(),
                new Integer(1));

    }
}