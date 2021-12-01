import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Graph_STUDENT_Test {
    Graph graph;
    Town[] towns;

    @Before
    public void setUp(){
        //Creates a graph with 4 corners with roads connecting the edges but not diagonals
        towns = new Town[4];

        for(int i = 0; i < 4; i++){
            towns[i] = new Town("Town" + (i + 1));
        }

        graph = new Graph();

        for(Town town : towns){
            graph.addVertex(town);
        }

        graph.addEdge(towns[0], towns[1], 1, "Road1");
        graph.addEdge(towns[1], towns[3], 1, "Road2");
        graph.addEdge(towns[0], towns[2], 1, "Road3");
        graph.addEdge(towns[2], towns[3], 1, "Road4");
    }

    @After
    public void tearDown(){
        graph = null;
    }

    @Test
    public void testGetEdge(){
        try {
            assertEquals(new Road(towns[0], towns[1], 1, "Test Road"), graph.getEdge(towns[0], towns[1]));
        } catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void testAddEdge(){
        graph.addEdge(towns[0], towns[3], 1, "TestEdge");

        assertFalse(graph.getEdge(towns[0], towns[3]) == null);
    }

    @Test
    public void testGetVertex(){
        for(Town town : towns){
            assertEquals(town, graph.getVertex(town.getName()));
        }
    }

    @Test
    public void testAddVertex(){
        try {
            Town testTown = new Town("Test Town");
            graph.addVertex(testTown);

            assertEquals(testTown, graph.getVertex(testTown.getName()));
        } catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void testContainsEdge(){
        assertTrue(graph.containsEdge(towns[0], towns[1]));
        assertFalse(graph.containsEdge(towns[1], towns[2]));
    }

    @Test
    public void testContainsVertex(){
        Town testTown = new Town("Test Town");

        assertTrue(graph.containsVertex(towns[0]));
        assertFalse(graph.containsVertex(testTown));
    }

    @Test
    public void testEdgeSet(){
        Set<Road> roadSet = graph.edgeSet();

        assertTrue(roadSet.contains(graph.getEdge(towns[0], towns[1])));
        assertFalse(roadSet.contains(new Road(towns[0], towns[3], 2, "Test Road")));
    }

    @Test
    public void testEdgesOf(){
        try {
            Set<Road> adjacentRoads = graph.edgesOf(towns[0]);

            for(Road road : adjacentRoads){
                assertTrue(road.contains(towns[0]));
            }
        } catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void testRemoveEdge(){
        graph.removeEdge(towns[0], towns[1], 1, "Road1");
        assertFalse(graph.containsEdge(towns[0], towns[1]));
    }

    @Test
    public void testRemoveVertex(){
        graph.removeVertex(towns[0]);

        assertFalse(graph.containsVertex(towns[0]));
    }

    @Test
    public void testVertexSet(){
        Set<Town> townSet = graph.vertexSet();

        for(Town town : towns){
            assertTrue(townSet.contains(town));
        }
    }

    @Test
    public void testShortestPath(){
        ArrayList<String> path = graph.shortestPath(towns[0], towns[3]);

        assertEquals(towns[0].getName() + " via Road3 to " + towns[2].getName() + " 1 mi", path.get(0));
        assertEquals(towns[2].getName() + " via Road4 to " + towns[3].getName() + " 1 mi", path.get(1));
    }

    @Test
    public void testDijkstraShortestPath(){
        graph.dijkstraShortestPath(towns[0]);
        Map<Town, Integer> shortestDistances = graph.shortestDistances;

        assertTrue(shortestDistances.get(towns[0]) == 0);
        assertTrue(shortestDistances.get(towns[1]) == 1);
        assertTrue(shortestDistances.get(towns[2]) == 1);
        assertTrue(shortestDistances.get(towns[3]) == 2);
    }
}
