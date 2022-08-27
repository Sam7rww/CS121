import java.util.*;

public class ListGraph implements Graph {
    private HashMap<String, LinkedList<String>> nodes = new HashMap<>();

    public boolean addNode(String n) {
        if(nodes.containsKey(n)){
            //check whether n is in nodes
            return false;
        }else{
            LinkedList<String> nodelist = new LinkedList<>();
            nodes.put(n,nodelist);
            return true;
        }
        //throw new UnsupportedOperationException();
    }

    public boolean addEdge(String n1, String n2) {
        if(!nodes.containsKey(n1) || !nodes.containsKey(n2)){
            throw new NoSuchElementException();
        }else if(nodes.get(n1).contains(n2)){
            //check whether there are n1 to n2
            return false;
        }else{
            //add edge
            nodes.get(n1).add(n2);
            return true;
        }
//        throw new UnsupportedOperationException();
    }

    public boolean hasNode(String n) {
        if(nodes.containsKey(n)){
            return true;
        }else{
            return false;
        }
//        throw new UnsupportedOperationException();
    }

    public boolean hasEdge(String n1, String n2) {
        if(nodes.containsKey(n1) && nodes.containsKey(n2)){
            return nodes.get(n1).contains(n2);
        }else{
            return false;
        }
//        throw new UnsupportedOperationException();
    }

    public boolean removeNode(String n) {
        if(!nodes.containsKey(n)){
            return false;
        }else{
            nodes.remove(n);
            for(Map.Entry<String, LinkedList<String>> entries:nodes.entrySet()){
                LinkedList<String> templist = entries.getValue();
                if(templist.contains(n)) templist.remove(n);
            }
            return true;
        }
//        throw new UnsupportedOperationException();
    }

    public boolean removeEdge(String n1, String n2) {
        if(!nodes.containsKey(n1) || !nodes.containsKey(n2)){
            throw new NoSuchElementException();
        }else{
            nodes.get(n1).remove(n2);
            return true;
        }

//        throw new UnsupportedOperationException();
    }

    public List<String> nodes() {
        List<String> allnodes = new LinkedList<>();
        allnodes.addAll(nodes.keySet());
        return allnodes;
//        throw new UnsupportedOperationException();
    }

    public List<String> succ(String n) {
        if(!nodes.containsKey(n)){
            throw new NoSuchElementException();
        }else{
            return nodes.get(n);
        }
//        throw new UnsupportedOperationException();
    }

    public List<String> pred(String n) {
        if(!nodes.containsKey(n)){
            throw new NoSuchElementException();
        }else{
            List<String> predecessor = new LinkedList<>();
            for(Map.Entry<String,LinkedList<String>> entry:nodes.entrySet()){
                LinkedList<String> temp = entry.getValue();
                if(temp.contains(n)){
                    predecessor.add(entry.getKey());
                }
            }
            return predecessor;
        }
//        throw new UnsupportedOperationException();
    }

    public Graph union(Graph g) {
        Graph unionG = new ListGraph();
        List<String> gnodes = g.nodes();
        //add two graph nodes
        for(String a : gnodes){
            unionG.addNode(a);
        }
        for(String a : nodes.keySet()){
            if(!unionG.hasNode(a)) unionG.addNode(a);
        }
        //add two graph edge
        for(String from : gnodes){
            List<String> succ = g.succ(from);
            for(String to:succ) unionG.addEdge(from,to);
        }
        for(String from : nodes.keySet()){
            List<String> succ = nodes.get(from);
            for(String to:succ){
                if(!unionG.hasEdge(from,to)) unionG.addEdge(from,to);
            }
        }

        return unionG;
//        throw new UnsupportedOperationException();
    }

    public Graph subGraph(Set<String> nodes) {
        Graph unionG = new ListGraph();
        //add sub_nodes into new Graph
        for(String currentnode: this.nodes.keySet()){
            if(nodes.contains(currentnode)) unionG.addNode(currentnode);
        }
        //add sub_edge into new Graph
        for(String includenode: unionG.nodes()){
            List<String> edge_target = this.succ(includenode);
            for(String tonode:edge_target){
                if(unionG.hasNode(tonode)) unionG.addEdge(includenode,tonode);
            }
        }

        return unionG;
//        throw new UnsupportedOperationException();
    }

    public boolean connected(String n1, String n2) {
        if(!nodes.containsKey(n1) || !nodes.containsKey(n2)){
            throw new NoSuchElementException();
        }
        if(n1.equals(n2)) return true;
        HashSet<String> searchSet = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        //BFS
        queue.add(n1);
        searchSet.add(n1);
        while (!queue.isEmpty()){
            String a = queue.poll();
            if(a.equals(n2)) return true;
            List<String> succs = this.succ(a);
            for(String next:succs){
                if(!searchSet.contains(next)){
                    queue.add(next);
                    searchSet.add(next);
                }
            }
        }

//        List<String> firstsucc = nodes.get(n1);
//        for(String a:firstsucc){
//            if(a.equals(n2)){
//                return true;
//            }else {
//                searchSet.add(a);
//            }
//        }
//        while (searchSet.size()!=0){
//            Iterator<String> iterator = searchSet.iterator();
//            String searchnode = iterator.next();
//            List<String> templist = nodes.get(searchnode);
//            for(String b:templist){
//                if(b.equals(n2)){
//                    return true;
//                }else{
//                    searchSet.add(b);
//                }
//            }
//            searchSet.remove(searchnode);
//        }
        return false;
//        throw new UnsupportedOperationException();
    }
}
