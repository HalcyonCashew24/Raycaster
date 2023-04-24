import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    int width;
    int height;

    int counter = 0;
    Panel(int width, int height) {
        super();
        setPreferredSize(new Dimension(width, height));

        this.width = width;
        this.height = height;
    }

    public void paint(Graphics b) {
        Game g = Main.game;

        g.movePlayer();

        b.setColor(Color.GRAY);
        b.fillRect(0,0,g.level.width*32,g.level.height*32);

        for (int x = 0; x < g.level.width; x++) {
            for (int y = 0; y < g.level.height; y++) {
                if (g.level.getTile(x,y) == 1)
                    b.setColor(Color.WHITE);
                else
                    b.setColor(Color.BLACK);

                b.fillRect(x*32,y*32,31,31);
            }
        }

        b.setColor(Color.YELLOW);
        b.fillOval((int) g.playerX-4,(int)g.playerY-4,8,8);
        b.drawLine((int) g.playerX, (int) g.playerY, (int) (Math.cos(Math.toRadians(g.playerA))*16 + g.playerX), (int) (Math.sin(Math.toRadians(g.playerA))*16 + g.playerY));

        Raycaster.paint(b);
    }
}
