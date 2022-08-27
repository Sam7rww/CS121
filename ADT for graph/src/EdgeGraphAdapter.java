import java.util.*;

public class EdgeGraphAdapter implements EdgeGraph {

    private Graph g;

    EdgeGraphAdapter(Graph g) { this.g = g; }

    public boolean addEdge(Edge e) {
        if(g.hasEdge(e.getSrc(),e.getDst())){
            return false;
        }else{
            if(!g.hasNode(e.getSrc())) g.addNode(e.getSrc());
            if(!g.hasNode(e.getDst())) g.addNode(e.getDst());
            g.addEdge(e.getSrc(),e.getDst());
            return true;
        }
//        throw new UnsupportedOperationException();
    }

    public boolean hasNode(String n) {
        if(g.hasNode(n)){
            return true;
        }else{
            return false;
        }
//        throw new UnsupportedOperationException();
    }

    public boolean hasEdge(Edge e) {
        if(g.hasEdge(e.getSrc(),e.getDst())){
            return true;
        }else{
            return false;
        }
//        throw new UnsupportedOperationException();
    }

    public boolean removeEdge(Edge e) {
        if(g.hasEdge(e.getSrc(),e.getDst())){
            g.removeEdge(e.getSrc(),e.getDst());
            if(g.succ(e.getSrc()).size()==0 && g.pred(e.getSrc()).size()==0) g.removeNode(e.getSrc());
            if(g.succ(e.getDst()).size()==0 && g.pred(e.getDst()).size()==0) g.removeNode(e.getDst());
            return true;
        }else{
            return false;
        }

//        throw new UnsupportedOperationException();
    }

    public List<Edge> outEdges(String n) {
        List<Edge> edges = new LinkedList<>();
        List<String> succs = g.succ(n);
        for(String to : succs){
            Edge temp = new Edge(n,to);
            edges.add(temp);
        }
        return edges;
//        throw new UnsupportedOperationException();
    }

    public List<Edge> inEdges(String n) {
        List<Edge> edges = new LinkedList<>();
        List<String> preds = g.pred(n);
        for(String from : preds){
            Edge temp = new Edge(from,n);
            edges.add(temp);
        }
        return edges;
//        throw new UnsupportedOperationException();
    }

    public List<Edge> edges() {
        List<Edge> edges = new LinkedList<>();
        List<String> nodes = g.nodes();
        for(int i=0;i<nodes.size();i++){
            for(int j=0;j<nodes.size();j++){
                if(i==j) continue;
                if(g.hasEdge(nodes.get(i),nodes.get(j))){
                    Edge e = new Edge(nodes.get(i),nodes.get(j));
                    edges.add(e);
                }
            }
        }
        return edges;
//        throw new UnsupportedOperationException();
    }

    public EdgeGraph union(EdgeGraph g) {
        EdgeGraph eg = new EdgeGraphAdapter(this.g);
        List<Edge> gedges = g.edges();
        for(Edge e:gedges){
            if(!eg.hasEdge(e)){
                eg.addEdge(e);
            }
        }

        return eg;
//        throw new UnsupportedOperationException();
    }

    public boolean hasPath(List<Edge> e) {
        if(e.size()==0) return true;
        //check the e
        for(int i=0;i<(e.size()-1);i++){
            String dst = e.get(i).getDst();
            String src = e.get(i+1).getSrc();
            if(!dst.equals(src)){
                throw new BadPath();
            }
        }
        //check edges
        for(Edge edge:e){
            if(!this.hasEdge(edge)) return false;
        }

        return true;
//        throw new UnsupportedOperationException();
    }

}
