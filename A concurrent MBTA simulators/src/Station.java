import java.util.HashMap;

public class Station extends Entity {
  private Station(String name) { super(name); }

  private static final HashMap<String,Station> stations = new HashMap<>();

  public static Station make(String name) {
    // Change this method!
    if(stations.containsKey(name)){
      return stations.get(name);
    }else{
      Station s = new Station(name);
      stations.put(name,s);
      return s;
    }
  }
}
