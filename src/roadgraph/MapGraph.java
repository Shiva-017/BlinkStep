/*
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedList;
import queueADT.Queue;
import priorityQueueADT.PriorityQueue;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	private Map<GeographicPoint, MapNode> intersections;
	private int numVertices;
	private int numEdges;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		intersections = new HashMap<GeographicPoint, MapNode>();
		numVertices = 0;
		numEdges = 0;
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		return numVertices;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		
		return intersections.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		return numEdges;
	}

	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		if (location == null || intersections.containsKey(location)) {
			return false;		
		}
		
		MapNode intersection = new MapNode(location);
		intersections.put(location, intersection);
		numVertices++;
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		
		if (from == null || to == null || roadName == null || roadType == null || length < 0) {
			throw new IllegalArgumentException();
		}
		
		if (!intersections.containsKey(from) || !intersections.containsKey(to)) {
			throw new IllegalArgumentException();
		}
		
		MapEdge road = new MapEdge(from, to, roadName, roadType, length);
		
		intersections.get(from).getNeighbors().add(intersections.get(to));
		intersections.get(from).getRoadList().add(road);		
		
		numEdges++;		
	}
	
	/** Print MapGraph Attributes
	 * @return s - information of MapGraph each intersection with
	 * list of all it's edge info	  
	 */
	public void printGraph() {
		for (GeographicPoint key: intersections.keySet()) {
			System.out.println("vert: " + key);			
			for (MapEdge e: intersections.get(key).getRoadList()) {
				System.out.println(e);
			}
			System.out.println("\n\n");			
		}
	}

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		Set<MapNode> visited = new HashSet<MapNode>();
		Queue<MapNode> queue = new Queue<>();
		Map<MapNode, MapNode> parent = new HashMap<MapNode, MapNode>();
		
		boolean pathFound = false;
		
		MapNode startNode = intersections.get(start);
		MapNode goalNode = intersections.get(goal);
		
		queue.enqueue(startNode);
		visited.add(startNode);		
		
		while (!queue.isEmpty()) {
			MapNode currentNode = queue.dequeue();
			nodeSearched.accept(currentNode.getLocation());
			
			if (currentNode.toString().equals(goalNode.toString())) {
				pathFound = true;
				break;
			}
			
			for (MapNode neighbor: currentNode.getNeighbors()) {
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					parent.put(neighbor, currentNode);
					queue.enqueue(neighbor);
				}
			}
			
		}
		
		return getPath(startNode, goalNode, parent, pathFound);				
	}
	
	/** Helper private method that constructs a path 
	 * from start node to goal node by tracing back using 
	 * parent Map
	 * 
	 * @param start
	 * @param goal
	 * @param parent
	 * @param pathFound
	 * @return path from start to goal 
	 * 
	 */
	private List<GeographicPoint> getPath(MapNode start, MapNode goal, 
			Map<MapNode, MapNode> parent, boolean pathFound) {
		
		if (pathFound == false) {
			System.out.println("There is no path found from " + start + " to " + goal + ".");
			return null;
		}
		
		List<GeographicPoint> path = new LinkedList<GeographicPoint>();
		MapNode currNode = goal;
		
		while (true) {
			if (currNode.toString().equals(start.toString())) {
				break;
			}			
			MapNode prevNode = parent.get(currNode);
			path.add(currNode.getLocation());
			currNode = prevNode;			
		}
		
		path.add(start.getLocation());
		Collections.reverse(path);
		
		System.out.println("Path: " + path);
		return path;
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		HashSet<MapNode> visited = new HashSet<MapNode>();
		PriorityQueue<MapNode> priorityQueue = new PriorityQueue<>();
		HashMap<MapNode, MapNode> parent = new HashMap<MapNode, MapNode>();
		
		Integer inf = Integer.MAX_VALUE;
		boolean pathFound = false;
		MapNode startNode = intersections.get(start);
		MapNode goalNode = intersections.get(goal);
		
		for (GeographicPoint location: intersections.keySet()) {
			intersections.get(location).setDistance(inf.doubleValue());
		}
		
		intersections.get(start).setDistance(0.0);
		priorityQueue.add(startNode);
		
		int count = 0;
		
		while (!priorityQueue.isEmpty()) {
			MapNode currentNode = priorityQueue.remove();
			count++;
			nodeSearched.accept(currentNode.getLocation());
			
			if (!visited.contains(currentNode)) {
				visited.add(currentNode);
				
				if (currentNode.toString().equals(goalNode.toString())) {
					pathFound = true;
					break;
				}
				
				for (MapEdge road: currentNode.getRoadList()) {					
					MapNode neighbor = intersections.get(road.getEndPoint());
					if (!visited.contains(neighbor)) {
						Double minDist = currentNode.getDistance() + road.getLength();
						if (minDist < neighbor.getDistance()) {
							neighbor.setDistance(minDist);							
							parent.put(neighbor, currentNode);
							priorityQueue.add(neighbor);
						}
						
					}
				}				
			}
			
		}		
		System.out.println("Dijkstra total No. of Node-visited: " + count);
		return getPath(startNode, goalNode, parent, pathFound);		
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		HashSet<MapNode> visited = new HashSet<MapNode>();
		PriorityQueue<MapNode> priorityQueue = new PriorityQueue<>();
		HashMap<MapNode, MapNode> parent = new HashMap<MapNode, MapNode>();
		 
		Integer inf = Integer.MAX_VALUE;
		boolean pathFound = false;
		MapNode startNode = intersections.get(start);
		MapNode goalNode = intersections.get(goal);
				
		for (GeographicPoint location: intersections.keySet()) {
			intersections.get(location).setDistance(inf.doubleValue());
			intersections.get(location).setHCost(goalNode);
			intersections.get(location).setAstar();
		}
		
		intersections.get(start).setDistance(0.0);
		priorityQueue.add(startNode);	
		
		int count = 0;
				
		while (!priorityQueue.isEmpty()) {
			MapNode currentNode = priorityQueue.remove();
			count++;
			nodeSearched.accept(currentNode.getLocation());
					
			if (!visited.contains(currentNode)) {
				visited.add(currentNode);
				
				if (currentNode.toString().equals(goalNode.toString())) {
					pathFound = true;
					break;
				}
						
				for (MapEdge road: currentNode.getRoadList()) {					
					MapNode neighbor = intersections.get(road.getEndPoint());
					System.out.println("Road-type: " + road.getType());
					if (!visited.contains(neighbor)) {
						
						Double minDist = currentNode.getDistance() + road.getLength();
						
						if (minDist < neighbor.getDistance()) {
							neighbor.setDistance(minDist);							
							parent.put(neighbor, currentNode);
							priorityQueue.add(neighbor);
						}
								
					}
				}				
			}
					
		}	
		
		System.out.println("Astar total No. of Node-visited: " + count);
				
		return getPath(startNode, goalNode, parent, pathFound);		
		
	}

	/**
	 * Performs a bidirectional search algorithm to find the shortest path from the start point to the end point.
	 * 
	 * @param start The starting location
	 * @param end The goal location
	 * @return A list of GeographicPoint objects representing the shortest path from start to end (including both start and end points),
	 *         or an empty list if no path is found.
	 */
	public List<GeographicPoint> bidirectionalSearch(GeographicPoint start, GeographicPoint end) {
	    return bidirectionalSearch(start, end, (x) -> {});
	}

	 
	/**
	 * Performs a bidirectional search algorithm to find the shortest path from the start point to the end point.
	 * 
	 * @param start The starting location
	 * @param end The goal location
	 * @param nodeSearched A hook for visualization. See assignment instructions for how to use it.
	 * @return A list of GeographicPoint objects representing the shortest path from start to end (including both start and end points),
	 *         or an empty list if no path is found.
	 */
	public List<GeographicPoint> bidirectionalSearch(GeographicPoint start, GeographicPoint end, Consumer<GeographicPoint> nodeSearched) {
	    // Data structures for the forward and backward searches
	    HashSet<MapNode> visitedForward = new HashSet<>();
	    HashSet<MapNode> visitedBackward = new HashSet<>();
	    PriorityQueue<MapNode> queueForward = new PriorityQueue<>();
	    PriorityQueue<MapNode> queueBackward = new PriorityQueue<>();
	    HashMap<MapNode, MapNode> parentForward = new HashMap<>();
	    HashMap<MapNode, MapNode> parentBackward = new HashMap<>();
	    
	    // Initialize start and end nodes for forward and backward searches
	    MapNode startNode = intersections.get(start);
	    MapNode endNode = intersections.get(end);
	    
	    // Set initial distances for both directions
	    for (GeographicPoint location : intersections.keySet()) {
	        intersections.get(location).setDistance(Double.MAX_VALUE);
	    }
	    startNode.setDistance(0.0);
	    endNode.setDistance(0.0);
	    
	    // Add start and end nodes to their respective queues
	    queueForward.add(startNode);
	    queueBackward.add(endNode);
	    
	    // Initialize variables to track common intersection and minimum distance
	    MapNode intersectionNode = null;
	    double minDistance = Double.MAX_VALUE;
	    
	    // Loop until one of the queues becomes empty
	    while (!queueForward.isEmpty() && !queueBackward.isEmpty()) {
	        // Perform forward search
	        MapNode currentForward = queueForward.remove();
	        visitedForward.add(currentForward);
	        nodeSearched.accept(currentForward.getLocation());
	        
	        // Check if current node is visited by backward search
	        if (visitedBackward.contains(currentForward)) {
	            // Check if combined distance is minimum
	            if (currentForward.getDistance() + parentBackward.get(currentForward).getDistance() < minDistance) {
	                minDistance = currentForward.getDistance() + parentBackward.get(currentForward).getDistance();
	                intersectionNode = currentForward;
	            }
	        }
	        
	        // Expand neighbors of current node in forward search
	        for (MapEdge road : currentForward.getRoadList()) {
	            MapNode neighbor = intersections.get(road.getEndPoint());
	            if (!visitedForward.contains(neighbor)) {
	                double minDist = currentForward.getDistance() + road.getLength();
	                if (minDist < neighbor.getDistance()) {
	                    neighbor.setDistance(minDist);
	                    parentForward.put(neighbor, currentForward);
	                    queueForward.add(neighbor);
	                }
	            }
	        }
	        
	        // Perform backward search
	        MapNode currentBackward = queueBackward.remove();
	        visitedBackward.add(currentBackward);
	        nodeSearched.accept(currentBackward.getLocation());
	        
	        // Check if current node is visited by forward search
	        if (visitedForward.contains(currentBackward)) {
	            // Check if combined distance is minimum
	            if (currentBackward.getDistance() + parentForward.get(currentBackward).getDistance() < minDistance) {
	                minDistance = currentBackward.getDistance() + parentForward.get(currentBackward).getDistance();
	                intersectionNode = currentBackward;
	            }
	        }
	        
	        // Expand neighbors of current node in backward search
	        for (MapEdge road : currentBackward.getRoadList()) {
	            MapNode neighbor = intersections.get(road.getEndPoint());
	            if (!visitedBackward.contains(neighbor)) {
	                double minDist = currentBackward.getDistance() + road.getLength();
	                if (minDist < neighbor.getDistance()) {
	                    neighbor.setDistance(minDist);
	                    parentBackward.put(neighbor, currentBackward);
	                    queueBackward.add(neighbor);
	                }
	            }
	        }
	    }
	    
	    if (intersectionNode != null) {
	        List<GeographicPoint> path = new LinkedList<>();
	        MapNode temp = intersectionNode;
	        while (temp != null) {
	            path.add(temp.getLocation());
	            temp = parentForward.get(temp);
	        }
	        Collections.reverse(path);
	        temp = parentBackward.get(intersectionNode);
	        while (temp != null) {
	            path.add(temp.getLocation());
	            temp = parentBackward.get(temp);
	        }
	        return path;
	    } else {
	        System.out.println("No path found.");
	        return new LinkedList<>();
	    }
	}


	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		
		
		MapGraph theMap = new MapGraph();

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		
		
	}
	
}