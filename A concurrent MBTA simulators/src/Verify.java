import java.io.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Verify {

  public static void verify(MBTA mbta, Log log) {
//    System.out.println("---------- start verify! ----------");
    mbta.checkStart();
    for (Event e : log.events()) {
//      System.out.println("Start verify!!");
      e.replayAndCheck(mbta);
    }

    mbta.checkEnd();


  }

  public static void main(String[] args) throws FileNotFoundException {
    if (args.length != 2) {
      System.out.println("usage: ./verify <config file> <log file>");
      System.exit(1);
    }

    MBTA mbta = new MBTA();
    mbta.loadConfig(args[0]);
    Reader r = new BufferedReader(new FileReader(args[1]));
    Log log = LogJson.fromJson(r).toLog();
    verify(mbta, log);
  }
}
