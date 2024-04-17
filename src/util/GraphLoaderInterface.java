package util;

import java.util.Set;

import geography.GeographicPoint;
import geography.RoadSegment;
import graph.Graph;
import hashMap.HashMap;
import hashSet.HashSet;
import roadgraph.MapGraph;

/**
 * An interface that describes the operations of a graph loader 
 * i.e to load graph from coordinate file ( example : boston_coordinates.map)
 */

public interface GraphLoaderInterface {

    /** Creates an intersections file from road data.
     * This method reads road data from a file, identifies intersections, 
     * and writes them to an intersections file.
     * @param roadDataFile The file containing road data.
     * @param intersectionsFile The output file containing intersections.
     */
    public void createIntersectionsFile(String roadDataFile, String intersectionsFile);

    /** Loads a road map into a MapGraph.
     * This method reads road data from a file and constructs a MapGraph 
     * representing the road map.
     * @param filename The file containing road data.
     * @param map The MapGraph to load the road map into.
     */
    public void loadRoadMap(String filename, MapGraph map);

    /** Loads a road map into a MapGraph with additional parameters.
     * This method reads road data from a file, constructs a MapGraph 
     * representing the road map, and allows specifying additional parameters 
     * like segments and intersections to load.
     * @param filename The file containing road data.
     * @param map The MapGraph to load the road map into.
     * @param segments The collection of RoadSegments defining the shape of roads.
     * @param intersectionsToLoad The set of intersections to load.
     */
    public void loadRoadMap(String filename, MapGraph map, HashMap<GeographicPoint, HashSet<RoadSegment>> segments, HashSet<GeographicPoint> intersectionsToLoad);

    /** Loads a road map into a Graph.
     * This method reads road data from a file and constructs a Graph 
     * representing the road map.
     * @param filename The file containing road data.
     * @param theGraph The Graph to load the road map into.
     */
    public void loadRoadMap(String filename, Graph theGraph);

    /** Loads routes into a Graph.
     * This method reads route data from a file and constructs a Graph 
     * representing routes between airports.
     * @param filename The file containing route data.
     * @param graph The Graph to load the routes into.
     */
    public void loadRoutes(String filename, Graph graph);

    /** Loads a graph from a file.
     * This method reads graph data from a file and constructs a Graph 
     * representing the graph.
     * @param filename The file containing the graph data.
     * @param theGraph The Graph to be loaded.
     */
    public void loadGraph(String filename, Graph theGraph);

}
