package application;
	
import java.io.FileReader;
import java.io.IOException;

import application.mapmaker.MapMaker;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;



public class Main extends Application {
	
    private String persistPath = "data/maps/mapfiles.list";
    
	@Override
	public void start(Stage primaryStage) {
		try {
			
	        float[] bound_arr = new float[] {42.3146f, -71.1061f, 42.3736f, -71.0330f};
	
	        MapMaker map = new MapMaker(bound_arr);
	        map.parseData("boston.map");
	        
			Button btOK = new Button("Hello To Blink Step");
			Scene scene = new Scene(btOK, 200, 250);
			primaryStage.setTitle("Blink Step");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
