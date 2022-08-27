import java.util.*;

public class MoveEvent implements Event {
//  static { LogJson.registerEvent(MoveEvent.class, "Move"); }
  public final Train t; public final Station s1, s2;
  public MoveEvent(Train t, Station s1, Station s2) {
    this.t = t; this.s1 = s1; this.s2 = s2;
  }
  public boolean equals(Object o) {
    if (o instanceof MoveEvent e) {
      return t.equals(e.t) && s1.equals(e.s1) && s2.equals(e.s2);
    }
    return false;
  }
  public int hashCode() {
    return Objects.hash(t, s1, s2);
  }
  public String toString() {
    return "Train " + t + " moves from " + s1 + " to " + s2;
  }
  public List<String> toStringList() {
    return List.of(t.toString(), s1.toString(), s2.toString());
  }
  public void replayAndCheck(MBTA mbta) {
    List<Station> stations = mbta.trainStation.get(t);

    if(!mbta.stationTotrain.get(s1).equals(t)) throw new RuntimeException();

    if(!stations.contains(s1) || !stations.contains(s2)) throw new RuntimeException();
    int distance = stations.indexOf(s1) - stations.indexOf(s2);
    if(distance!=1 && distance!=(-1)) throw new RuntimeException();
    if(mbta.stationTotrain.get(s2) != null) {
      throw new RuntimeException();
    }

    //move train
    mbta.stationTotrain.replace(s1,null);
    mbta.stationTotrain.replace(s2,t);
  }
}
