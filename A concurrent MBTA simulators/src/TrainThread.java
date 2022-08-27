import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TrainThread extends Thread{
    private volatile boolean isCancelled;
    //store all the station train need to stop
    private List<Station> stations;
    //store the info of this train
    private Train train;

    private Lock lock;
    private Condition condition;

    private Log log;

    private MBTA mbta;

    private int num=0;

    public TrainThread(List<Station> stations, Lock lock,Train train,Log log,MBTA mbta){
        this.stations = stations;
        this.train = train;
        this.lock = lock;
        this.log = log;
        this.mbta = mbta;
        isCancelled = false;
        condition = mbta.trainTolock_lc.get(this.lock);
        //set the initial state of train
        mbta.stationTotrain.replace(stations.get(0),train);
    }

    @Override
    public void run() {
        int length = stations.size();
        //which station the train now at
        int position = 0;
        //certify the orientation
        boolean orientation = true;
        while(!isCancelled && num<=100){
            //first, determine the orientation of the train
            if(position == length-1){
                orientation = false;
            }else if(position == 0){
                orientation = true;
            }

            //run the train
            Station current;
            Station next;
            if(orientation){
                current = stations.get(position);
                next = stations.get(position+1);
            }else{
                current = stations.get(position);
                next = stations.get(position-1);
            }
            //go to next station
            //first lock the train
            lock.lock();
            if(mbta.commonStation.containsKey(next)){
                //next station is common station, so we need to use lock
                Lock l = mbta.commonStation.get(next);
                Condition c = mbta.commonStation_lc.get(l);
                l.lock();
                while (mbta.stationTotrain.get(next) != null){
                    try {
                        c.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mbta.stationTotrain.replace(next,train);
                log.train_moves(train,current,next);
                this.leaveStation(current);
                //c.signalAll();
                l.unlock();
            }else{
                //next station isn't common station
                mbta.stationTotrain.replace(next,train);
                log.train_moves(train,current,next);
                this.leaveStation(current);
            }

            //leave from current station
//            if(MBTA.commonStation.containsKey(current)){
//                //current station is common station, so we need to use lock
//                Lock l = MBTA.commonStation.get(current);
//                Condition c = MBTA.commonStation_lc.get(l);
//                l.lock();
//                MBTA.stationTotrain.replace(current,null);
//                c.signalAll();
//                l.unlock();
//            }else{
//                //current station isn't common station
//                MBTA.stationTotrain.replace(current,null);
//            }

            //after the train movement
            condition.signalAll();
            lock.unlock();

            num++;

            if(orientation){
                position++;
            }else{
                position--;
            }
            if(isCancelled) break;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void cancel() { isCancelled=true; }

    public void leaveStation(Station current){
        //leave from current station
        if(mbta.commonStation.containsKey(current)){
            //current station is common station, so we need to use lock
            Lock l = mbta.commonStation.get(current);
            Condition c = mbta.commonStation_lc.get(l);
            l.lock();
            mbta.stationTotrain.replace(current,null);
            c.signalAll();
            l.unlock();
        }else{
            //current station isn't common station
            mbta.stationTotrain.replace(current,null);
        }
    }
}
