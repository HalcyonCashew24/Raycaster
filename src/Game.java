import java.awt.*;
import java.awt.event.*;

public class Game implements KeyListener, MouseMotionListener {
    Map level;
    Map[] rooms = new Map[2];
    Texture[] walls = new Texture[3];
    Texture[] interactions = new Texture[2];
    Texture[] floors = new Texture[2];
    double playerX = 16;
    double playerY = 16;
    double playerA = 90;

    Game() {
        rooms[0] = new Map(new int[]{
                0,2,0,0,1,0,0,
                0,2,0,0,1,0,0,
                0,0,0,0,2,0,0,
                2,0,1,0,0,0,0,
                0,0,1,0,2,2,2,
                0,2,1,0,0,0,-1,
        }, new int[]{
                0,1,1,1,1,1,1,
                0,1,1,1,1,1,1,
                0,1,1,1,1,1,1,
                0,1,1,1,1,1,1,
                1,1,1,1,1,1,1,
                1,1,1,1,1,1,1,
        }, new int[]{
        		1,1,0,0,1,0,1,
                1,1,1,1,1,1,1,
                1,0,0,0,1,0,0,
                1,0,1,0,1,0,0,
                1,0,1,0,1,1,1,
                1,1,1,1,1,0,1,
        },7,6);
        
        rooms[1] = new Map(new int[]{
                1,2,0,2,1,2,0,1,2,2,1,0,2,1,2,0,2,1,
                -2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-3,
                1,2,0,2,1,2,0,1,2,2,1,0,2,1,2,0,2,1,
        },18,3);

        walls[1] = new Texture("./src/brick.png");
        walls[2] = new Texture("./src/smooth_stone_slab_side.png");

        interactions[1] = new Texture("./src/IMG_3057.PNG");

        floors[0] = new Texture("./src/brick.png");
        floors[1] = new Texture("./src/smooth_stone_slab_side.png");
        
        level = rooms[0];
    }

    public void doorCheck() {
        int tile = level.getWall((int)playerX/32,(int)playerY/32);
        if (tile == -1) {
            level = rooms[1];
            playerY -= 32*4;
            playerX -= 32*5;
        }
        else if (tile == -2) {
            level = rooms[0];
            playerY += 32*4;
            playerX += 32*5;
        }
        else if (tile == -3) {
            Main.screen = 1;
            Main.pause = true;
        }
    }

    public void movePlayer() {

        if (w) {if (level.getWall((int) (playerX + Math.cos(Math.toRadians(playerA))*10)/32, (int) playerY/32) <= 0) {playerX += Math.cos(Math.toRadians(playerA))*5;}
                if (level.getWall((int) playerX/32, (int) (playerY + Math.sin(Math.toRadians(playerA))*10)/32) <= 0) {playerY += Math.sin(Math.toRadians(playerA))*5;}}
        if (s) {if (level.getWall((int) (playerX - Math.cos(Math.toRadians(playerA))*10)/32, (int) playerY/32) <= 0) {playerX -= Math.cos(Math.toRadians(playerA))*5;}
                if (level.getWall((int) playerX/32, (int) (playerY - Math.sin(Math.toRadians(playerA))*10)/32) <= 0) {playerY -= Math.sin(Math.toRadians(playerA))*5;}}
        if (a) {if (level.getWall((int) (playerX + Math.cos(Math.toRadians(playerA-90))*10)/32, (int) playerY/32) <= 0) {playerX += Math.cos(Math.toRadians(playerA-90))*4;}
                if (level.getWall((int) playerX/32, (int) (playerY + Math.sin(Math.toRadians(playerA-90))*10)/32) <= 0) {playerY += Math.sin(Math.toRadians(playerA-90))*4;}}
        if (d) {if (level.getWall((int) (playerX + Math.cos(Math.toRadians(playerA+90))*10)/32, (int) playerY/32) <= 0) {playerX += Math.cos(Math.toRadians(playerA+90))*4;}
                if (level.getWall((int) playerX/32, (int) (playerY + Math.sin(Math.toRadians(playerA+90))*10)/32) <= 0) {playerY += Math.sin(Math.toRadians(playerA+90))*4;}}

        if (playerX < 10) {playerX = 10;}
        if (playerY < 10) {playerY = 10;}

        if (left) {playerA -= 5;}
        if (right) {playerA += 5;}

        if (playerA >= 360) {playerA -= 360;}
        if (playerA < 0) {playerA += 360;}
    }

    boolean w = false;
    boolean a = false;
    boolean s = false;
    boolean d = false;
    boolean left = false;
    boolean right = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {w = true;}
        if (e.getKeyCode() == KeyEvent.VK_A) {a = true;}
        if (e.getKeyCode() == KeyEvent.VK_S) {s = true;}
        if (e.getKeyCode() == KeyEvent.VK_D) {d = true;}
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {left = true;}
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {right = true;}
        if (e.getKeyCode() == KeyEvent.VK_N) {level = Map.generateMap(14,7);}
        if (e.getKeyCode() == KeyEvent.VK_C) {level = new Map(new int[98],14,7);}
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {System.exit(0);}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {w = false;}
        if (e.getKeyCode() == KeyEvent.VK_A) {a = false;}
        if (e.getKeyCode() == KeyEvent.VK_S) {s = false;}
        if (e.getKeyCode() == KeyEvent.VK_D) {d = false;}
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {left = false;}
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {right = false;}
    }

    Point mousePos = MouseInfo.getPointerInfo().getLocation();
    Point mousePrevPos = mousePos;
    double mouseDX;

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        mousePrevPos = new Point(mousePos.x,mousePos.y);
        mousePos = e.getLocationOnScreen();

        mouseDX = mousePrevPos.x - mousePos.x;

        playerA -= mouseDX/5;

        if (playerA >= 360) {playerA -= 360;}
        if (playerA < 0) {playerA += 360;}
    }
}
