import java.awt.*;

public class Raycaster {

    public static double shade = 1.0;
    public static double raycast(double rayA) {
        double pX = Main.game.playerX;
        double pY = Main.game.playerY;
        double rayX = 0;
        double rayY = 0;

        //normalize angle
        if (rayA >= 360) {rayA -= 360;}
        if (rayA < 0) {rayA += 360;}

        //***** left-right check *****
        if (rayA > 90 && rayA < 270) { //left
            rayX = Math.floor(pX/32)*32 - 0.0001;
            rayY = pY + -Math.tan(Math.toRadians(rayA))*(pX-rayX);

            while (Main.game.level.getTile((int) Math.floor(rayX/32), (int) Math.floor(rayY/32)) != 1) {
                rayX -= 32;
                rayY = pY + -Math.tan(Math.toRadians(rayA))*(pX-rayX);
            }
        }
        if (rayA > 270 || rayA < 90) { //right
            rayX = Math.floor(pX/32)*32 + 32;
            rayY = pY + -Math.tan(Math.toRadians(rayA))*(pX-rayX);

            while (Main.game.level.getTile((int) Math.floor(rayX/32), (int) Math.floor(rayY/32)) != 1) {
                rayX += 32;
                rayY = pY + -Math.tan(Math.toRadians(rayA))*(pX-rayX);
            }
        }
        if (rayA == 270 || rayA == 90) { //up and down
            rayX = -1000;
            rayY = -1000;
        }

        double hLength = Math.sqrt((pX-rayX)*(pX-rayX) + (pY-rayY)*(pY-rayY));

        //***** up-down check *****
        if (rayA > 180) { //up
            rayY = Math.floor(pY/32)*32 - 0.0001;
            rayX = pX + 1/-Math.tan(Math.toRadians(rayA))*(pY-rayY);

            while (Main.game.level.getTile((int) Math.floor(rayX/32), (int) Math.floor(rayY/32)) != 1) {
                rayY -= 32;
                rayX = pX + 1/-Math.tan(Math.toRadians(rayA))*(pY-rayY);
            }
        }
        if (rayA < 180) { //down
            rayY = Math.floor(pY/32)*32 + 32;
            rayX = pX + 1/-Math.tan(Math.toRadians(rayA))*(pY-rayY);

            while (Main.game.level.getTile((int) Math.floor(rayX/32), (int) Math.floor(rayY/32)) != 1) {
                rayY += 32;
                rayX = pX + 1/-Math.tan(Math.toRadians(rayA))*(pY-rayY) - 0.0001;
            }
        }
        if (rayA == 0 || rayA == 180) { //left and right
            rayY = -1000;
            rayX = -1000;
        }

        double vLength = Math.sqrt((pX-rayX)*(pX-rayX) + (pY-rayY)*(pY-rayY));

        if (hLength < vLength) {
            shade = 1.0;
            return hLength;
        }
        else {
            shade = 0.7;
            return vLength;
        }
    }

    public static void paint(Graphics b) {
        Game g = Main.game;
        Panel p = Main.panel;
        double screenX = 0;
        double screenDX = Main.panel.width/60.0;

        for (double a = g.playerA - 30; a < g.playerA + 30; a++) {

            double rLength = raycast(a);


            b.setColor(Color.BLUE);
            b.drawLine((int) g.playerX,
                    (int) g.playerY,
                    (int) (Math.cos(Math.toRadians(a)) * rLength + g.playerX),
                    (int) (Math.sin(Math.toRadians(a)) * rLength + g.playerY));


            double wLength = 32*p.height/(rLength * Math.cos(Math.toRadians(g.playerA-a)));
            if (wLength > p.height)
                wLength = p.height;

            double wOffset = p.height/2.0 - wLength/2.0;

            b.setColor(new Color((int) (255*shade), 0,0));
            b.fillRect((int) screenX, (int) wOffset, (int) screenDX+1, (int) wLength);

            screenX += screenDX;
        }
    }
}
