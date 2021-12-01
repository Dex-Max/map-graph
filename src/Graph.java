import java.util.*;

/**
 * Graph that can hold Towns and Roads
 * Uses a map to hold list of adjacent roads of each town
 * @author Randall Kim
 */
public class Graph implements GraphInterface<Town, Road>{
    Map<Town, List<Road>> adjacents;
    Map<Town, Integer> shortestDistances;
    Map<Town, Town> backpointers;

    /**
     * Default Constructor
     */
    public Graph(){
        adjacents = new HashMap<>();
    }

    /**
     * Finds an edge in the graph given source and destination
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     *
     * @return edge with matching source and destination, or null if not found
     */
    @Override
    public Road getEdge(Town sourceVertex, Town destinationVertex) {
        if(sourceVertex == null || destinationVertex == null){
            return null;
        }

        List<Road> adjacentRoads = adjacents.get(sourceVertex);

        for(Road road : adjacentRoads){
            if(road.contains(destinationVertex)){
                return road;
            }
        }

        return null;
    }

    /**
     * Adds a road to the graph
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     * @param weight weight of the edge
     * @param description description for edge
     *
     * @return Road that was added
     */
    @Override
    public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
        if(sourceVertex == null || destinationVertex == null){
            throw new NullPointerException("Source and destination cannot be null");
        }
        if(!adjacents.containsKey(sourceVertex) || !adjacents.containsKey(destinationVertex)){
            throw new IllegalArgumentException("Source and destination must already be in the graph");
        }

        Road newRoad = new Road(sourceVertex, destinationVertex, weight, description);

        sourceVertex.getRoads().add(newRoad);
        destinationVertex.getRoads().add(newRoad);

        adjacents.get(sourceVertex).add(newRoad);
        adjacents.get(destinationVertex).add(newRoad);

        return newRoad;
    }

    /**
     * Finds the vertex given name
     * @param townName name to search
     * @return Town that was found
     */
    public Town getVertex(String townName){
        Set<Town> towns = adjacents.keySet();

        for(Town town : towns){
            if(town.getName().equals(townName)){
                return town;
            }
        }

        return null;
    }

    /**
     * Adds a town to the graph
     * @param town town to add
     * @return true if town was added successfully, false if the town already exists
     */
    @Override
    public boolean addVertex(Town town) {
        if(town == null){
            throw new NullPointerException();
        }

        if(adjacents.containsKey(town)){
            return false;
        } else {
            adjacents.put(town, new ArrayList<>());
            return true;
        }
    }

    /**
     * Determines if a given edge is in the graph
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     *
     * @return true if edge is found, false otherwise
     */
    @Override
    public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
        if(adjacents.containsKey(sourceVertex)){
            List<Road> adjacentRoads = adjacents.get(sourceVertex);

            for(Road road : adjacentRoads){
                if(road.contains(destinationVertex)){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Determines if a vertex is in the graph
     * @param town town to search for
     * @return true if vertex is found, false otherwise
     */
    @Override
    public boolean containsVertex(Town town) {
        if(adjacents.containsKey(town)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves a set of all edges in the graph
     * @return a set of all edges in the graph
     */
    @Override
    public Set<Road> edgeSet() {
        Set<Town> keys = adjacents.keySet();
        Set<Road> result = new HashSet<>();

        for(Town town : keys){
            for(Road road : adjacents.get(town)){
                if(!result.contains(road)){
                    result.add(road);
                }
            }
        }

        return result;
    }

    /**
     * Finds all of the roads adjacent to a given vertex
     * @param vertex the vertex for which a set of touching edges is to be
     * returned.
     *
     * @return set of all roads adjacent to the vertex
     */
    @Override
    public Set<Road> edgesOf(Town vertex) {
        if(vertex == null){
            throw new NullPointerException();
        }

        if(!adjacents.containsKey(vertex)){
            throw new IllegalArgumentException();
        }

        return new HashSet<>(adjacents.get(vertex));
    }

    /**
     * Removes an edge from the graph
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     * @param weight weight of the edge
     * @param name name of the edge
     * @return the road that was removed
     */
    @Override
    public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String name) {
        List<Road> adjacentRoads = adjacents.get(sourceVertex);

        for(Road road : adjacentRoads){
            if(road.contains(destinationVertex) && road.getName() == name){
                adjacentRoads.remove(road);
                adjacents.get(destinationVertex).remove(sourceVertex);

                sourceVertex.getRoads().remove(destinationVertex);
                destinationVertex.getRoads().remove(sourceVertex);

                return road;
            }
        }

        return null;
    }

    /**
     * Removes a vertex from the graph
     * @param town town to be removed
     * @return true if it was removed, false otherwise
     */
    @Override
    public boolean removeVertex(Town town) {
        if(adjacents.containsKey(town)){
            for(Road road : adjacents.get(town)){
                adjacents.get(road.getDestination()).remove(town);
            }

            adjacents.remove(town);
            return true;
        }

        return false;
    }

    /**
     * Finds all vertices
     * @return set of all vertices
     */
    @Override
    public Set<Town> vertexSet() {
        return adjacents.keySet();
    }

    /**
     * Finds the shortest path between two vertexes
     * @param sourceVertex starting vertex
     * @param destinationVertex ending vertex
     * @return an ArrayList containing the shortest path
     */
    @Override
    public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
        dijkstraShortestPath(sourceVertex);
        ArrayList<String> result = new ArrayList<>();

        if(shortestDistances.get(destinationVertex) == Integer.MAX_VALUE){
            return result;
        }

        Town current = destinationVertex;

        while(!current.equals(sourceVertex)){
            Town previous = backpointers.get(current);
            Road connectingRoad = getEdge(current, previous);
            result.add(0, previous.getName() + " via " + connectingRoad.getName() + " to " + current.getName() + " " + connectingRoad.getWeight() + " mi");
            current = backpointers.get(current);
        }

        return result;
    }

    /**
     * Finds the shortest distance to all other vertices in the graph
     * @param sourceVertex the vertex to find shortest path from
     */
    @Override
    public void dijkstraShortestPath(Town sourceVertex) {
        shortestDistances = new HashMap<>();
        backpointers = new HashMap<>();
        Map<Town, Boolean> included = new HashMap<>();

        for(Town town : adjacents.keySet()){
            shortestDistances.put(town, Integer.MAX_VALUE);
            backpointers.put(town, null);
            included.put(town, false);
        }

        shortestDistances.replace(sourceVertex, 0);

        for(Town town1 : adjacents.keySet()){
            Town minimumDistanceTown = getMinimumDistance(shortestDistances, included);

            if(minimumDistanceTown == null){
                continue;
            }

            included.replace(minimumDistanceTown, true);

            for(Town town : adjacents.keySet()){
                int distanceToNew = Integer.MAX_VALUE;

                for(Road road : adjacents.get(minimumDistanceTown)){
                    if(road.contains(town)){
                        distanceToNew = road.getWeight();
                        break;
                    }
                }

                if(included.get(town) == false && shortestDistances.get(minimumDistanceTown) + distanceToNew < shortestDistances.get(town) && distanceToNew != Integer.MAX_VALUE){
                    shortestDistances.replace(town, distanceToNew + shortestDistances.get(minimumDistanceTown));
                    backpointers.replace(town, minimumDistanceTown);
                }
            }
        }
    }

    private Town getMinimumDistance(Map<Town, Integer> distances, Map<Town, Boolean> included){
        int minDist = Integer.MAX_VALUE;
        Town minTown = null;

        for(Town town : adjacents.keySet()){
            if(included.get(town) == false && distances.get(town) < minDist){
                minDist = distances.get(town);
                minTown = town;
            }
        }

        return minTown;
    }
}
