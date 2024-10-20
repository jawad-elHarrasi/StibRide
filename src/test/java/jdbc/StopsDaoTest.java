package jdbc;

import atl.model.dao.StopsDao;
import atl.model.dto.StopsDto;
import atl.model.exception.StibException;
import org.junit.*;
import java.io.File;
import java.util.List;

public class StopsDaoTest {
    private static StopsDao stopsDao;
    private static String dbUrl;

    @BeforeClass
    public static void setUpClass() throws Exception {
        // Load the database URL from the class loader resource
        File dbFile = new File(StopsDaoTest.class.getClassLoader().getResource("data/stibTest.db").getFile());
        dbUrl =  dbFile.getAbsolutePath();

        // Create an instance of StopsDao using the database URL
        stopsDao = new StopsDao(dbUrl);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        // If there were any resources to clean up, do it here
    }

    @Test
    public void testSelectValidId() throws StibException {
        // Test the retrieval of a valid record
        StopsDto result = stopsDao.select(8012); // Ensure that ID 1 is valid in your test DB
        Assert.assertNotNull("The result should not be null", result);
        Assert.assertEquals("The station name should match", "DE BROUCKERE", result.getNameStation());
    }

    @Test
    public void testSelectInvalidId() throws StibException {
        // Test the retrieval with an invalid ID
        StopsDto result = stopsDao.select(999); // Use an ID that does not exist
        Assert.assertNull("The result should be null for an invalid ID", result);
    }

    @Test
    public void testSelectAll() throws StibException {
        // Test the retrieval of all records
        List<StopsDto> results = stopsDao.selectAll();
        Assert.assertFalse("The list should not be empty", results.isEmpty());
        Assert.assertTrue("The list should contain elements", results.size() > 0);
    }

    @Test
    public void testSelectAllWithOutDouble() throws StibException {
        // Test the retrieval of all records without duplicates for the stations
        List<StopsDto> results = stopsDao.selectAllWithOutDouble();
        Assert.assertFalse("The list should not be empty", results.isEmpty());
        Assert.assertTrue("The list should contain elements", results.size() > 0);
    }
}