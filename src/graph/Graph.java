package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import hashMap.HashMap;


public abstract class Graph implements GraphADT {
    private int numVertices;
    private int numEdges;
    private HashMap<Integer, String> vertexLabels;

    public Graph() {
        numVertices = 0;
        numEdges = 0;
        vertexLabels = null;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public int getNumEdges() {
        return numEdges;
    }

    public int addVertex() {
        implementAddVertex();
        numVertices++;
        return (numVertices - 1);
    }

    public abstract void implementAddVertex();

    public void addEdge(int v, int w) {
        numEdges++;
        if (v < numVertices && w < numVertices) {
            implementAddEdge(v, w);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public abstract void implementAddEdge(int v, int w);

    public abstract List<Integer> getNeighbors(int v);

    public abstract List<Integer> getInNeighbors(int v);

    public List<Integer> degreeSequence() {
        List<Integer> degreeSeq = new ArrayList<Integer>();
        for (int i = 0; i < this.getNumVertices(); i++) {
            int degree = getNeighbors(i).size() + getInNeighbors(i).size();
            degreeSeq.add(degree);
        }

        Collections.sort(degreeSeq, Collections.reverseOrder());
        return degreeSeq;
    }

    public abstract List<Integer> getDistance2(int v);

    public String toString() {
        String s = "\nGraph with " + numVertices + " vertices and " + numEdges + " edges.\n";
        s += "Degree sequence: " + degreeSequence() + ".\n";
        if (numVertices <= 20) s += adjacencyString();
        return s;
    }

    public abstract String adjacencyString();

    public void initializeLabels() {
        vertexLabels = new HashMap<Integer, String>();
    }

    public boolean hasVertex(int v) {
        return v < getNumVertices();
    }

    public boolean hasVertex(String s) {
        return vertexLabels.containsValue(s);
    }

    public void addLabel(int v, String s) {
        if (v < getNumVertices() && !vertexLabels.containsKey(v)) {
            vertexLabels.put(v, s);
        } else {
            System.out.println("ERROR: tried to label a vertex that is out of range or already labeled");
        }
    }

    public String getLabel(int v) {
        if (vertexLabels.containsKey(v)) {
            return vertexLabels.get(v);
        } else return null;
    }

    public int getIndex(String s) {
        for (HashMap.Entry<Integer, String> entry : vertexLabels.entrySet()) {
            if (entry.getValue().equals(s))
                return entry.getKey();
        }
        System.out.println("ERROR: No vertex with this label");
        return -1;
    }
}
