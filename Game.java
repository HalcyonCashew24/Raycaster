import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements KeyListener {
    Map level;
    double playerX = 32;
    double playerY = 32;
    double playerA = 90;

    Game() {
        level = new Map(new int[]{
                1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                1,1,1,0,0,0,0,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,0,0,0,0,0,0,1,
                1,0,0,0,1,0,0,0,0,1,0,0,0,1,
                1,0,0,0,1,0,0,0,0,0,0,0,0,1,
                1,0,0,0,0,1,0,0,0,0,0,0,0,1,
                1,1,1,1,1,1,1,1,1,1,1,1,1,1
        },14,7);
    }

    public void movePlayer() {
        if (w) {playerX += Math.cos(Math.toRadians(playerA))*5;
                playerY += Math.sin(Math.toRadians(playerA))*5;}
        if (s) {playerX -= Math.cos(Math.toRadians(playerA))*5;
                playerY -= Math.sin(Math.toRadians(playerA))*5;}
        if (a) {playerA -= 8;}
        if (d) {playerA += 8;}

        if (playerA >= 360) {playerA -= 360;}
        if (playerA < 0) {playerA += 360;}
    }

    boolean w = false;
    boolean a = false;
    boolean s = false;
    boolean d = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {w = true;}
        if (e.getKeyCode() == KeyEvent.VK_A) {a = true;}
        if (e.getKeyCode() == KeyEvent.VK_S) {s = true;}
        if (e.getKeyCode() == KeyEvent.VK_D) {d = true;}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {w = false;}
        if (e.getKeyCode() == KeyEvent.VK_A) {a = false;}
        if (e.getKeyCode() == KeyEvent.VK_S) {s = false;}
        if (e.getKeyCode() == KeyEvent.VK_D) {d = false;}
    }
}
