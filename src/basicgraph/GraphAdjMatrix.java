package basicgraph;

import java.util.ArrayList;
import java.util.List;

public class GraphAdjMatrix implements Graph {

    private final int defaultNumVertices = 5;
    private int[][] adjMatrix;

    public GraphAdjMatrix() {
        adjMatrix = new int[defaultNumVertices][defaultNumVertices];
    }

    @Override
    public int getNumVertices() {
        return adjMatrix.length;
    }

    @Override
    public int getNumEdges() {
        int numEdges = 0;
        for (int[] row : adjMatrix) {
            for (int edgeCount : row) {
                numEdges += edgeCount;
            }
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
        int numVertices = getNumVertices();
        if (numVertices >= adjMatrix.length) {
            resizeAdjMatrix();
        }
    }

    private void resizeAdjMatrix() {
        int newSize = adjMatrix.length * 2;
        int[][] newAdjMatrix = new int[newSize][newSize];
        for (int i = 0; i < adjMatrix.length; i++) {
            System.arraycopy(adjMatrix[i], 0, newAdjMatrix[i], 0, adjMatrix.length);
        }
        adjMatrix = newAdjMatrix;
    }

    @Override
    public void addEdge(int v, int w) {
        implementAddEdge(v, w);
    }

    @Override
    public void implementAddEdge(int v, int w) {
        adjMatrix[v][w]++;
    }

    @Override
    public List<Integer> getNeighbors(int v) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < getNumVertices(); i++) {
            for (int j = 0; j < adjMatrix[v][i]; j++) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    @Override
    public List<Integer> getInNeighbors(int v) {
        List<Integer> inNeighbors = new ArrayList<>();
        for (int i = 0; i < getNumVertices(); i++) {
            for (int j = 0; j < adjMatrix[i][v]; j++) {
                inNeighbors.add(i);
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
        StringBuilder s = new StringBuilder("Adjacency matrix (size " + getNumVertices() + "x" + getNumVertices() + " = " + getNumVertices() * getNumVertices() + " integers):\n");
        for (int i = 0; i < getNumVertices(); i++) {
            s.append("\t").append(i).append(": ");
            for (int j = 0; j < getNumVertices(); j++) {
                for (int k = 0; k < adjMatrix[i][j]; k++) {
                    s.append(j).append(", ");
                }
            }
            s.append("\n");
        }
        return s.toString();
    }
}
