/** Class for route visualization for search
 */

package application;

import geography.GeographicPoint;

import gmapsfx.javascript.IJavascriptRuntime;
import gmapsfx.javascript.JavascriptArray;
import gmapsfx.javascript.JavascriptRuntime;
import gmapsfx.javascript.object.LatLong;
import gmapsfx.javascript.object.LatLongBounds;
import gmapsfx.javascript.object.Marker;
import gmapsfx.javascript.object.MarkerOptions;
import list.ArrayList;


public class RouteVisualization {
	ArrayList<GeographicPoint> points;
    ArrayList<Marker> markerList;
	MarkerManager manager;
	JavascriptArray markers;
    IJavascriptRuntime runtime;


	public RouteVisualization(MarkerManager manager) {
        points = new ArrayList<geography.GeographicPoint>();
        markerList = new ArrayList<Marker>();
		this.manager = manager;

	}

    public void acceptPoint(geography.GeographicPoint point) {
    	points.add(point);
    }



    public void startVisualization() {

    	LatLongBounds bounds = new LatLongBounds();
    	JavascriptArray jsArray = new JavascriptArray();
    	manager.hideIntermediateMarkers();
        manager.hideDestinationMarker();

        for (int i = 0; i < points.size(); i++) {
            GeographicPoint point = points.get(i);
            LatLong ll = new LatLong(point.getX(), point.getY());
            MarkerOptions options = MarkerManager.createDefaultOptions(ll);
            Marker newMarker = new Marker(options);
            jsArray.push(newMarker);
            markerList.add(newMarker);
            bounds.extend(ll);
        }


    	manager.getMap().fitBounds(bounds);

    	runtime = JavascriptRuntime.getInstance();
    	String command = runtime.getFunction("visualizeSearch", manager.getMap(), jsArray);

    	runtime.execute(command);

    	manager.disableVisButton(true);

    }

    public void clearMarkers() {
    	for (int i = 0; i < markerList.size(); i++) {
            Marker marker = markerList.get(i);
            marker.setVisible(false);
        }
    }
}
