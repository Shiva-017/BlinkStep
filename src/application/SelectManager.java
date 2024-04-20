/** Class to manage items selected in the GUI
 */

package application;
import geography.GeographicPoint;
import util.GraphLoader;
import gmapsfx.javascript.object.Marker;

public class SelectManager {
    private CLabel<GeographicPoint> pointLabel;
    private CLabel<GeographicPoint> startLabel;
    private CLabel<GeographicPoint> destinationLabel;
    private MarkerManager markerManager;


    public SelectManager() {
        pointLabel = null;
        startLabel = null;
        destinationLabel = null;
    }


    public void resetSelect() {
        markerManager.setSelectMode(true);
    }
    public void clearSelected() {
    	pointLabel.setItem(null);
    }

    public void setAndDisplayData(DataSet data) {
    	setDataSet(data);
        if(markerManager != null) {
            markerManager.displayDataSet();
        }
        else {
        	System.err.println("Error : Marker Manager is null.");
        }
    }

    public void setMarkerManager(MarkerManager manager) { this.markerManager = manager; }
    public void setPoint(GeographicPoint point, Marker marker) {
         String street = GraphLoader.findStreetName(point.x, point.y);
    	pointLabel.setItemWithName(point, street);
    }
    public void setDataSet(DataSet dataSet) {
    	if(markerManager != null) {
    		markerManager.setDataSet(dataSet);
    	}
    }

    public void setPointLabel(CLabel<GeographicPoint> label) { this.pointLabel = label; }
    public void setStartLabel(CLabel<GeographicPoint> label) { this.startLabel = label; }
    public void setDestinationLabel(CLabel<GeographicPoint> label) { this.destinationLabel = label; }

    public GeographicPoint getPoint() { return pointLabel.getItem(); }


	public GeographicPoint getStart(){return startLabel.getItem();}
	public GeographicPoint getDestination(){return destinationLabel.getItem();}
	public void setStart() {
		if(pointLabel.getItem() != null) {
        	GeographicPoint point = pointLabel.getItem();
    		startLabel.setItem(point);
            markerManager.setStart(point);
		}
	}

	public void setDestination() {
		if(pointLabel.getItem() != null) {
        	GeographicPoint point = pointLabel.getItem();
    		destinationLabel.setItem(point);
    		markerManager.setDestination(point);
		}
	}
}