package application.controllers;

import application.DataSet;
import application.services.GeneralService;
import application.services.RouteService;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class FetchController {
    private GeneralService generalService;
    private RouteService routeService;
    private Button displayButton;
    private ComboBox<DataSet> dataChoices;
    
    public FetchController(GeneralService generalService, RouteService routeService, TextField writeFile,
    					   ComboBox<DataSet> cb, Button displayButton) {
        this.generalService = generalService;
        this.routeService = routeService;
        this.displayButton = displayButton;
        dataChoices = cb;
        setupComboCells();
        setupDisplayButton();
        loadDataSets();

    }

    private void loadDataSets() {
    	String line = "boston_coordinates.map";

    	DataSet defaultDataSet = new DataSet(GeneralService.getDataSetDirectory() + line);
        dataChoices.getItems().add(defaultDataSet);
        
        dataChoices.setValue(defaultDataSet);
    }
    
    private void setupComboCells() {
    	dataChoices.setCellFactory(new Callback<ListView<DataSet>, ListCell<DataSet>>() {
        	@Override public ListCell<DataSet> call(ListView<DataSet> p) {
        		return new ListCell<DataSet>() {
        			{
                        super.setPrefWidth(100);
        			}

                    @Override
                    protected void updateItem(DataSet item, boolean empty) {
                        super.updateItem(item, empty);
                    	if(empty || item == null) {
                            super.setText("None.");
                    	}
                    	else {
                        	super.setText(item.getFilePath().substring(GeneralService.getDataSetDirectory().length()));

                    	}
                    }
        		};

        	}
    	});

        dataChoices.setButtonCell(new ListCell<DataSet>() {
        	@Override
        	protected void updateItem(DataSet t, boolean bln) {
        		super.updateItem(t,  bln);
        		if(t!=null) {
        			setText(t.getFilePath().substring(GeneralService.getDataSetDirectory().length()));
        		}
        		else {
        			setText("Choose...");
        		}
        	}
        });
    }

    

    /**
     * Registers event to fetch data
     */
    private void setupDisplayButton() {
    	displayButton.setOnAction( e -> {
            DataSet dataSet = dataChoices.getValue();

            if(dataSet == null) {
    		    Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Display Error");
    			alert.setHeaderText("Invalid Action :" );
    			alert.setContentText("No map file has been selected for display.");
    			alert.showAndWait();
            }
            else if(!dataSet.isDisplayed()) {
                if(routeService.isRouteDisplayed()) {
                	routeService.hideRoute();
                }
        		generalService.displayIntersections(dataSet);

            }
            else {
    		    Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("Display Info");
    			alert.setHeaderText("Intersections Already Displayed" );
    			alert.setContentText("Data set : " + dataSet.getFilePath() + " has already been loaded.");
    			alert.showAndWait();
            }
    	});
    }




}
