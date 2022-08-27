import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonInfo {
    private Map<String, List<String>> lines = new HashMap<>();
    private Map<String, List<String>> trips = new HashMap<>();

    public JsonInfo(Map<String, List<String>> lines, Map<String, List<String>> trips) {
        this.lines = lines;
        this.trips = trips;
    }

    public Map<String, List<String>> getLines() {
        return lines;
    }

    public void setLines(Map<String, List<String>> lines) {
        this.lines = lines;
    }

    public Map<String, List<String>> getTrips() {
        return trips;
    }

    public void setTrips(Map<String, List<String>> trips) {
        this.trips = trips;
    }

    public void printInfo(){
        System.out.println("lines info:");
        for(Map.Entry<String, List<String>> e:lines.entrySet()){
            System.out.println("line name is :"+e.getKey()+",line size is :"+e.getValue().size());
        }
        System.out.println("trips info:");
        for(Map.Entry<String, List<String>> e:trips.entrySet()){
            System.out.println("passenger name is :"+e.getKey()+",trip size is :"+e.getValue().size());
        }
    }
}
