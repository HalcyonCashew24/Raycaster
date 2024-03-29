import java.awt.*;

public class Raycaster {

    public static double shade = 1.0;
    public static double hitX;
    public static double hitY;
    public static Integer wallType = 1;

    public static final int numRays = 250;
    public static final int fov = 60;

    public static double[] depths = new double[numRays];

    public static double raycast(double rayA) {
        double pX = Main.game.playerX;
        double pY = Main.game.playerY;
        double rayX = 0;
        double rayY = 0;
        double nTan = -Math.tan(Math.toRadians(rayA));
        double nCot = 1/nTan;

        //normalize angle
        if (rayA >= 360) {rayA -= 360;}
        if (rayA < 0) {rayA += 360;}

        //***** left-right check *****
        if (rayA > 90 && rayA < 270) { //left
            rayX = (((int)pX>>5)<<5) - 0.0001;
            rayY = pY + nTan*(pX-rayX);

            while (Main.game.level.getWall((int)rayX>>5, (int)rayY>>5) == 0) {
                rayX -= 32;
                rayY = pY + nTan*(pX-rayX);
            }
        }
        if (rayA > 270 || rayA < 90) { //right
            rayX = (((int)pX>>5)+1)<<5;
            rayY = pY + nTan*(pX-rayX);

            while (Main.game.level.getWall((int)rayX>>5, (int)rayY>>5) == 0) {
                rayX += 32;
                rayY = pY + nTan*(pX-rayX);
            }
        }
        if (rayA == 270 || rayA == 90) { //up and down
            rayX = -64;
            rayY = -64;
        }

        double hLength = Math.sqrt((pX-rayX)*(pX-rayX) + (pY-rayY)*(pY-rayY));
        hitY = rayY;
        int hWallType = Main.game.level.getWall((int)rayX>>5, (int)rayY>>5);

        //***** up-down check *****
        if (rayA > 180) { //up
            rayY = (((int)pY>>5)<<5) - 0.0001;
            rayX = pX + nCot*(pY-rayY);

            while (Main.game.level.getWall((int)rayX>>5, (int)rayY>>5) == 0) {
                rayY -= 32;
                rayX = pX + nCot*(pY-rayY);
            }
        }
        if (rayA < 180) { //down
            rayY = (((int)pY>>5)+1)<<5;
            rayX = pX + nCot*(pY-rayY);

            while (Main.game.level.getWall((int)rayX>>5, (int)rayY>>5) == 0) {
                rayY += 32;
                rayX = pX + nCot*(pY-rayY) - 0.0001;
            }
        }
        if (rayA == 0 || rayA == 180) { //left and right
            rayY = -1000;
            rayX = -1000;
        }

        double vLength = Math.sqrt((pX-rayX)*(pX-rayX) + (pY-rayY)*(pY-rayY));
        hitX = rayX;
        int vWallType = Main.game.level.getWall((int) (rayX/32), (int) (rayY/32));

        if (hLength < vLength) {
            shade = 0.8;
            wallType = hWallType;
            return hLength;
        }
        else {
            shade = 0.7;
            wallType = vWallType;
            return vLength;
        }
    }

    public static void paint(Graphics b) {
        Game g = Main.game;
        Panel p = Main.panel;
        double screenX = 0;
        double screenDX = Main.panel.width/(double)numRays;

        for (double a = g.playerA - fov/2.0; a < g.playerA + fov/2.0; a += (double)fov/numRays) {

            double rLength = raycast(a);
            depths[(int)(screenX/screenDX)] = rLength;
            
            double wLength = 32*p.height/(rLength * Math.cos(Math.toRadians(g.playerA-a)));

            double wOffset = p.height/2.0 - wLength/2.0;

            int textureX;
            if (shade == 0.8)
                textureX = (int) Math.abs((hitY%32)/2);
            else
                textureX = (int) Math.abs((hitX%32)/2);

            for (int y = 0; y < 16; y++) {

                if (wallType == 0) {wallType = 1;}
                if (wallType < 0) {wallType = -1;}

                Color c;
                if (wallType < 0)
                    c = g.doors[wallType*-1].getPixel(textureX,y);
                else
                    c = g.walls[wallType].getPixel(textureX,y);

                b.setColor(new Color((int) (c.getRed()*shade), (int) (c.getGreen()*shade), (int) (c.getBlue()*shade)));
                b.fillRect((int) screenX, (int) (wOffset+wLength/16.0*y), (int) (screenDX+1), (int) (wLength/16 +1));
            }
            
            for(int y=(int) (wOffset+wLength)+1;y<p.height+1;y++) {
            	double dy=y-(p.height/2.0), deg= Math.toRadians(a), raFix=Math.cos(Math.toRadians(g.playerA-a));
            	int tx=(int) (g.playerX/2 + Math.cos(deg)*(p.height/2)*16/dy/raFix)*2;
            	int ty=(int) (g.playerY/2 + Math.sin(deg)*(p.height/2)*16/dy/raFix)*2;
            	int mp=g.level.getFloor(tx>>5, ty>>5);
            	if (mp == 0) {mp = 1;}
            	Color c=g.floors[mp].getPixel(tx>>1&15, ty>>1&15);
             
            	b.setColor(new Color((int)(c.getRed()*0.7),(int)(c.getGreen()*0.7),(int)(c.getBlue()*0.7)));
            	b.fillRect((int)((a-g.playerA+fov/2.0)*screenDX*((double)numRays/fov)), y-1, (int)screenDX+1,1);

            	mp=g.level.getCeiling(tx>>5, ty>>5);

            	if (mp != 0) {
            		c=g.floors[mp].getPixel(tx>>1&15, ty>>1&15);
             
            		b.setColor(new Color((int)(c.getRed()*0.7),(int)(c.getGreen()*0.7),(int)(c.getBlue()*0.7)));
                    b.fillRect((int)((a-g.playerA+fov/2.0)*screenDX*((double)numRays/fov)), p.height-y-1, (int)screenDX+1,8);
            	}
            }
            screenX += screenDX;
        }
    }
}
