import java.util.ArrayList;
import java.util.List;

/**
 * Town that represents a vertex of a graph
 * @author Randall Kim
 */
public class Town implements Comparable<Town>{
    private String name;
    private List<Road> roads;

    /**
     * Constructs a town
     * @param name name of town
     */
    public Town(String name){
        this.name = name;
        roads = new ArrayList<>();
    }

    /**
     * Constructs a copy of a town
     * @param templateTown town to be copied
     */
    public Town(Town templateTown){
        this.name = templateTown.name;
        this.roads = templateTown.roads;
    }

    /**
     * Gets name
     * @return name of town
     */
    public String getName(){
        return name;
    }

    /**
     * Gets adjacent roads
     * @return adjacent roads to the town
     */
    public List<Road> getRoads(){
        return roads;
    }

    /**
     * String representation of the town
     * @return name of town
     */
    @Override
    public String toString(){
        return name;
    }

    /**
     * Determine if a town is equal
     * @param o object to be compared
     * @return true if names are equal
     */
    @Override
    public boolean equals(Object o){
        if(o == null || getClass() != o.getClass()){
            return false;
        }

        Town town = (Town) o;
        return (name.equals(town.name));
    }

    /**
     * Compares towns
     * @param t town to be compared
     * @return comparison of names
     */
    @Override
    public int compareTo(Town t) {
        return name.compareTo(t.name);
    }

    /**
     * Hash code contract
     * @return hash code of the name
     */
    @Override
    public int hashCode(){
        return name.hashCode();
    }
}
