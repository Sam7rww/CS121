import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Main {

    // Run "java -ea Main" to run with assertions enabled (If you run
    // with assertions disabled, the default, then assert statements
    // will not execute!)

    public static void test1() {
        Graph g = new ListGraph();
        assert g.addNode("a");
        assert g.hasNode("a");
    }

    public static void test2() {
        Graph g = new ListGraph();
        EdgeGraph eg = new EdgeGraphAdapter(g);
        Edge e = new Edge("a", "b");
        assert eg.addEdge(e);
        assert eg.hasEdge(e);
    }



    public static void main(String[] args) {
        System.out.println("Hello world!");
        test1();
        test2();
        Graph g = test_ListGraph();
        test_EdgeGraph(g);
    }

    public static Graph test_ListGraph(){
        Graph g = new ListGraph();
        g.addNode("A");
        g.addNode("B");
        g.addNode("C");
        g.addNode("D");
        g.addNode("E");
        g.addNode("F");
        g.addNode("G");
        g.addNode("H");
        g.addNode("I");
        g.addNode("J");
        g.addNode("K");
        g.addNode("L");
        g.addNode("M");
        g.addNode("N");
        g.addNode("O");
        g.addNode("P");
        g.addNode("Q");
        g.addNode("R");

        g.addEdge("A","B");
        g.addEdge("B","C");
        g.addEdge("C","D");
        g.addEdge("D","E");
        g.addEdge("E","F");
        g.addEdge("F","G");
        g.addEdge("A","H");
        g.addEdge("H","J");
        g.addEdge("B","P");
        g.addEdge("C","I");
        g.addEdge("D","L");
        g.addEdge("L","M");
        g.addEdge("M","Q");
        g.addEdge("Q","R");
        g.addEdge("E","K");
        g.addEdge("F","O");
        g.addEdge("G","N");

        g.addEdge("J","M");
        g.addEdge("P","R");
        g.addEdge("I","K");
        g.addEdge("R","A");
        g.addEdge("K","H");
        g.addEdge("O","N");
        g.addEdge("N","L");

        //test add
        assert g.addNode("S");
        assert g.addNode("W");
        assert g.addEdge("C","S");
        assert g.addEdge("A","W");
        assert g.addEdge("A","S");
        assert g.addEdge("S","B");
        assert g.addEdge("S","G");

        //test has
        assert g.hasNode("A");
        assert !g.hasNode("Z");
        assert g.hasEdge("B","C");
        assert !g.hasEdge("M","N");

        //test remove
        assert g.removeEdge("S","G");
        assert !g.hasEdge("S","G");
        assert g.removeNode("S");
        assert !g.hasNode("S");
        assert !g.hasEdge("A","S");

        //nodes
        assert g.nodes().size() == 19;
        assert g.succ("A").size() == 3; //B,H,W
        assert g.pred("A").get(0).equals("R");
        assert g.pred("R").size() == 2; //Q,P

        //subgraph
        Set<String> testnodes = new HashSet<>();
        testnodes.add("A");
        testnodes.add("B");
        testnodes.add("C");
        testnodes.add("D");
        testnodes.add("P");
        testnodes.add("R");
        Graph subgraph = g.subGraph(testnodes);
        assert subgraph.nodes().size() == 6;
        assert subgraph.hasEdge("R","A");
        assert subgraph.hasEdge("P","R");
        assert !subgraph.hasEdge("D","L");

        //test connnect
        assert g.connected("A","N");
        assert g.connected("B","W");
        assert g.connected("A","K");

        return g;
    }

    public static void test_EdgeGraph(Graph g){
        EdgeGraph eg = new EdgeGraphAdapter(g);

        //construct
        assert eg.addEdge(new Edge("A","S"));
        assert eg.addEdge(new Edge("C","S"));
        assert eg.addEdge(new Edge("S","G"));
        assert eg.addEdge(new Edge("S","B"));
        assert eg.hasNode("S");
        assert !eg.hasNode("Z");
        assert eg.hasEdge(new Edge("C","S"));

        //out,in
        assert eg.outEdges("A").size() == 4;
        assert eg.inEdges("A").size() == 1;
        assert eg.inEdges("R").size() == 2;

        assert eg.edges().size() == 29;

        //remove
        assert eg.removeEdge(new Edge("A","W"));
        assert !eg.hasEdge(new Edge("A","W"));
        assert !eg.hasNode("W");

        assert eg.edges().size() == 28;

        //has path
        List<Edge> edges = new LinkedList<>();
        edges.add(new Edge("A","B"));
        edges.add(new Edge("B","C"));
        edges.add(new Edge("D","L"));
        edges.add(new Edge("L","M"));
        edges.add(new Edge("M","Q"));
        try {
            eg.hasPath(edges);
        }catch (Exception e){
            assert e instanceof BadPath;
        }
        edges.add(2,new Edge("C","D"));
        assert eg.hasPath(edges);

        //union
        EdgeGraph eg2 = new EdgeGraphAdapter(new ListGraph());
        eg2.addEdge(new Edge("M","N"));
        eg2.addEdge(new Edge("W","V"));
        eg2.addEdge(new Edge("N","R"));
        EdgeGraph eg3 = eg.union(eg2);
        assert eg3.edges().size()==31;
        assert eg3.hasEdge(new Edge("M","N"));
        assert eg3.hasEdge(new Edge("W","V"));
        assert eg3.hasEdge(new Edge("N","R"));
        assert !eg3.hasEdge(new Edge("Q","Y"));

    }

}