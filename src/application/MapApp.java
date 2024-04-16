/* Starting point of the application */
package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import LinkedListADT.LinkedListADT;

import java.util.List;

import application.controllers.FetchController;
import application.controllers.RouteController;
import application.mapmaker.MapMaker;
import application.services.GeneralService;
import application.services.RouteService;
import gmapsfx.MapComponentInitializedListener;
import gmapsfx.javascript.object.GoogleMap;
import gmapsfx.javascript.object.LatLong;
import gmapsfx.javascript.object.MapOptions;
import gmapsfx.javascript.object.MapTypeIdEnum;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;



public class MapApp extends Application 
implements MapComponentInitializedListener{
	
	/* Map View Setup */
	protected GoogleMapView mapComponent;
	protected static GoogleMap map;
	protected BorderPane bp;
	protected static Stage primaryStage;
	
	
	private static final double MARGIN_VAL = 10;
	private static final double FETCH_COMPONENT_WIDTH = 160.0;
	
	// getter to get map
	public static GoogleMap getMap() {
	        return map;
	    }
	
	// Getter method for primaryStage
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    
    /* START POINT */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		try {
			//boston boundaries
	        float[] bound_arr = new float[] {42.35f, -71.08f, 42.36f, -71.07f};
	        
	        // MAIN CONTAINER
	        bp = new BorderPane();
	        
	        // google map view setup
	        mapComponent = new GoogleMapView();
			mapComponent.addMapInitializedListener(this);
			
			Tab routeTab = new Tab("Routing");
			Button displayButton = new Button("Show Intersections");
			TextField tf = new TextField();
			ComboBox<DataSet> cb = new ComboBox<DataSet>();
			
			
			cb.setOnMousePressed( e -> {
				cb.requestFocus();
			});

			VBox fetchBox = getFetchBox(displayButton, cb);
			
			Button routeButton = new Button("Show Route");
			Button hideRouteButton = new Button("Hide Route");
			Button resetButton = new Button("Reset");
			Button visualizationButton = new Button("Start Visualization");
			Button exportButton = new Button("Export Map");
			Image sImage = new Image(MarkerManager.startURL);
			Image dImage = new Image(MarkerManager.destinationURL);
			CLabel<geography.GeographicPoint> startLabel = new CLabel<geography.GeographicPoint>("Empty.", new ImageView(sImage), null);
			CLabel<geography.GeographicPoint> endLabel = new CLabel<geography.GeographicPoint>("Empty.", new ImageView(dImage), null);
			
			startLabel.setMinWidth(180);
			endLabel.setMinWidth(180);
			Button startButton = new Button("Start");
			Button destinationButton = new Button("Dest");

			final ToggleGroup group = new ToggleGroup();
			
			@SuppressWarnings("unchecked")
			List<RadioButton> searchOptions = setupToggle(group);

		
			SelectManager manager = new SelectManager();
			MarkerManager markerManager = new MarkerManager();
			markerManager.setSelectManager(manager);
			manager.setMarkerManager(markerManager);
			markerManager.setVisButton(visualizationButton);
	
			CLabel<geography.GeographicPoint> pointLabel = new CLabel<geography.GeographicPoint>("No point Selected.", null);
			manager.setPointLabel(pointLabel);
			manager.setStartLabel(startLabel);
			manager.setDestinationLabel(endLabel);
			setupRouteTab(routeTab, fetchBox, startLabel, endLabel, pointLabel, routeButton, hideRouteButton,
					resetButton, visualizationButton, startButton, destinationButton, searchOptions, exportButton);
			
			
			TabPane tp = new TabPane(routeTab);
			tp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

			/* CURRENTLY - MAIN PIECE OF CODE */
	        MapMaker map1 = new MapMaker(bound_arr);
	        map1.parseData("boston_coordinates.map");
	        
	     // initialize Services and controllers after map is loaded
			mapComponent.addMapReadyListener(() -> {
				GeneralService gs = new GeneralService(mapComponent, manager, markerManager);
				RouteService rs = new RouteService(mapComponent, markerManager);

				new RouteController(rs, routeButton, hideRouteButton, resetButton, startButton, destinationButton, group, searchOptions, visualizationButton,
						startLabel, endLabel, pointLabel, manager, markerManager, exportButton);
				new FetchController(gs, rs, tf, cb, displayButton);
			});
	        
			bp.setRight(tp);
			mapComponent.setMaxHeight(480);
			
			
			bp.setCenter(mapComponent);
			//Button btOK = new Button("Hello To Blink Step");
			Scene scene = new Scene(bp);
			scene.getStylesheets().add("html/routing.css");
			primaryStage.setTitle("Blink Step");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void mapInitialized() {

		LatLong center = new LatLong(42.3601,-71.0589);
		//LatLong center = new LatLong(42.3411, -71.0886);


		// set map options
		MapOptions options = new MapOptions();
		options.center(center)
		.mapMarker(false)
		.mapType(MapTypeIdEnum.ROADMAP)
		.mapTypeControl(false)
		.overviewMapControl(false)
		.panControl(true)
		.rotateControl(false)
		.scaleControl(false)
		.streetViewControl(false)
		.zoom(14)
		.zoomControl(true);

		// create map;
		map = mapComponent.createMap(options);
		setupJSAlerts(mapComponent.getWebView());
	}
	
	private VBox getFetchBox(Button displayButton, ComboBox<DataSet> cb) {
		VBox v = new VBox();
		HBox h = new HBox();

		HBox intersectionControls = new HBox();
		cb.setPrefWidth(FETCH_COMPONENT_WIDTH);
		displayButton.setPrefWidth(FETCH_COMPONENT_WIDTH);
		intersectionControls.getChildren().add(displayButton);

		h.getChildren().add(v);
		v.getChildren().add(new Label("Boston mini map"));
		v.getChildren().add(intersectionControls);

		return v;
	}
	
	
	private void setupRouteTab(Tab routeTab, VBox fetchBox, Label startLabel, Label endLabel, Label pointLabel,
			Button showButton, Button hideButton, Button resetButton, Button vButton, Button startButton,
			Button destButton, List<RadioButton> searchOptions, Button exportButton) {

		HBox h = new HBox();
		VBox v = new VBox();
		h.getChildren().add(v);



		VBox selectLeft = new VBox();


		selectLeft.getChildren().add(startLabel);
		HBox startBox = new HBox();
		startBox.getChildren().add(startLabel);
		startBox.getChildren().add(startButton);
		startBox.setSpacing(20);

		HBox destinationBox = new HBox();
		destinationBox.getChildren().add(endLabel);
		destinationBox.getChildren().add(destButton);
		destinationBox.setSpacing(20);


		VBox markerBox = new VBox();
		Label markerLabel = new Label("Selected Marker : ");

		markerBox.getChildren().add(markerLabel);

		markerBox.getChildren().add(pointLabel);
		markerBox.getChildren().add(exportButton);
		exportButton.setDisable(true); 

		VBox.setMargin(markerLabel, new Insets(MARGIN_VAL,MARGIN_VAL,MARGIN_VAL,MARGIN_VAL));
		VBox.setMargin(pointLabel, new Insets(0,MARGIN_VAL,MARGIN_VAL,MARGIN_VAL));
		VBox.setMargin(fetchBox, new Insets(0,0,MARGIN_VAL*2,0));

		HBox showHideBox = new HBox();
		showHideBox.getChildren().add(showButton);
		showHideBox.getChildren().add(hideButton);
		showHideBox.setSpacing(2*MARGIN_VAL);

		v.getChildren().add(fetchBox);
		v.getChildren().add(new Label("Start Position : "));
		v.getChildren().add(startBox);
		v.getChildren().add(new Label("Goal : "));
		v.getChildren().add(destinationBox);
		v.getChildren().add(showHideBox);
		for (RadioButton rb : searchOptions) {
			v.getChildren().add(rb);
		}
		v.getChildren().add(vButton);
		VBox.setMargin(showHideBox, new Insets(MARGIN_VAL,MARGIN_VAL,MARGIN_VAL,MARGIN_VAL));
		VBox.setMargin(vButton, new Insets(MARGIN_VAL,MARGIN_VAL,MARGIN_VAL,MARGIN_VAL));
		VBox.setMargin(exportButton,new Insets(MARGIN_VAL,MARGIN_VAL,MARGIN_VAL,MARGIN_VAL));
		vButton.setDisable(true);
		v.getChildren().add(markerBox);

		routeTab.setContent(h);


	}
	
	private void setupJSAlerts(WebView webView) {
		webView.getEngine().setOnAlert( e -> {
			Stage popup = new Stage();
			popup.initOwner(primaryStage);
			popup.initStyle(StageStyle.UTILITY);
			popup.initModality(Modality.WINDOW_MODAL);

			StackPane content = new StackPane();
			content.getChildren().setAll(
					new Label(e.getData())
					);
			content.setPrefSize(200, 100);

			popup.setScene(new Scene(content));
			popup.showAndWait();
		});
	}
	
	@SuppressWarnings("unchecked")
	private <T> List<T> setupToggle(ToggleGroup group) {

		RadioButton rbD = new RadioButton("Dijkstra");
		rbD.setUserData("Dijkstra");
		rbD.setSelected(true);

		RadioButton rbA = new RadioButton("A*");
		rbA.setUserData("A*");

		RadioButton rbB = new RadioButton("BFS");
		rbB.setUserData("BFS");

		RadioButton rbBDS = new RadioButton("BDS");
		rbBDS.setUserData("BDS");
		
		rbB.setToggleGroup(group);
		rbD.setToggleGroup(group);
		rbA.setToggleGroup(group);
		rbBDS.setToggleGroup(group);
		
		@SuppressWarnings("rawtypes")
		LinkedListADT ll = new LinkedListADT();
		ll.add((T)rbB);
		ll.add((T)rbD);
		ll.add((T)rbA);
		ll.add((T)rbBDS);
		return ll.toStandardList();
	}
	
	public void showLoadStage(Stage loadStage, String text) {
		loadStage.initModality(Modality.APPLICATION_MODAL);
		loadStage.initOwner(primaryStage);
		VBox loadVBox = new VBox(20);
		loadVBox.setAlignment(Pos.CENTER);
		Text tNode = new Text(text);
		tNode.setFont(new Font(16));
		loadVBox.getChildren().add(new HBox());
		loadVBox.getChildren().add(tNode);
		loadVBox.getChildren().add(new HBox());
		Scene loadScene = new Scene(loadVBox, 300, 200);
		loadStage.setScene(loadScene);
		loadStage.show();
	}
	
	public static void showInfoAlert(String header, String content) {
		Alert alert = getInfoAlert(header, content);
		alert.showAndWait();
	}

	public static Alert getInfoAlert(String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert;
	}

	public static void showErrorAlert(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("File Name Error");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}