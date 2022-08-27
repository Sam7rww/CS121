import java.util.*;

public class BoardEvent implements Event {
//  static { LogJson.registerEvent(BoardEvent.class, "Board"); }
  public final Passenger p; public final Train t; public final Station s;
  public BoardEvent(Passenger p, Train t, Station s) {
    this.p = p; this.t = t; this.s = s;
  }
  public boolean equals(Object o) {
    if (o instanceof BoardEvent e) {
      return p.equals(e.p) && t.equals(e.t) && s.equals(e.s);
    }
    return false;
  }
  public int hashCode() {
    return Objects.hash(p, t, s);
  }
  public String toString() {
    return "Passenger " + p + " boards " + t + " at " + s;
  }
  public List<String> toStringList() {
    return List.of(p.toString(), t.toString(), s.toString());
  }
  public void replayAndCheck(MBTA mbta) {
    List<Station> stations = mbta.passengerStation.get(p);
    //test passenger
    if(!stations.contains(s)) throw new RuntimeException();
    if(!mbta.passengerPosition.get(p).equals(s)) throw new RuntimeException();
    if(!mbta.passengerState.get(p).equals("station")) throw new RuntimeException();

    //test whether the train is in station
    if(!mbta.stationTotrain.get(s).equals(t)) throw new RuntimeException();

//    mbta.passengerPosition.replace(p,t);
    mbta.passengerState.replace(p,"train");
  }
}
