import java.util.Arrays;
import java.util.Random;

public class Map {

    int[] map;
    int[] floor;
    int[] ceiling;
    int width;
    int height;

    Map(int[] map, int width, int height) {
        this.map = map;
        this.width = width;
        this.height = height;
        floor = new int[width*height];
        Arrays.fill(floor, 1);
        ceiling = new int[width*height];
        Arrays.fill(ceiling, 0);
    }

    Map(int[] map, int[] floor, int[] ceiling, int width, int height) {
        this.map = map;
        this.width = width;
        this.height = height;
        this.floor = floor;
        this.ceiling = ceiling;
    }

    public int getWall(int x, int y) {
        if (x >= width || x < 0 || y >= height || y < 0)
            return 1;
        else
            return map[x + y*width];
    }

    public int getFloor(int x, int y) {
        if (x >= width || x < 0 || y >= height || y < 0)
            return 1;
        else
            return floor[x + y*width];
    }

    public int getCeiling(int x, int y) {
        if (x >= width || x < 0 || y >= height || y < 0)
            return 1;
        else
            return ceiling[x + y*width];
    }
    
    public static Map generateMap(int width, int height) {
    	
    	int[] map = new int[width*height];
    	Random r = new Random();
    	
    	for (int i = 0; i < map.length; i++) {

            int w = r.nextInt(8);
    		if (w == 2)
    			map[i] = 2;
            else if (w == 1)
                map[i] = 1;
    		else
    			map[i] = 0;
    	}
    	
    	return new Map(map, width, height);
    }

}
