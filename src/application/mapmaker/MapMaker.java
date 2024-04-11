package application.mapmaker;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;


public class MapMaker {
	
    float[] bounds;
    HashMap<Integer, Location> nodes = new HashMap<Integer, Location>();

    public MapMaker(float[] bounds) {
        this.bounds = bounds;
    }

    public boolean parseData(String filename) {
        DataFetcher fetcher = new DataFetcher(bounds);
        JsonObject data = fetcher.getData();

        JsonArray elements = data.getJsonArray("elements");

        for (JsonObject elem : elements.getValuesAs(JsonObject.class)) {
            if (elem.getString("type").equals("node")) {
                nodes.put(elem.getInt("id"), new Location(elem.getJsonNumber("lat").doubleValue(), elem.getJsonNumber("lon").doubleValue()));
            }
        }
        
//        try {
//        	FileWriter fileWriter = new FileWriter("/Users/srujanaadapa/Documents/SEM2/PSA/Project/BlinkStep/b.txt");
//        	PrintWriter outfile = new PrintWriter(fileWriter);
//        	System.out.print("Written");
//        	System.out.println("Absolute path: " + new File("/Users/srujanaadapa/Documents/SEM2/PSA/Project/BlinkStep/b.txt").getAbsolutePath());
//        	outfile.println("Simple Test");
//            outfile.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        PrintWriter outfile = null;
        try {
            FileWriter fileWriter = new FileWriter("/Users/srujanaadapa/Documents/SEM2/PSA/Project/BlinkStep/./b.txt");
            outfile = new PrintWriter(fileWriter);
//            outfile.print("Started");
            System.out.print("Written");
            System.out.println(System.getProperty("user.dir"));
            System.out.println("Writing to file at: " + new java.io.File("./b.txt").getAbsolutePath());
            
            for (JsonObject elem : elements.getValuesAs(JsonObject.class)) {
                if (elem.getString("type").equals("way")) {
                    String street = elem.getJsonObject("tags").getString("name", "");
                    String type = elem.getJsonObject("tags").getString("highway", "");
                    String oneway = elem.getJsonObject("tags").getString("oneway", "no");
                    List<JsonNumber> nodelist = elem.getJsonArray("nodes").getValuesAs(JsonNumber.class);
                    for (int i = 0; i < nodelist.size() - 1; i++) {
                        Location start = nodes.get(nodelist.get(i).intValue());
                        Location end = nodes.get(nodelist.get(i + 1).intValue());
                        if(start!=null && end !=null) {
                            if (start.outsideBounds(bounds) || end.outsideBounds(bounds)) {
                                continue;
                            }

                            outfile.println("" + start + end + "\"" + street + "\" " + type);
                            System.out.println("" + start + end + "\"" + street + "\" " + type);
                            if (oneway.equals("no")) {
                                outfile.println("" + end + start + "\"" + street + "\" " + type);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (outfile != null) {
                outfile.close(); // Always close the stream
            }
        }


//        outfile.close();
        return true;
    }

//    public static void main(String[] args) {
//        if (args.length != 4) {
//            System.out.println("Incorrect number of arguments.");
//            System.out.println(args.length);
//            return;
//        }
//
//        float[] bound_arr = new float[4];
//        try {
//            for (int i = 0; i < args.length; i++) {
//                bound_arr[i] = Float.parseFloat(args[i]);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return;
//        }
//
//        MapMaker map = new MapMaker(bound_arr);
//        map.parseData("ucsd.map");
//    }
}

class Location {
    private double lat;
    private double lon;

    public Location(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String toString() {
        return "" + lat + " " + lon + " ";
    }

    /**
     * @param bounds [south, west, north, east]
     */
    public boolean outsideBounds(float[] bounds) {
        return (lat < bounds[0] || lat > bounds[2] || lon < bounds[1] || lon > bounds[3]);
    }
}
