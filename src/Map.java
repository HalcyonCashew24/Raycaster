import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Map {

    int[] map;
    int[] floor;
    int[] ceiling;
    int[] items;
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
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
        items = new int[width*height];
        Arrays.fill(items, 0);
    }

    Map(int[] map, int[] floor, int[] ceiling, int width, int height) {
        this.map = map;
        this.width = width;
        this.height = height;
        this.floor = floor;
        this.ceiling = ceiling;
        items = new int[width*height];
        Arrays.fill(items, 0);
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

    public int getItem(int x, int y) {
        if (x >= width || x < 0 || y >= height || y < 0)
            return 1;
        else
            return items[x + y*width];
    }

    public void addItem(int x, int y, int itemID) {
        items[y*width + x] = itemID;
    }

    public void addEnemy(Enemy e) {
        enemies.add(e);
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

    public static Map[] loadRooms() {
        Map[] rooms = new Map[4];

        rooms[1] = new Map(new int[]{
                4,6,4,3,3,4,4,4,4,
                4,0,4,0,0,4,0,0,4,
                4,0,4,0,0,3,0,0,5,
                5,0,0,0,0,4,0,0,3,
                3,4,0,3,0,0,0,0,3,
                3,0,0,5,0,4,4,4,3,
                3,0,4,3,0,0,0,-1,3,
                4,4,4,3,3,5,3,3,3,
        }, new int[]{
                3,3,3,3,3,3,3,3,3,
                3,4,3,3,3,3,4,4,3,
                3,4,3,3,3,3,3,3,3,
                3,3,3,3,3,3,3,3,3,
                3,3,3,3,3,4,3,3,3,
                3,3,3,3,3,3,3,3,3,
                3,3,3,3,3,4,4,3,3,
                3,3,3,3,3,3,3,3,3,
        }, new int[]{
                3,3,3,3,3,3,3,3,3,
                3,4,3,3,3,3,4,4,3,
                3,4,3,3,3,3,3,3,3,
                3,3,3,3,3,3,3,3,3,
                3,3,3,3,3,4,3,3,3,
                3,3,3,3,3,3,3,3,3,
                3,3,3,3,3,4,4,3,3,
                3,3,3,3,3,3,3,3,3,
        },9,8);

        rooms[2] = new Map(new int[]{
                3,3,3,3,1,1,1,1,
                -2,0,0,2,1,1,1,1,
                3,0,0,0,0,0,0,-3,
                3,0,0,0,0,0,0,1,
                3,3,3,4,1,0,1,1,
                1,1,1,1,1,7,1,1,
        },new int[]{
                3,3,3,3,1,1,1,1,
                3,3,3,4,1,1,1,1,
                3,3,3,2,1,1,1,1,
                3,3,3,4,1,1,1,1,
                3,3,3,4,1,1,1,1,
                1,1,1,1,1,1,1,1,
        },new int[]{
                3,3,3,4,0,0,0,0,
                3,3,3,2,0,0,0,0,
                3,3,3,0,0,0,0,0,
                3,3,3,2,0,0,0,0,
                3,3,3,4,0,0,0,0,
                0,0,0,0,0,0,0,0,
        },8,6);

        rooms[3] = new Map(new int[]{
                7,7,7,7,7,7,7,7,7,7,7,7,7,0,0,0,0,0,0,
                7,7,7,7,7,0,0,0,7,0,0,0,7,0,0,0,0,0,0,
                1,1,0,0,0,0,0,0,0,0,7,0,7,0,0,0,0,0,0,
                1,1,0,0,0,0,0,0,7,0,7,0,7,0,0,0,0,0,0,
                1,-4,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,
                1,1,0,0,0,0,0,0,7,0,0,0,7,7,7,7,7,7,7,
                1,1,0,0,0,0,0,7,7,7,0,0,0,0,0,0,0,0,7,
                7,0,0,0,7,0,0,0,0,7,7,1,0,0,1,1,1,0,7,
                7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,7,
                7,7,7,7,7,7,7,7,7,7,0,0,0,0,2,1,1,0,7,
                0,0,0,0,0,0,0,0,0,7,0,1,0,0,-5,1,0,0,7,
                0,0,0,0,0,0,0,0,0,7,0,1,0,0,2,2,0,0,7,
                0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,7,
                0,0,0,0,0,0,0,0,0,7,7,7,7,7,7,7,7,7,7,

        },19,14);

        rooms[3].addItem(1,7,1);
        rooms[3].addItem(1,8,2);

        rooms[3].ceiling[145] = 1;
        rooms[3].ceiling[146] = 1;
        Arrays.fill(rooms[3].floor, 5);

        rooms[3].addEnemy(new Enemy(100,100));
        rooms[3].addEnemy(new Enemy(200,200));
        rooms[3].addEnemy(new Enemy(12*32,10*32));

        rooms[0] = new Map(new int[]{
                0,0,0,0,0,0,0,0,0,
                0,1,2,3,4,5,6,7,0,
                0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,
        },9,5);

        return rooms;
    }
}
