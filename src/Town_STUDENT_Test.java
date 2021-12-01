import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class Town_STUDENT_Test {
    private Town town1;
    private Town town2;

    @Before
    public void setUp(){
        town1 = new Town("Town1");
        town2 = new Town("Town2");
    }

    @After
    public void tearDown(){
        town1 = null;
        town2 = null;
    }

    @Test
    public void testGetName(){
        assertEquals("Town1", town1.getName());
        assertEquals( "Town2", town2.getName());
    }

    @Test
    public void testGetRoads(){
        assertTrue(town1.getRoads().isEmpty());
    }

    @Test
    public void testToString(){
        assertEquals("Town1", town1.toString());
        assertEquals("Town2", town2.toString());
    }

    @Test
    public void testEquals(){
        assertTrue(town1.equals(new Town("Town1")));
        assertTrue(town2.equals(new Town("Town2")));
    }

    @Test
    public void testCompareTo(){
        assertTrue(town1.compareTo(town2) == town1.getName().compareTo(town2.getName()));
    }
}
