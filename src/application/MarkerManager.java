/** Class to manage Markers on the Map
 */

package application;

import java.util.Iterator;
import gmapsfx.javascript.event.UIEventType;
import gmapsfx.javascript.object.GoogleMap;
import gmapsfx.javascript.object.LatLong;
import gmapsfx.javascript.object.Marker;
import gmapsfx.javascript.object.MarkerOptions;
import hashMap.HashMap;
import javafx.scene.control.Button;
import list.ArrayList;
import gmapsfx.javascript.object.LatLongBounds;
import netscape.javascript.JSObject;

public class MarkerManager {

    private HashMap<geography.GeographicPoint, Marker> markerMap;
    private ArrayList<geography.GeographicPoint> markerPositions;
    private GoogleMap map;
    protected static String startURL = "https://img.icons8.com/color/28/000000/marker--v1.png";
    protected static String destinationURL = "https://img.icons8.com/fluency/30/order-delivered.png";
    protected static String SELECTED_URL = "https://img.icons8.com/external-sbts2018-mixed-sbts2018/28/external-03-gps-ecommerce-basic-1-sbts2018-mixed-sbts2018-2.png";
    protected static String markerURL = "http://maps.google.com/mapfiles/kml/paddle/blu-diamond-lv.png";
	protected static String visURL = "http://maps.google.com/mapfiles/kml/paddle/red-diamond-lv.png";
    private Marker startMarker;
    private Marker destinationMarker;
    private Marker selectedMarker;
    private DataSet dataSet;
    private LatLongBounds bounds;
    private SelectManager selectManager;
    private RouteVisualization rv;
    private Button vButton;
    private boolean selectMode = true;

    public MarkerManager() {
    	markerMap = new HashMap<geography.GeographicPoint, Marker>();
    	this.map = null;
    	this.selectManager = null;
        this.rv = null;
        markerPositions = null;
    }
    public MarkerManager(GoogleMap map, SelectManager selectManager) {
        dataSet = null;

    }

    
    public void setVisButton(Button vButton) {
    	this.vButton = vButton;
    }
    

    public void setSelect(boolean value) {
    	selectMode = value;
    }
    public RouteVisualization getVisualization() { return rv; }



    public GoogleMap getMap() { return this.map; }
    public void setMap(GoogleMap map) { this.map = map; }
    public void setSelectManager(SelectManager selectManager) { this.selectManager = selectManager; }

    public void putMarker(geography.GeographicPoint key, Marker value) {
    	markerMap.put(key, value);

    }

    
    public void initVisualization() {
    	rv = new RouteVisualization(this);
    }

    public void clearVisualization() {
        rv.clearMarkers();
    	rv = null;
    }

    public void startVisualization() {
    	if(rv != null) {
	    	rv.startVisualization();
    	}
    }

    public void setStart(geography.GeographicPoint point) {
    	if(startMarker!= null) {
            changeIcon(startMarker, markerURL);
    	}
        startMarker = markerMap.get(point);
        changeIcon(startMarker, startURL);
    }
    public void setDestination(geography.GeographicPoint point) {
    	if(destinationMarker != null) {
    		destinationMarker.setIcon(markerURL);
    	}
        destinationMarker = markerMap.get(point);
        changeIcon(destinationMarker, destinationURL);
    }

    public void changeIcon(Marker marker, String url) {
        marker.setVisible(false);
        marker.setIcon(url);
        marker.setVisible(true);
    }

    
    public void restoreMarkers() {
    	Iterator<geography.GeographicPoint> it = markerMap.keySet().iterator();
        while(it.hasNext()) {
            Marker marker = markerMap.get(it.next());
            if(marker != startMarker) {
                marker.setVisible(false);
                marker.setVisible(true);
            }
        }
        selectManager.resetSelect();
    }

    public void refreshMarkers() {

    	Iterator<geography.GeographicPoint> it = markerMap.keySet().iterator();
        while(it.hasNext()) {
        	Marker marker = markerMap.get(it.next());
        	marker.setVisible(true);
        }
    }
    public void clearMarkers() {
        if(rv != null) {
        	rv.clearMarkers();
        	rv = null;
        }
    	Iterator<geography.GeographicPoint> it = markerMap.keySet().iterator();
    	while(it.hasNext()) {
    		markerMap.get(it.next()).setVisible(false);
    	}
    }

    public void setSelectMode(boolean value) {
        if(!value) {
        	selectManager.clearSelected();
        }
    	selectMode = value;
    }

    public boolean getSelectMode() {
    	return selectMode;
    }
    public static MarkerOptions createDefaultOptions(LatLong coord) {
        	MarkerOptions markerOptions = new MarkerOptions();
        	markerOptions.animation(null)
        				 .icon(markerURL)
        				 .position(coord)
                         .title(null)
                         .visible(true);
        	return markerOptions;
    }

    public void hideIntermediateMarkers() {
        Iterator<geography.GeographicPoint> it = markerMap.keySet().iterator();
        while(it.hasNext()) {
            Marker marker = markerMap.get(it.next());
            if(marker != startMarker && marker != destinationMarker) {
                marker.setVisible(false);
            }
        }
    }

    public void hideDestinationMarker() {
    	destinationMarker.setVisible(false);
    }

    public void displayMarker(geography.GeographicPoint point) {
    	if(markerMap.containsKey(point)) {
        	Marker marker = markerMap.get(point);
            marker.setVisible(true);
    	}
    	else {
    	}
    }
    public void displayDataSet() {
        markerPositions = new ArrayList<geography.GeographicPoint>();
        dataSet.initializeGraph();
    	Iterator<geography.GeographicPoint>it = dataSet.getIntersections().iterator();
        bounds = new LatLongBounds();
        while(it.hasNext()) {
        	geography.GeographicPoint point = it.next();
            LatLong ll = new LatLong(point.getX(), point.getY());
        	MarkerOptions markerOptions = createDefaultOptions(ll);
            bounds.extend(ll);
        	Marker marker = new Marker(markerOptions);
            registerEvents(marker, point);
        	map.addMarker(marker);
        	putMarker(point, marker);
        	markerPositions.add(point);
        }
        map.fitBounds(bounds);

    }


    private void registerEvents(Marker marker, geography.GeographicPoint point) {

        map.addUIEventHandler(marker, UIEventType.click, (JSObject o) -> {
            if(selectMode) {
                	if(selectedMarker != null && selectedMarker != startMarker
                	   && selectedMarker != destinationMarker) {
                		selectedMarker.setIcon(markerURL);
                	}
            	selectManager.setPoint(point, marker);
                selectedMarker = marker;
                selectedMarker.setIcon(SELECTED_URL);
            }
        });
    }

    public void disableVisButton(boolean value) {
    	if(vButton != null) {
	    	vButton.setDisable(value);
    	}
    }
	public void setDataSet(DataSet dataSet) {
		this.dataSet= dataSet;
	}


    public DataSet getDataSet() { return this.dataSet; }
}
