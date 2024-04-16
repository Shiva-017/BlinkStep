/** Class for route visualization for search
 */

package application;

import ArrayListADT.ArrayListADT;
import geography.GeographicPoint;

import gmapsfx.javascript.IJavascriptRuntime;
import gmapsfx.javascript.JavascriptArray;
import gmapsfx.javascript.JavascriptRuntime;
import gmapsfx.javascript.object.LatLong;
import gmapsfx.javascript.object.LatLongBounds;
import gmapsfx.javascript.object.Marker;
import gmapsfx.javascript.object.MarkerOptions;


public class RouteVisualization {
	ArrayListADT<GeographicPoint> points;
    ArrayListADT<Marker> markerList;
	MarkerManager manager;
	JavascriptArray markers;
    IJavascriptRuntime runtime;


	public RouteVisualization(MarkerManager manager) {
        points = new ArrayListADT<geography.GeographicPoint>();
        markerList = new ArrayListADT<Marker>();
		this.manager = manager;

	}

    public void acceptPoint(geography.GeographicPoint point) {
    	points.add(point);
    }



    public void startVisualization() {

    	LatLongBounds bounds = new LatLongBounds();
//    	List<LatLong> latLongs = new ArrayListADT<LatLong>();
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
