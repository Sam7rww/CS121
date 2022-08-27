import java.util.HashMap;

public class Passenger extends Entity {
  private Passenger(String name) { super(name); }

  private static final HashMap<String,Passenger> passengers = new HashMap<>();

  public static Passenger make(String name) {
    // Change this method!
    if(passengers.containsKey(name)){
      return passengers.get(name);
    }else{
      Passenger p = new Passenger(name);
      passengers.put(name,p);
      return p;
    }
  }
}
