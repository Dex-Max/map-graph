import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class Road_STUDENT_Test {
    Town town1;
    Town town2;
    Road road;

    @Before
    public void setUp(){
        town1 = new Town("Town1");
        town2 = new Town("Town2");
        road = new Road(town1, town2, "Test Road");
    }

    @After
    public void tearDown(){
        town1 = null;
        town2 = null;
        road = null;
    }

    @Test
    public void testGetName(){
        assertEquals("Test Road", road.getName());
    }

    @Test
    public void testGetWeight(){
        assertEquals(1, road.getWeight());
    }

    @Test
    public void testGetSource(){
        assertEquals(town1, road.getSource());
    }

    @Test
    public void testGetDestination(){
        assertEquals(town2, road.getDestination());
    }

    @Test
    public void testContains(){
        assertTrue(road.contains(town1) && road.contains(town2));
    }

    @Test
    public void testToString(){
        assertEquals(road.getName(), road.toString());
    }

    @Test
    public void testEquals(){
        assertTrue(road.equals(new Road(town1, town2, "Road 2")));
    }

    @Test
    public void testCompareTo(){
        Road road2 = new Road(town1, town2, "Road 2");
        assertTrue(road.compareTo(road2) == road.getName().compareTo(road2.getName()));
    }
}
