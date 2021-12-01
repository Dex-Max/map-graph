import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;

/**
 * Manager class for graph
 * @author Randall Kim
 */
public class TownGraphManager implements  TownGraphManagerInterface{
    private Graph graph;

    /**
     * Default constructor
     */
    public TownGraphManager(){
        graph = new Graph();
    }

    /**
     * Adds a road
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @param weight weight of the road
     * @param roadName name of road
     * @return true if added successfully
     */
    @Override
    public boolean addRoad(String town1, String town2, int weight, String roadName) {
        graph.addEdge(new Town(town1), new Town(town2), weight, roadName);

        return true;
    }

    /**
     * Gets a road from the graph
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @return name of road
     */
    @Override
    public String getRoad(String town1, String town2) {
        return graph.getEdge(new Town(town1), new Town(town2)).toString();
    }

    /**
     * Adds a town to the graph
     * @param v the town's name  (lastname, firstname)
     * @return true if added successfully
     */
    @Override
    public boolean addTown(String v) {
        return graph.addVertex(new Town(v));
    }

    /**
     * Gets town from the graph
     * @param name the town's name
     * @return town that was found
     */
    @Override
    public Town getTown(String name) {
        return graph.getVertex(name);
    }

    /**
     * Determines if town exists in the graph
     * @param v the town's name
     * @return true if town is found
     */
    @Override
    public boolean containsTown(String v) {
        return graph.containsVertex(new Town(v));
    }

    /**
     * Determines if road exists in the graph
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @return true if the road is found
     */
    @Override
    public boolean containsRoadConnection(String town1, String town2) {
        return graph.containsEdge(new Town(town1), new Town(town2));
    }

    /**
     * Gets all roads in the graph
     * @return ArrayList of names of all roads
     */
    @Override
    public ArrayList<String> allRoads() {
        Set<Road> roads = graph.edgeSet();
        ArrayList<String> result = new ArrayList<>();

        for(Road road : roads){
            result.add(road.getName());
        }

        Collections.sort(result);

        return result;
    }

    /**
     * Deletes a road from the graph
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @param road name of road
     * @return true if road was deleted
     */
    @Override
    public boolean deleteRoadConnection(String town1, String town2, String road) {
        if(graph.removeEdge(new Town(town1), new Town(town2), 0, road) == null){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Deletes town from the graph
     * @param v name of town (lastname, firstname)
     * @return true if town was deleted
     */
    @Override
    public boolean deleteTown(String v) {
        return graph.removeVertex(new Town(v));
    }

    /**
     * Gets all towns from the graph
     * @return ArrayList of names of all towns
     */
    @Override
    public ArrayList<String> allTowns() {
        Set<Town> towns = graph.vertexSet();
        ArrayList<String> result = new ArrayList<>();

        for(Town town : towns){
            result.add(town.getName());
        }

        Collections.sort(result);

        return result;
    }

    /**
     * Gets a shortest path between two towns
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @return ArrayList of the path
     */
    @Override
    public ArrayList<String> getPath(String town1, String town2) {
        return graph.shortestPath(new Town(town1), new Town(town2));
    }

    /**
     * Populates the graph from a file
     * @param file file with text to populate graph
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void populateTownGraph(File file) throws FileNotFoundException, IOException {
        Scanner scanner = new Scanner(file);
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            Scanner reader = new Scanner(line);

            reader.useDelimiter(",|;");

            String roadName = reader.next();
            int roadWeight = Integer.parseInt(reader.next());
            String town1 = reader.next();
            String town2 = reader.next();

            if(!containsTown(town1)){
                addTown(town1);
            }

            if(!containsTown(town2)){
                addTown(town2);
            }

            addRoad(town1, town2, roadWeight, roadName);
        }
    }
}
