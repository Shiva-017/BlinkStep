package basicgraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphAdjList extends Graph {

    private Map<Integer, ArrayList<Integer>> adjListsMap;

    public GraphAdjList() {
        adjListsMap = new java.util.HashMap<Integer, ArrayList<Integer>>();
    }

    public void implementAddVertex() {
        int v = getNumVertices();
        ArrayList<Integer> neighbors = new ArrayList<Integer>();
        adjListsMap.put(v, neighbors);
    }

    public void implementAddEdge(int v, int w) {
        (adjListsMap.get(v)).add(w);

    }

    public List<Integer> getNeighbors(int v) {
        return new ArrayList<Integer>(adjListsMap.get(v));
    }

    public List<Integer> getInNeighbors(int v) {
        List<Integer> inNeighbors = new ArrayList<Integer>();
        for (int u : adjListsMap.keySet()) {
            for (int w : adjListsMap.get(u)) {
                if (v == w) {
                    inNeighbors.add(u);
                }
            }
        }
        return inNeighbors;
    }

    public List<Integer> getDistance2(int v) {
        List<Integer> twoHop = new ArrayList<Integer>();
        List<Integer> oneHop = getNeighbors(v);

        for (int neighbor : oneHop) {
            twoHop.addAll(getNeighbors(neighbor));
        }
        return twoHop;
    }

    public String adjacencyString() {
        String s = "Adjacency list";
        s += " (size " + getNumVertices() + "+" + getNumEdges() + " integers):";

        for (int v : adjListsMap.keySet()) {
            s += "\n\t" + v + ": ";
            for (int w : adjListsMap.get(v)) {
                s += w + ", ";
            }
        }
        return s;
    }
}