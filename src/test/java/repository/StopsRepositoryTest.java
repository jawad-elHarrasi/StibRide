package repository;

import atl.model.dao.StopsDao;
import atl.model.dao.StopsRepository;
import atl.model.dto.StopsDto;
import atl.model.exception.StibException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for StopsRepository utilizing Mockito for interaction testing.
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class StopsRepositoryTest {

    @Mock
    private StopsDao stopsDao;

    private StopsRepository stopsRepository;

    @BeforeEach
    void init() throws StibException {
        System.out.println("==== BEFORE EACH =====");
        // Initialize the StopsRepository with the mocked StopsDao
        stopsRepository = new StopsRepository(stopsDao);

        // Setting up lenient mock behavior to prevent unnecessary stubbing exceptions
        Mockito.lenient().when(stopsDao.selectAll()).thenReturn(Arrays.asList(
                new StopsDto(1, 101, 1, "Station 1"),
                new StopsDto(2, 102, 2, "Station 2")
        ));
        Mockito.lenient().when(stopsDao.select(1)).thenReturn(new StopsDto(1, 101, 1, "Station 1"));
        Mockito.lenient().when(stopsDao.select(2)).thenReturn(null);  // simulate "not found" scenario
        Mockito.lenient().when(stopsDao.select(3)).thenThrow(new StibException("Database error"));
    }

    @Test
    public void testSelectAll() throws StibException {
        System.out.println("testSelectAll");
        // Act
        List<StopsDto> result = stopsRepository.selectAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(stopsDao, times(1)).selectAll();
    }

    @Test
    public void testSelect() throws StibException {
        System.out.println("testSelect");
        // Act
        StopsDto result = stopsRepository.select(1);

        // Assert
        assertNotNull(result);
        assertEquals("Station 1", result.getNameStation());
        verify(stopsDao, times(1)).select(1);
    }

    @Test
    public void testSelectNotFound() throws StibException {
        System.out.println("testSelectNotFound");
        // Assert
        assertNull(stopsRepository.select(2));
        verify(stopsDao, times(1)).select(2);
    }

    @Test
    public void testSelectThrowsException() throws StibException {
        System.out.println("testSelectThrowsException");
        // Assert
        assertThrows(StibException.class, () -> {
            // Act
            stopsRepository.select(null);
        });
        verify(stopsDao, times(0)).select(3);
    }
}
