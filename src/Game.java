import java.awt.*;
import java.awt.event.*;

public class Game implements KeyListener, MouseMotionListener {
    Map level;
    Map[] rooms = new Map[100];
    Texture[] walls = new Texture[8];
    Texture[] doors = new Texture[2];
    Texture[] floors = new Texture[5];
    double playerX = 48;
    double playerY = 48;
    double playerA = 90;

    Game() {
        rooms[0] = new Map(new int[]{
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
        
        rooms[1] = new Map(new int[]{
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
        
        rooms[99] = new Map(new int[]{
        		0,0,0,0,0,0,0,0,0,
        		0,1,2,3,4,5,6,7,0,
        		0,0,0,0,0,0,0,0,0,
        		0,0,0,0,0,0,0,0,0,
        		0,0,0,0,0,0,0,0,0,
        },9,5);

        walls[1] = new Texture("./txt/brick.png");
        walls[2] = new Texture("./txt/studded_plate.png");
        walls[3] = new Texture("./txt/brick_dark.png");
        walls[4] = new Texture("./txt/studded_plate_dark.png");
        walls[5] = new Texture("./txt/brick_light.png");
        walls[6] = new Texture("./txt/studded_plate_light.png");
        walls[7] = new Texture("./txt/tree.png");

        doors[1] = new Texture("./txt/door.png");

        floors[1] = walls[1];
        floors[2] = walls[2];
        floors[3] = walls[3];
        floors[4] = walls[4];

        level = rooms[0];
    }

    public void doorCheck() {
        int tile = level.getWall((int)playerX/32,(int)playerY/32);
        if (tile == -1) {
            level = rooms[1];
            playerY -= 32*5;
            playerX -= 32*6;
        }
        else if (tile == -2) {
            level = rooms[0];
            playerY += 32*5;
            playerX += 32*6;
        }
        else if (tile == -3) {
        	Main.frame.removeMouseMotionListener(this);
            Main.screen = 2;
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
        if (e.getKeyCode() == KeyEvent.VK_C) {level = rooms[99];}
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {System.exit(0);}

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
