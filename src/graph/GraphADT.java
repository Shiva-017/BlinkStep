package graph;

import java.util.List;

public interface GraphADT {
    int getNumVertices();
    int getNumEdges();
    int addVertex();
    void implementAddVertex();
    void addEdge(int v, int w);
    void implementAddEdge(int v, int w);
    List<Integer> getNeighbors(int v);
    List<Integer> getInNeighbors(int v);
    List<Integer> getDistance2(int v);
    List<Integer> degreeSequence();
    String adjacencyString();
}
