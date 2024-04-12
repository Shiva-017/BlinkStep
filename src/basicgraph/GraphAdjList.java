package basicgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphAdjList implements Graph {

    private Map<Integer, ArrayList<Integer>> adjListsMap;

    public GraphAdjList() {
        adjListsMap = new HashMap<>();
    }

    @Override
    public int getNumVertices() {
        return adjListsMap.size();
    }

    @Override
    public int getNumEdges() {
        int numEdges = 0;
        for (List<Integer> neighbors : adjListsMap.values()) {
            numEdges += neighbors.size();
        }
        return numEdges;
    }

    @Override
    public int addVertex() {
        implementAddVertex();
        return getNumVertices() - 1;
    }

    @Override
    public void implementAddVertex() {
        int v = getNumVertices();
        ArrayList<Integer> neighbors = new ArrayList<>();
        adjListsMap.put(v, neighbors);
    }

    @Override
    public void addEdge(int v, int w) {
        implementAddEdge(v, w);
    }

    @Override
    public void implementAddEdge(int v, int w) {
        adjListsMap.get(v).add(w);
    }

    @Override
    public List<Integer> getNeighbors(int v) {
        return new ArrayList<>(adjListsMap.get(v));
    }

    @Override
    public List<Integer> getInNeighbors(int v) {
        List<Integer> inNeighbors = new ArrayList<>();
        for (Map.Entry<Integer, ArrayList<Integer>> entry : adjListsMap.entrySet()) {
            int u = entry.getKey();
            List<Integer> neighbors = entry.getValue();
            if (neighbors.contains(v)) {
                inNeighbors.add(u);
            }
        }
        return inNeighbors;
    }

    @Override
    public List<Integer> getDistance2(int v) {
        List<Integer> twoHop = new ArrayList<>();
        List<Integer> oneHop = getNeighbors(v);
        for (int neighbor : oneHop) {
            twoHop.addAll(getNeighbors(neighbor));
        }
        return twoHop;
    }

    @Override
    public List<Integer> degreeSequence() {
        List<Integer> degreeSeq = new ArrayList<>();
        for (int i = 0; i < getNumVertices(); i++) {
            int degree = getNeighbors(i).size() + getInNeighbors(i).size();
            degreeSeq.add(degree);
        }
        degreeSeq.sort((a, b) -> Integer.compare(b, a)); // Reverse order
        return degreeSeq;
    }

    @Override
    public String adjacencyString() {
        StringBuilder s = new StringBuilder("Adjacency list (size " + getNumVertices() + "+" + getNumEdges() + " integers):\n");
        for (Map.Entry<Integer, ArrayList<Integer>> entry : adjListsMap.entrySet()) {
            int v = entry.getKey();
            List<Integer> neighbors = entry.getValue();
            s.append("\t").append(v).append(": ");
            for (int w : neighbors) {
                s.append(w).append(", ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}

