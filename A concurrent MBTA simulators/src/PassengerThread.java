import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class PassengerThread extends Thread{

    private List<Station> stations;

    private Passenger passenger;

    private Log log;

    private MBTA mbta;

    public PassengerThread(List<Station> stations, Passenger passenger, Log log, MBTA mbta) {
        this.stations = stations;
        this.passenger = passenger;
        this.log = log;
        this.mbta = mbta;
    }

    @Override
    public void run() {
        for(int i=0;i<(stations.size()-1);i++){
            Station board = stations.get(i);
            Station deboard = stations.get(i+1);
            Train train = this.findtrain(board,deboard);
            if(train == null) throw new RuntimeException();
            //start board train
            Lock lock = mbta.trainTolock.get(train);
            Condition c = mbta.trainTolock_lc.get(lock);
            lock.lock();
            while (mbta.stationTotrain.get(board) != train){
                try {
                    c.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.passenger_boards(passenger,train,board);
            lock.unlock();
            //start deboard train
            lock.lock();
            while (mbta.stationTotrain.get(deboard) != train){
                try {
                    c.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.passenger_deboards(passenger,train,deboard);
            lock.unlock();
        }

    }

    private Train findtrain(Station s1,Station s2){
        Train train = null;
        for(Map.Entry<Train,List<Station>> entry:mbta.trainStation.entrySet()){
            List<Station> stations = entry.getValue();
            if(stations.contains(s1) && stations.contains(s2)){
                train = entry.getKey();
            }
        }
        return train;
    }
}
