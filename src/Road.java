/**
 * Road that connects two towns
 * @author Randall Kim
 */
public class Road implements Comparable<Road> {
    private String name;
    private Town source, destination;
    private int distance;

    /**
     * Constructs a road
     * @param source source town
     * @param destination destination town
     * @param distance distance or weight of the road
     * @param name name of road
     */
    public Road(Town source, Town destination, int distance, String name){
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    /**
     * Constructs a road with distance of 1
     * @param source source town
     * @param destination destination town
     * @param name name of road
     */
    public Road(Town source, Town destination, String name){
        this(source, destination, 1, name);
    }

    /**
     * Gets name of road
     * @return name of road
     */
    public String getName(){
        return name;
    }

    /**
     * Gets weight of road
     * @return weight of road
     */
    public int getWeight(){
        return distance;
    }

    /**
     * Gets source town
     * @return source town of the road
     */
    public Town getSource(){
        return source;
    }

    /**
     * Gets destination town
     * @return destination town of the road
     */
    public Town getDestination(){
        return destination;
    }

    /**
     * Determines if road contains a given town
     * @param town town to search road for
     * @return true if road connects to or from the town, false otherwise
     */
    public boolean contains(Town town){
        return town.equals(source) || town.equals(destination);
    }

    /**
     * String representation of road
     * @return name of road
     */
    @Override
    public String toString(){
        return name;
    }

    /**
     * Determine if road is equal
     * @param o object to compare
     * @return true if destination and source are equal
     */
    @Override
    public boolean equals(Object o){
        if(o == null || getClass() != o.getClass()){
            return false;
        }

        Road road = (Road) o;
        if(source == road.source || source == road.destination){
            if(destination == road.source || destination == road.destination){
                return true;
            }
        }

        return false;
    }

    /**
     * Compares two roads based on name
     * @param r road to compare to
     * @return comparison of names
     */
    @Override
    public int compareTo(Road r) {
        return name.compareTo(r.name);
    }
}
