import java.awt.*;

public class Enemy {

    double x;
    double y;
    double a = -90;
    static Texture[] textures = new Texture[]{new Texture("textures/slime.png"),
    										 new Texture("textures/slime_side.png")};
    int t = 0;

    Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    boolean move() {
        Game g = Main.game;
        double sin = Math.sin(Math.toRadians(a));
        double cos = Math.cos(Math.toRadians(a));

        //[g.playerX-x, g.playerY-y]
        //[x+cos,y+sin]

        double angleToPlayer = Math.acos(((g.playerX-x)*(cos) + (g.playerY-y)*(sin))/Math.sqrt((g.playerX-x)*(g.playerX-x)+(g.playerY-y)*(g.playerY-y)));
        double playerAngle = Math.atan((g.playerY-y)/(g.playerX-x));
        if (g.playerX-x < 0) {playerAngle += Math.toRadians(180);}
        if (playerAngle < 0) {playerAngle += Math.toRadians(360);}
        if (playerAngle > Math.toRadians(360)) {playerAngle -= Math.toRadians(360);}

        if (Math.toDegrees(angleToPlayer) < 30) {
        	t = 0;
            if (g.level.getWall((int)(x + cos*10)>>5, (int)y>>5) <= 0) {x += cos*3;}
            if (g.level.getWall((int)x>>5, (int)(y + sin*10)>>5) <= 0) {y += sin*3;}
        }
        else {
        	t = 1;
            if (Math.toDegrees(playerAngle) < a && Math.abs(a-Math.toDegrees(playerAngle)) < 180) {
                a -= 5;
            }
            else {
                a += 5;
            }
        }
        
        double distanceToPlayer = Math.sqrt((g.playerX-x)*(g.playerX-x)+(g.playerY-y)*(g.playerY-y));
        
		return (distanceToPlayer < 10);
    }

    boolean draw(Graphics brush) {
        Game g = Main.game;
        Panel p = Main.panel;

        double sx = -x+g.playerX;
        double sy = y-g.playerY;
        double sz = 15;
        double sin = Math.sin(Math.toRadians(g.playerA));
        double cos = Math.cos(Math.toRadians(g.playerA));
        double a = sy*cos+sx*sin;
        double b = sx*cos-sy*sin;
        sx = a;
        sy = b;
        double distance = Math.sqrt((x-g.playerX)*(x-g.playerX)+(y-g.playerY)*(y-g.playerY));
        double angleToEnemy = Math.acos((cos*(x-g.playerX)+sin*(y-g.playerY))/distance);
        double zNear = (p.width/2.0)/Math.sin(Raycaster.fov/2.0);
        zNear = -647;

        sx = (sx*zNear/sy)+(p.width/2);
        sy = (sz*zNear/sy)+(p.height/2);

        double scale = 16*p.height/distance;

        if (sy > p.height/2) {
            for (double x = sx - scale / 2; x < sx + scale / 2; x += scale/16) {
                if (x > 0 && x < p.width) {
                    if (Raycaster.depths[(int) (x / (p.width / (double) Raycaster.numRays))] > distance) {
                        for (double y = sy-scale; y < sy; y += scale/16) {
                            if (y > 0 && y < p.height) {
                                Color c = textures[t].getPixel((int) ((x-(sx-scale/2))/(scale/16)), (int) ((y-(sy-scale))/(scale/16)));
                                if (!(c.getRed()>235 && c.getGreen()<20 && c.getBlue()>200)) {
                                    brush.setColor(c);
                                    brush.fillRect((int) x, (int) y, (int) (scale/16)+1, (int) (scale/16)+1);
                                }
                            }
                        }
                    }
                }
            }
        }

        return (sx > p.width/2 - 20 && sx < p.width/2 + 20 && g.shoot);
    }
}
