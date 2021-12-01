import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.ArrayList;

public class TownGraphManager_STUDENT_Test {
    TownGraphManager graphManager;

    @Before
    public void setUp(){
        graphManager = new TownGraphManager();

        graphManager.addTown("Town1");
        graphManager.addTown("Town2");
        graphManager.addTown("Town3");
        graphManager.addTown("Town4");

        graphManager.addRoad("Town1", "Town2", 1, "Road1");
        graphManager.addRoad("Town2", "Town4", 1, "Road2");
        graphManager.addRoad("Town1", "Town3", 1, "Road3");
        graphManager.addRoad("Town3", "Town4", 1, "Road4");
    }

    @After
    public void tearDown(){
        graphManager = null;
    }

    @Test
    public void testAddRoad(){
        graphManager.addRoad("Town1", "Town4", 1, "Test Road");

        assertFalse(graphManager.getRoad("Town1", "Town4") == null);
    }

    @Test
    public void testGetRoad(){
        assertFalse(graphManager.getRoad("Town1", "Town2") == null);
    }

    @Test
    public void testAddTown(){
        graphManager.addTown("Test Town");

        assertFalse(graphManager.getTown("Test Town") == null);
    }

    @Test
    public void testGetTown(){
        assertFalse(graphManager.getTown("Town1") == null);
        assertTrue(graphManager.getTown("Random Town") == null);
    }

    @Test
    public void testContainsTown(){
        assertTrue(graphManager.containsTown("Town1"));
        assertFalse(graphManager.containsTown("Random Town"));
    }

    @Test
    public void testContainsRoadConnection(){
        assertTrue(graphManager.containsRoadConnection("Town1", "Town2"));
        assertFalse(graphManager.containsRoadConnection("Town1", "Town4"));
    }

    @Test
    public void testAllRoads(){
        ArrayList<String> allRoads = graphManager.allRoads();

        assertTrue(allRoads.contains("Road1"));
        assertFalse(allRoads.contains("Random Road"));
    }

    @Test
    public void testDeleteRoadConnection(){
        assertTrue(graphManager.containsRoadConnection("Town1", "Town2"));
        graphManager.deleteRoadConnection("Town1", "Town2", "Road1");
        assertFalse(graphManager.containsRoadConnection("Town1", "Town2"));
    }

    @Test
    public void testDeleteTown(){
        assertTrue(graphManager.containsTown("Town1"));
        graphManager.deleteTown("Town1");
        assertFalse(graphManager.containsTown("Town1"));
    }

    @Test
    public void testAllTowns(){
        ArrayList<String> allTowns = graphManager.allTowns();

        assertTrue(allTowns.contains("Town1"));
        assertFalse(allTowns.contains("Random Town"));
    }

    @Test
    public void testGetPath(){
        ArrayList<String> path = graphManager.getPath("Town1", "Town4");

        assertEquals("Town1 via Road3 to Town3 1 mi", path.get(0));
        assertEquals("Town3 via Road4 to Town4 1 mi", path.get(1));
    }
}
