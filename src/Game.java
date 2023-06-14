import java.awt.*;
import java.awt.event.*;

public class Game implements KeyListener, MouseMotionListener {
    Map level;
    Map[] rooms;
    Texture[] walls = new Texture[8];
    Texture[] doors = new Texture[2];
    Texture[] floors = new Texture[6];
    Texture[] items = new Texture[3];
    double playerX = 1*32+16;
    double playerY = 1*32+16;
    double playerA = 90;
    boolean shoot = false;

    Game() {

        rooms = Map.loadRooms();

        walls[1] = new Texture("textures/brick.png");
        walls[2] = new Texture("textures/studded_plate.png");
        walls[3] = new Texture("textures/brick_dark.png");
        walls[4] = new Texture("textures/studded_plate_dark.png");
        walls[5] = new Texture("textures/brick_light.png");
        walls[6] = new Texture("textures/studded_plate_light.png");
        walls[7] = new Texture("textures/tree.png");

        doors[1] = new Texture("textures/door.png");

        floors[1] = walls[1];
        floors[2] = walls[2];
        floors[3] = walls[3];
        floors[4] = walls[4];
        floors[5] = new Texture("textures/grass.png");

        items[1] = new Texture("textures/IMG_3057.PNG");
        items[2] = walls[1];

        level = rooms[1];
    }

    public void doorCheck() {
        int tile = level.getWall((int)playerX/32,(int)playerY/32);
        if (tile == -1) {
            level = rooms[2];
            playerY -= 32*5;
            playerX -= 32*6;
        }
        else if (tile == -2) {
            level = rooms[1];
            playerY += 32*5;
            playerX += 32*6;
        }
        else if (tile == -3) {
        	level = rooms[3];
            playerX -= 32*5;
            playerY += 32*2;
        }
        else if (tile == -4) {
            level = rooms[2];
            playerX += 32*5;
            playerY -= 32*2;
        }
        else if (tile == -5) {
            Main.screen = 2;
            Main.frame.removeMouseMotionListener(this);
        }
    }

    public void movePlayer() {

        double cos = Math.cos(Math.toRadians(playerA));
        double sin = Math.sin(Math.toRadians(playerA));

        if (w) {if (level.getWall((int)(playerX + cos*10)>>5, (int)playerY>>5) <= 0) {playerX += cos*6;}
                if (level.getWall((int)playerX>>5, (int)(playerY + sin*10)>>5) <= 0) {playerY += sin*6;}}
        if (s) {if (level.getWall((int)(playerX - cos*10)>>5, (int)playerY>>5) <= 0) {playerX -= cos*6;}
                if (level.getWall((int)playerX>>5, (int)(playerY - sin*10)>>5) <= 0) {playerY -= sin*6;}}

        cos = Math.cos(Math.toRadians(playerA+90));
        sin = Math.sin(Math.toRadians(playerA+90));

        if (d) {if (level.getWall((int)(playerX + cos*10)>>5, (int)playerY>>5) <= 0) {playerX += cos*4;}
                if (level.getWall((int)playerX>>5, (int)(playerY + sin*10)>>5) <= 0) {playerY += sin*4;}}
        if (a) {if (level.getWall((int)(playerX - cos*10)>>5, (int)playerY>>5) <= 0) {playerX -= cos*4;}
                if (level.getWall((int)playerX>>5, (int)(playerY - sin*10)>>5) <= 0) {playerY -= sin*4;}}

        if (playerX < 10) {playerX = 10;}
        if (playerY < 10) {playerY = 10;}

        if (left) {playerA -= 7;}
        if (right) {playerA += 7;}

        if (playerA >= 360) {playerA -= 360;}
        if (playerA < 0) {playerA += 360;}
    }

    boolean w = false;
    boolean a = false;
    boolean s = false;
    boolean d = false;
    boolean left = false;
    boolean right = false;
    boolean shift = false;

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
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {shift = true;}
        if (e.getKeyCode() == KeyEvent.VK_N) {level = Map.generateMap(14,7);}
        if (e.getKeyCode() == KeyEvent.VK_C) {level = rooms[0];}
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {System.exit(0);}
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {shoot = true;}

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (e.getKeyCode() == KeyEvent.VK_Q && !shift) {gd.setFullScreenWindow(null);Main.frame.setExtendedState(Frame.MAXIMIZED_BOTH);}
        else if (e.getKeyCode() == KeyEvent.VK_Q && shift && gd.getFullScreenWindow() == null) {gd.setFullScreenWindow(Main.frame);}
        else if (e.getKeyCode() == KeyEvent.VK_Q && shift && gd.getFullScreenWindow() == Main.frame) {gd.setFullScreenWindow(null);}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {w = false;}
        if (e.getKeyCode() == KeyEvent.VK_A) {a = false;}
        if (e.getKeyCode() == KeyEvent.VK_S) {s = false;}
        if (e.getKeyCode() == KeyEvent.VK_D) {d = false;}
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {left = false;}
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {right = false;}
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {shift = false;}
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {shoot = false;}
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }
    
    Robot r = null; {
    	try {
    		r = new Robot();
    	} catch (AWTException e) {
    		System.err.println(e);
    	}
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    	
    	if (Main.pause)
    		return;
    	
    	Point l = Main.frame.getLocationOnScreen();
    	l.x += Main.panel.width/2;
    	l.y += Main.panel.height/2;
    	
    	playerA -= (l.x - e.getXOnScreen())/5.0;
    	
        r.mouseMove(l.x, l.y);

        if (playerA >= 360) {playerA -= 360;}
        if (playerA < 0) {playerA += 360;}
    }
}
