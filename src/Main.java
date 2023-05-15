import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

public class Main extends ComponentAdapter {

    static Game game = new Game();
    static JFrame frame = new JFrame(":3");
    
    static Panel panel = new Panel(1280, 720);
    //static Panel panel = new Panel(1920, 1080);
    //static Panel panel = new Panel(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height);
    static boolean pause = true;
    static int screen = 0;
    static boolean fullScreen = false;

	public static void main(String[] args) {
    	Image i = Toolkit.getDefaultToolkit().getImage("IMG_3057.PNG");
    	frame.setIconImage(i);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.addMouseListener(panel.title);
        frame.addMouseMotionListener(panel.title);
        frame.addKeyListener(game);
        frame.addComponentListener(new Main());

        //fullScreen = true;
        //GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);

        while(true) {
            try {
                Thread.sleep(50); //20fps
                             
                panel.repaint();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
	
	public void componentResized(ComponentEvent e) {
		
		if (fullScreen) {
			panel.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		}
		else {
			panel.setSize(new Dimension(frame.getWidth()-16,frame.getHeight()-39));
			frame.pack();
        }
	}
}
