import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

public class Sim {

  public static void run_sim(MBTA mbta, Log log) {
    //start all the passenger
    List<PassengerThread> threads_passenger = new ArrayList<>();
    for(Map.Entry<Passenger,List<Station>> e:mbta.passengerStation.entrySet()){
      PassengerThread thread = new PassengerThread(e.getValue(),e.getKey(),log,mbta);
      threads_passenger.add(thread);
      thread.start();
    }

    //start all the train
    List<TrainThread> threads_train = new ArrayList<>();
    for(Map.Entry<Train, List<Station>> e:mbta.trainStation.entrySet()){
      Train t = e.getKey();
      List<Station> list = e.getValue();
      Lock l = mbta.trainTolock.get(t);
      TrainThread thread = new TrainThread(list,l,t,log,mbta);
      threads_train.add(thread);
      thread.start();
    }

    //terminal thread
    for(PassengerThread th:threads_passenger){
      try {
        th.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    for(TrainThread th:threads_train){
      th.cancel();
    }

  }

  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.out.println("usage: ./sim <config file>");
      System.exit(1);
    }

    MBTA mbta = new MBTA();
    mbta.loadConfig(args[0]);

    Log log = new Log();

    run_sim(mbta, log);

    String s = new LogJson(log).toJson();
    PrintWriter out = new PrintWriter("log.json");
    out.print(s);
    out.close();

    mbta.reset();
    mbta.loadConfig(args[0]);
    Verify.verify(mbta, log);
  }
//  public static void main(String[] args) throws Exception {
//
//    MBTA mbta = new MBTA();
//    mbta.loadConfig("sample.json");
//
//    Log log = new Log();
//
//    run_sim(mbta, log);
//
//    String s = new LogJson(log).toJson();
//    PrintWriter out = new PrintWriter("log.json");
//    out.print(s);
//    out.close();
//
//    Thread.sleep(5000);
//
//    mbta.reset();
//    mbta.loadConfig("sample.json");
//    Verify.verify(mbta, log);
//  }
}
