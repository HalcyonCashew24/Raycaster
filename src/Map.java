public class Map {

    int[] map;
    int width;
    int height;
    int area;

    Map(int[] map, int width, int height) {
        this.map = map;
        this.width = width;
        this.height = height;
        area = width * height;
    }

    public int getTile(int x, int y) {
        if (x >= width || x < 0 || y >= height || y < 0)
            return 1;
        else
            return map[x + y*width];
    }

}
