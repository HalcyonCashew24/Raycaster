import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.*;

public class Main {

    static Game game = new Game();
    static JFrame frame = new JFrame(":3");
    static Panel panel = new Panel(1280, 720);

    public static void main(String[] args) throws InterruptedException {
    	Image i = Toolkit.getDefaultToolkit().getImage("IMG_3057.PNG");
    	frame.setIconImage(i);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.addKeyListener(game);

        while(true) {
            try {
                Thread.sleep(50); //20fps
                panel.repaint();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
