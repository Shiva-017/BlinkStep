package application;
	
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import application.mapmaker.MapMaker;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;



public class Main extends Application {
	
    private String persistPath = "data/maps/mapfiles.list";
   // private ComboBox<DataSet> dataChoices;
    
	@Override
	public void start(Stage primaryStage) {
		try {
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root,400,400);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();
			
	        float[] bound_arr = new float[] {42.3146f, -71.1061f, 42.3736f, -71.0330f};
//	        try {
//	            for (int i = 0; i < args.length; i++) {
//	                bound_arr[i] = Float.parseFloat(args[i]);
//	            }
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return;
//	        }
	
	        MapMaker map = new MapMaker(bound_arr);
	        map.parseData("boston.map");
	        
			Button btOK = new Button("Hello To Blink Step");
			Scene scene = new Scene(btOK, 200, 250);
			primaryStage.setTitle("Blink Step");
			primaryStage.setScene(scene);
			primaryStage.show();
			
//			loadDataSets();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
//    private void loadDataSets() {
//    	try {
//			BufferedReader reader = new BufferedReader(new FileReader(persistPath));
//            String line = reader.readLine();
//            while(line != null) {
//            	//dataChoices.getItems().add(new DataSet("data/maps/" + line));
//                line = reader.readLine();
//            }
//
//            reader.close();
//		} catch (IOException e) {
//            // System.out.println("No existing map files found.");
//			e.printStackTrace();
//		}
//    }
//	
	public static void main(String[] args) {
		launch(args);
	}
}
