import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class Panel extends JPanel {

    int width;
    int height;
    Screen screen;
    Title title;
    BufferedImage sky;
    double skyScale;

    Panel(int width, int height) {
        super();
        setPreferredSize(new Dimension(width, height));

        this.width = width;
        this.height = height;

        title = new Title(width, height);
        screen = new Screen(width, height);

        skyScale = height/128.0;
        Image img = new ImageIcon("./txt/sky.png").getImage();
        sky = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = sky.createGraphics();
        g.scale(skyScale,skyScale);
        g.drawImage(img,0,0,null);
        g.dispose();
    }
    
    public void resize(Dimension d) {
    	setPreferredSize(d);
    	width = d.width;
    	height = d.height;
    	
    	skyScale = height/128.0;
        Image img = new ImageIcon("./txt/sky.png").getImage();
        sky = new BufferedImage(width,width,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = sky.createGraphics();
        g.scale(skyScale,skyScale);
        g.drawImage(img,0,0,null);
        g.dispose();

        title = new Title(d.width,d.height);
        Main.frame.addMouseMotionListener(title);

        screen = new Screen(d.width,d.height);
    }

    public void paint(Graphics b) {

    	if (Main.pause)
    		return;
    	
        if (Main.screen == 0) {
            Game g = Main.game;
            
            g.movePlayer();
            g.doorCheck();
            
            for (int x = -(int)g.playerA*10-360; x < width; x += skyScale*128) {
                b.drawImage(sky,x,0,null);
            }
            Raycaster.paint(b);
        }
        else if (Main.screen == 1) {
            title.paint();
            b.drawImage(title,0,0,null);
        }
        else if (Main.screen == 2) {
            screen.paint();
            b.drawImage(screen,0,0,null);
        }
    }
}
