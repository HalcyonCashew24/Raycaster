import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    int width;
    int height;
    Screen screen;
    Title title;

    Panel(int width, int height) {
        super();
        setPreferredSize(new Dimension(width, height));

        this.width = width;
        this.height = height;

        title = new Title(width, height);
        screen = new Screen(width, height);
    }

    public void paint(Graphics b) {

        if (!Main.pause) {
            Game g = Main.game;

            g.movePlayer();
            g.doorCheck();
            Raycaster.paint(b);
        }
        else if (Main.screen == 0) {
            title.paint();
            b.drawImage(title,0,0,null);
        }
        else if (Main.screen == 1) {
            screen.paint();
            b.drawImage(screen,0,0,null);
        }
    }
}
