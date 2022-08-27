import static org.junit.Assert.*;
import org.junit.*;

import java.util.List;

public class Tests {
  @Test public void testPass() {
    assertTrue("true should be true", true);
  }

  @Test public void test() {
    MBTA mbta = new MBTA();
    List<String> redline = List.of( "Malden Center", "Assembly", "Community College", "State","Chinatown","Davis", "Harvard", "Downtown Crossing", "South Station");
    mbta.addLine("red", redline);
    List<String> PeterPark = List.of("Davis","Downtown Crossing");
    mbta.addJourney("PeterPark",PeterPark);
    List<String> PeterSteve = List.of("Assembly","Harvard");
    mbta.addJourney("PeterSteve",PeterSteve);

    Train red = Train.make("red");

    Station mc = Station.make("Malden Center");
    Station assembly = Station.make("Assembly");
    Station cc = Station.make("Community College");
    Station state = Station.make("State");
    Station chinatown = Station.make("Chinatown");
    Station davis = Station.make("Davis");
    Station harvard = Station.make("Harvard");
    Station dc = Station.make("Downtown Crossing");
    Station ss = Station.make("South Station");

    Passenger pp = Passenger.make("PeterPark");
    Passenger ps = Passenger.make("PeterSteve");

    Log log = new Log();

    log.train_moves(red,mc,assembly);
    log.passenger_boards(ps,red,assembly);
    log.train_moves(red,assembly,cc);
    log.train_moves(red,cc,state);
    log.train_moves(red,state,chinatown);
    log.train_moves(red,chinatown,davis);
    log.passenger_boards(pp,red,davis);
    log.train_moves(red,davis,harvard);
    log.passenger_deboards(ps,red,harvard);
    log.train_moves(red,harvard,dc);
    log.train_moves(red,dc,ss);
    log.train_moves(red,ss,dc);
    log.passenger_deboards(pp,red,dc);
    log.train_moves(red,dc,harvard);

    Verify.verify(mbta,log);
  }

}
