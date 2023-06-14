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
    long frameTime;

    Panel(int width, int height) {
        super();
        setPreferredSize(new Dimension(width, height));

        this.width = width;
        this.height = height;

        title = new Title(width, height);
        screen = new Screen(width, height);

        skyScale = height/128.0;
        Image img = new ImageIcon("./textures/sky.png").getImage();
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
        Image img = new ImageIcon("./textures/sky.png").getImage();
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

        long time1 = System.currentTimeMillis();

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

            for (int i = 0; i < Main.game.level.enemies.size(); i++) {
                if (Main.game.level.enemies.get(i).move()) {
                	Main.screen = 2;
                	Main.frame.removeMouseMotionListener(g);
                }
                
                if (Main.game.level.enemies.get(i).draw(b)) {
                    Main.game.level.enemies.remove(i);
                    i--;
                }
            }
        }
        else if (Main.screen == 1) {
            title.paint();
            b.drawImage(title,0,0,null);
        }
        else if (Main.screen == 2) {
            screen.paint();
            b.drawImage(screen,0,0,null);
        }


        /*
        for (int x = 0; x < Main.game.level.width; x++) {
            for (int y = 0; y < Main.game.level.height; y++) {
                if (Main.game.level.getWall(x,y) != 0)
                    b.setColor(Color.WHITE);
                else
                    b.setColor(Color.BLACK);

                b.fillRect(x*32,y*32,31,31);
            }
        }

        b.setColor(Color.pink);
        b.fillOval((int) Main.game.playerX, (int) Main.game.playerY, 5,5);
        b.setColor(Color.RED);
        b.fillOval((int) Main.game.enemy.x, (int) Main.game.enemy.y, 5,5);

         */







        frameTime = System.currentTimeMillis() - time1;
    }
}
