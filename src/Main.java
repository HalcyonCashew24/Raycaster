import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

public class Main extends ComponentAdapter implements FocusListener {

    static Game game = new Game();
    static JFrame frame = new JFrame(":3");
    
    static Panel panel = new Panel(1280, 720);
    //static Panel panel = new Panel(1920, 1080);
    //static Panel panel = new Panel(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height);
    static boolean pause = true;
    static int screen = 1;
    static boolean fullScreen = false;

	public static void main(String[] args) {
    	Image i = Toolkit.getDefaultToolkit().getImage("placeholder.png");
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
        frame.addFocusListener(new Main());

        //GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
        
        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("./txt/cursor.png").getImage(), new Point(0,0), null);
        
        while (true) {
            try {
                Thread.sleep(50); //20fps
                panel.repaint();
                
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            
            if (screen == 0 && !pause) 
            	frame.setCursor(cursor);
            else 
            	frame.setCursor(null);
        }
    }

	public void componentResized(ComponentEvent e) {

        if (frame.getState() == 0)
            panel.resize(new Dimension(frame.getWidth(),frame.getHeight()));
        else
            panel.resize(new Dimension(frame.getWidth(),frame.getHeight()-22));
    }

	@Override
	public void focusGained(FocusEvent e) {
		pause = false;
	}

	@Override
	public void focusLost(FocusEvent e) {
		pause = true;
	}

}
