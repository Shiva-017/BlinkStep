package application;


import geography.GeographicPoint;
import hashMap.HashMap;
import hashSet.HashSet;
import util.GraphLoader;

/**
 * Class for graph, i.e current .map file map
 */
public class DataSet {
	String filePath;
	roadgraph.MapGraph graph;
	HashSet<GeographicPoint> intersections;
    private HashMap<geography.GeographicPoint,HashSet<geography.RoadSegment>>  roads;
	boolean currentlyDisplayed;

	public DataSet (String path) {
        this.filePath = path;
        graph = null;
        roads = null;
        currentlyDisplayed = false;
	}

    public void setGraph(roadgraph.MapGraph graph) {
    	this.graph = graph;
    }

    public void setRoads(HashMap<geography.GeographicPoint,HashSet<geography.RoadSegment>>  roads) { this.roads = roads; }
    public roadgraph.MapGraph getGraph(){ return graph; }
    
    /** Return the intersections in this graph.
     * @return The set of road intersections (vertices in the graph)
     */
    public HashSet<GeographicPoint> getIntersections() {
    	HashSet<GeographicPoint> intersectionsFromGraph = graph.getVertices();
    	if (intersectionsFromGraph == null) {
    		return intersections;
    	}
    	else {
    		return intersectionsFromGraph;
    	}
    }
    
    public HashMap<geography.GeographicPoint,HashSet<geography.RoadSegment>>  getRoads() { return this.roads; }

    public void initializeGraph() {
        graph = new roadgraph.MapGraph();
        roads = new HashMap<geography.GeographicPoint, HashSet<geography.RoadSegment>>();
        intersections = new HashSet<GeographicPoint>();
        GraphLoader graphLoader = new GraphLoader();
    	graphLoader.loadRoadMap(filePath, graph, roads, intersections);
    }

	public String getFilePath() {
		return this.filePath;
	}


    public Object[] getPoints() {
    	@SuppressWarnings("unchecked")
		HashSet<geography.GeographicPoint> pointSet = (HashSet<GeographicPoint>) roads.keySet();
    	return pointSet.toArray();
    }

    public boolean isDisplayed() {
    	return this.currentlyDisplayed;
    }

    public void setDisplayed(boolean value) {
    	this.currentlyDisplayed = value;
    }

}