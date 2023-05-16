import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Screen extends BufferedImage implements MouseListener {

    int width;
    int height;

    Screen(int width, int height) {
        super(width, height, TYPE_INT_RGB);

        this.width = width;
        this.height = height;
    }

    public void paint() {
        Graphics b = createGraphics();

        b.setColor(Color.BLACK);
        b.fillRect(0,0,width,height);

        b.setFont(new Font(Font.MONOSPACED,Font.PLAIN,100));
        b.setColor(Color.WHITE);
        b.drawString("Game Over",width/2 -241,height/2);

        b.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,25));
        b.drawString("Esc to Quit",width/2 -41,height/2 +40);

        //System.out.println(MouseInfo.getPointerInfo().getLocation().x);
        b.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Main.screen = 0;
        Main.frame.removeMouseListener(this);
        Main.frame.addKeyListener(Main.game);
        Main.frame.addMouseMotionListener(Main.game);


        if (this.getClass() == Title.class) {
            Main.frame.removeMouseMotionListener((Title)this);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
