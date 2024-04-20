package application.services;

import java.util.regex.Pattern;

import application.DataSet;
import application.GoogleMapView;
import application.MapApp;
import application.MarkerManager;
import application.SelectManager;
import gmapsfx.javascript.object.GoogleMap;
import gmapsfx.javascript.object.LatLong;
import gmapsfx.javascript.object.LatLongBounds;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import list.ArrayList;
import application.mapmaker.MapMaker;


public class GeneralService {
	private int currentState;
	private SelectManager selectManager;
	private GoogleMap map;
    private MarkerManager markerManager;



    private static final String DATA_FILE_PATTERN = "[\\w_]+.map";
    private static final String DATA_FILE_DIR_STR = "data/maps/";

    private ArrayList<String> filenames;
    DataSet dataSet;

    public GeneralService(GoogleMapView mapComponent, SelectManager selectManager, MarkerManager markerManager) {
    	this.map = mapComponent.getMap();
    	this.selectManager = selectManager;
        this.markerManager = markerManager;
        this.markerManager.setMap(map);
    	filenames = new ArrayList<String>();

    }


	// writes geographic data flat file
    // parameters array contains the coordinates of the bounds for the map region
    public boolean writeDataToFile(String filename, float[] arr) {
     	MapMaker mm = new MapMaker(arr);

    	// parse data and write to filename
    	if(mm.parseData(filename)) {
            return true;
    	}

        return false;
    }

    public static String getDataSetDirectory() { return GeneralService.DATA_FILE_DIR_STR; }

    // gets current bounds of map view
    public float[] getBoundsArray() {
        LatLong sw, ne;
    	LatLongBounds bounds = map.getBounds();

    	sw = bounds.getSouthWest();
    	ne = bounds.getNorthEast();

    	return new float[] {(float) sw.getLatitude(), (float) sw.getLongitude(),
    			            (float) ne.getLatitude(), (float) ne.getLongitude()};
    }

    public void addDataFile(String filename) {
    	filenames.add(filename);
    }

    public void displayIntersections(DataSet dataset) {
    	if(markerManager == null){
    	  System.out.println("failure!");
    	}
        if(markerManager.getDataSet() != null) {
        	markerManager.clearMarkers();
            markerManager.getDataSet().setDisplayed(false);
        }

    	selectManager.setAndDisplayData(dataset);
        dataset.setDisplayed(true);

    }
    
    public float boundsSize() {
    	float[] bounds = getBoundsArray();
    	return (bounds[2] - bounds[0]) * (bounds[3] - bounds[1]);
    }
    
    public boolean checkBoundsSize(double limit) {
    	if (boundsSize() > limit) {
    		return false;
    	}
    	return true;
    }

    /**
     * Check if file name matches pattern [filename].map
     *
     * @param str - path to check
     * @return string to use as path
     */
    public String checkDataFileName(String str) {
    	if(Pattern.matches(DATA_FILE_PATTERN, str)) {
            return DATA_FILE_DIR_STR + str;
    	}
    	return null;
    }

    public void runFetchTask(String fName, ComboBox<DataSet> cb, Button button) {
        float[] arr = getBoundsArray();

    	Task<String> task = new Task<String>() {
            @Override
        	public String call() {
        		if(writeDataToFile(fName, arr)) {
                    return fName;
        		}

        		return "z" + fName;

            }
        };



        Alert fetchingAlert = MapApp.getInfoAlert("Loading : ", "Fetching data for current map area...");
        task.setOnSucceeded( e -> {
          if(task.getValue().equals(fName)) {
               addDataFile(fName);

               cb.getItems().add(new DataSet(fName));
               if(fetchingAlert.isShowing()) {
            	   fetchingAlert.close();
               }
               MapApp.showInfoAlert("Fetch completed : ", "Data set : \"" + fName + "\" written to file!");

           }
           else {

           }

           button.setDisable(false);

        });


        task.setOnFailed( e -> {

        });

        task.setOnRunning(e -> {
            button.setDisable(true);
            fetchingAlert.showAndWait();
        });


        Thread fetchThread = new Thread(task);
        fetchThread.start();
    }



    public ArrayList<String> getDataFiles() {
    	return filenames;
    }

    public static String getFileRegex() {
    	return GeneralService.DATA_FILE_PATTERN;
    }


    public void setState(int state) {
    	currentState = state;
    }


    public double getState() { return currentState; }


}

