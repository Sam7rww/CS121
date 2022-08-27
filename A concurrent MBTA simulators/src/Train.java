import java.util.HashMap;

public class Train extends Entity {
  private Train(String name) { super(name); }

  private static final HashMap<String,Train> trains = new HashMap<>();

  public static Train make(String name) {
    // Change this method!
    if(trains.containsKey(name)){
      return trains.get(name);
    }else{
      Train t = new Train(name);
      trains.put(name,t);
      return t;
    }
  }

  public Entity test(){
    return new Train("");
  }
}
