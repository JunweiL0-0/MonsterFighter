package main.java.utilities.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MapLoader {
    private static final String MAP_FILE_PATH = "src/main/java/utilities/map/world.txt";
    private static final int MAX_MAP_COL = 50;
    private static final int MAX_MAP_ROW = 50;
    private int[][] encodedMap;


    public MapLoader() {
        this.encodedMap = new int[MAX_MAP_COL][MAX_MAP_ROW];
        loadEncodedMap();
    }

    private void loadEncodedMap() {
        try {
            // load the map
            File mapFile = new File(MAP_FILE_PATH);
            // create a bufferedReader
            BufferedReader br = new BufferedReader(new FileReader(mapFile));
            int col = 0;
            int row = 0;
            // while there is still some cols or rows left in the panel, we put the encoded integer into the 2D array
            while (col < MAX_MAP_COL && row < MAX_MAP_ROW) {
                // read one line(row) at a time
                String line = br.readLine();
                // read all col
                while (col < MAX_MAP_COL) {
                    // split by space
                    String[] numbers = line.split(" ");
                    // convert string into integer
                    int num = Integer.parseInt(numbers[col]);
                    // store it into the double array
                    this.encodedMap[col][row] = num;
                    // go to next col
                    col++;
                }
                // if col is equals to the maxPanelCol
                if (col == MAX_MAP_ROW) {
                    // reset the col to zero
                    col = 0;
                    // goes to next row
                    row++;
                }
            }
            // close the reader
            br.close();
        } catch (Exception e) {
            System.out.println("Error occurs while loading the Map.");
            e.printStackTrace();
        }
    }

    public int[][] getEncodedMap() {
        return this.encodedMap;
    }

}
