package application.controllers;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import application.MarkerManager;
import application.SelectManager;
import application.CLabel;
import application.MapApp;
import application.services.RouteService;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import list.ListADT;

public class RouteController {
	public static final int BDS = 4;
	public static final int BFS = 3;
    public static final int A_STAR = 2;
    public static final int DIJ = 1;
	public static final int DISABLE = 0;
	public static final int START = 1;
	public static final int DESTINATION = 2;

    private int selectedToggle = DIJ;

    private RouteService routeService;
    private Button displayButton;
    private Button hideButton;
    private Button startButton;
    private Button resetButton;
    private Button destinationButton;
    private Button visualizationButton;
    private Button exportButton;

    private ToggleGroup group;
    private CLabel<geography.GeographicPoint> startLabel;
    private CLabel<geography.GeographicPoint> endLabel;
    private SelectManager selectManager;
    private MarkerManager markerManager;



	public RouteController(RouteService routeService, Button displayButton, Button hideButton,
						   Button resetButton, Button startButton, Button destinationButton,
						   ToggleGroup group, ListADT<RadioButton> searchOptions, Button visualizationButton,
						   CLabel<geography.GeographicPoint> startLabel, CLabel<geography.GeographicPoint> endLabel,
						   CLabel<geography.GeographicPoint> pointLabel, SelectManager manager, MarkerManager markerManager, Button exportButton) {
        this.routeService = routeService;
		this.displayButton = displayButton;
        this.hideButton = hideButton;
		this.startButton = startButton;
		this.resetButton = resetButton;
		this.destinationButton = destinationButton;
        this.group = group;
        this.visualizationButton = visualizationButton;
        this.exportButton = exportButton;

		this.startLabel = startLabel;
		this.endLabel = endLabel;
        this.selectManager = manager;
        this.markerManager = markerManager;

        setupDisplayButtons();
        setupRouteButtons();
        setupVisualizationButton();
        setupLabels();
        setupToggle();
	}


	private void setupDisplayButtons() {
		displayButton.setOnAction(e -> {
            if(startLabel.getItem() != null && endLabel.getItem() != null) {
        			routeService.displayRoute(startLabel.getItem(), endLabel.getItem(), selectedToggle);
        		    exportButton.setDisable(false);
        		    exportButton.setOnAction(ex -> {
        		    	exportMap();
        		    });
            }
            else {
            	MapApp.showErrorAlert("Route Display Error", "Make sure to choose points for both start and destination.");
            }
		});

        hideButton.setOnAction(e -> {
        	routeService.hideRoute();
        	exportButton.setDisable(true);
        });

        resetButton.setOnAction( e -> {

            routeService.reset();
        });
	}
	
	

	private void exportMap() {	    
		BorderPane root = (BorderPane)MapApp.getPrimaryStage().getScene().getRoot();
	    Node centerComponent = root.getCenter();
	    SnapshotParameters parameters = new SnapshotParameters();
	    parameters.setFill(Color.TRANSPARENT); 

	    WritableImage sceneImage = new WritableImage((int) centerComponent.getBoundsInLocal().getWidth(), (int) centerComponent.getBoundsInLocal().getHeight());

	    root.snapshot(parameters, sceneImage);

	    FileChooser fileChooser = new FileChooser();
	    fileChooser.setInitialFileName("map_export.png");
	    File file = fileChooser.showSaveDialog(MapApp.getPrimaryStage()); 

	    if (file != null) {
	        try {
	            ImageIO.write(SwingFXUtils.fromFXImage(sceneImage, null), "png", file);
	            System.out.println("Map exported successfully.");
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.err.println("Failed to export the map.");
	        }
	    }
	}




    private void setupVisualizationButton() {
    	visualizationButton.setOnAction( e -> {
    		markerManager.startVisualization();
    	});
    }

    private void setupRouteButtons() {
    	startButton.setOnAction(e -> {
            selectManager.setStart();
    	});

        destinationButton.setOnAction( e-> {
            selectManager.setDestination();
        });
    }


    private void setupLabels() {


    }

    private void setupToggle() {
    	group.selectedToggleProperty().addListener( li -> {
            if(group.getSelectedToggle().getUserData().equals("Dijkstra")) {
            	selectedToggle = DIJ;
            }
            else if(group.getSelectedToggle().getUserData().equals("A*")) {
            	selectedToggle = A_STAR;
            }
            else if(group.getSelectedToggle().getUserData().equals("BFS")) {
            	selectedToggle = BFS;
            	
            }else if(group.getSelectedToggle().getUserData().equals("BDS")) {
            	selectedToggle = BDS;
            }
            else {
            	System.err.println("Invalid radio button selection");
            }
    	});
    }
}
