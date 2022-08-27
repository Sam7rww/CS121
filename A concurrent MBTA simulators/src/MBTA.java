import java.io.File;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MBTA {

  public  Map<Train,List<Station>> trainStation = new HashMap<>();

  public  Map<Passenger,List<Station>> passengerStation = new HashMap<>();

  //store all the station state(include all train) -> which train in now in which station, no train will be null
  public  Map<Station, Train> stationTotrain = new HashMap<>();

  //store the common station, which require lock to write
  public  Map<Station, Lock> commonStation = new HashMap<>();
  public  Map<Lock, Condition> commonStation_lc = new HashMap<>();

  //each train has its own lock
  public  Map<Train, Lock> trainTolock = new HashMap<>();
  public  Map<Lock, Condition> trainTolock_lc = new HashMap<>();

  //for verify, passenger's position
  public Map<Passenger,Station> passengerPosition = new HashMap<>();
  public Map<Passenger,String> passengerState = new HashMap<>();

  private final HandleJson handleJson = new HandleJson();

  // Creates an initially empty simulation
  public MBTA() { }

  // Adds a new transit line with given name and stations
  public void addLine(String name, List<String> stations) {
    Train t = Train.make(name);

    //create a lock for temporary train
    ReentrantLock lock = new ReentrantLock();
    trainTolock.put(t,lock);
    Condition c = lock.newCondition();
    trainTolock_lc.put(lock,c);

    //store trainStation information
    List<Station> lineStation = new LinkedList<>();
    for(String n:stations){
      Station s = Station.make(n);

      if(!stationTotrain.containsKey(s)){//station list don't have this station, so add it
        stationTotrain.put(s,null);
      }else{//station list has this station and this is common station, add lock
        if(!commonStation.containsKey(s)){//commonStation first appears, we need add a lock in Map
          ReentrantLock lock1 = new ReentrantLock();
          commonStation.put(s,lock1);
          Condition c1 =lock1.newCondition();
          commonStation_lc.put(lock1,c1);
        }
        //else commonStation has this station information, there is no need to one more lock
      }
      lineStation.add(s);
    }
    trainStation.put(t,lineStation);
    //initial train position at start
    stationTotrain.replace(lineStation.get(0),t);
  }

  // Adds a new planned journey to the simulation
  public void addJourney(String name, List<String> stations) {
    Passenger p = Passenger.make(name);
    List<Station> passStation = new LinkedList<>();
    for(String n:stations){
      Station s = Station.make(n);
      passStation.add(s);
    }
    passengerStation.put(p,passStation);
    //initial passenger position at start
    passengerPosition.put(p,passStation.get(0));
    passengerState.put(p,"station");
  }

  // reset to an empty simulation
  public void reset() {
    trainStation = new HashMap<>();
    passengerStation = new HashMap<>();

    stationTotrain = new HashMap<>();
    commonStation = new HashMap<>();
    commonStation_lc = new HashMap<>();
    trainTolock = new HashMap<>();
    trainTolock_lc = new HashMap<>();

    passengerPosition = new HashMap<>();
    passengerState = new HashMap<>();
  }

  // adds simulation configuration from a file
  public void loadConfig(String filename) {
    File file = new File(filename);
    if(!file.exists()) throw new RuntimeException();
    try {
      JsonInfo info = handleJson.readJsonStream(filename);
      //invoke addLine method to insert new train line
      for(Map.Entry<String,List<String>> e: info.getLines().entrySet()){
        this.addLine(e.getKey(),e.getValue());
      }
      //invoke addJourney method to insert new passenger trip
      for(Map.Entry<String,List<String>> e: info.getTrips().entrySet()){
        this.addJourney(e.getKey(),e.getValue());
      }
    }catch (Exception e){
      e.printStackTrace();
    }

  }

  // Return normally if initial simulation conditions are satisfied, otherwise
  // raises an exception
  public void checkStart() {
    for(Map.Entry<Train,List<Station>> trainSet : trainStation.entrySet()){
      Train t = trainSet.getKey();
      Station s = trainSet.getValue().get(0);
      if(!stationTotrain.get(s).equals(t)) throw new RuntimeException();
    }
  }

  // Return normally if final simulation conditions are satisfied, otherwise
  // raises an exception
  public void checkEnd() {
    //test whether all passenger arrive at end
    for(Map.Entry<Passenger,List<Station>> ps:passengerStation.entrySet()){
      Passenger p = ps.getKey();
      Station finalpos = ps.getValue().get(ps.getValue().size()-1);
      if(!passengerPosition.get(p).equals(finalpos)) throw new RuntimeException();
    }
  }

//  test the utility of handleJson class
//  public static void main(String[] args) {
//    HandleJson handleJson = new HandleJson();
//    try {
//      JsonInfo info = handleJson.readJsonStream("sample.json");
//      info.printInfo();
//
//    }catch (Exception e){
//      e.printStackTrace();
//    }
//
//  }

}
