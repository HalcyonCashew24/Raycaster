import java.util.Random;

public class Map {

    int[] map;
    int width;
    int height;

    Map(int[] map, int width, int height) {
        this.map = map;
        this.width = width;
        this.height = height;
    }

    public int getTile(int x, int y) {
        if (x >= width || x < 0 || y >= height || y < 0)
            return 1;
        else
            return map[x + y*width];
    }
    
    public static Map generateMap(int width, int height) {
    	
    	int[] map = new int[width*height];
    	Random r = new Random();
    	
    	for (int i = 0; i < map.length; i++) {
    		
    		if (r.nextInt(4) < 1) 
    			map[i] = 1;
    		else
    			map[i] = 0;
    	}
    	
    	return new Map(map, width, height);
    }

}
