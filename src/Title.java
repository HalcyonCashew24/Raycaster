import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Title extends Screen implements MouseMotionListener {

    int mouseY = 0;

    Title(int width, int height) {
        super(width, height);
    }

    public void paint() {
        Graphics b = createGraphics();
        int blue = (int) (mouseY/((double)height)*255);
        if (blue > 255) {blue = 255;}

        for (int x = 0; x <= 255; x++) {
            for (int y = 0; y <= 255; y++) {
                b.setColor(new Color(x,y,blue));
                b.fillRect((int) (width/255.0*x), (int) (height/255.0*y),width/255 +1,height/255 +1);
            }
        }

        b.setFont(new Font(Font.MONOSPACED,Font.PLAIN,100));
        b.setColor(Color.WHITE);
        b.drawString("Raycaster Game",width/2 -416,height/2);

        b.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,25));
        b.drawString("Click to Continue",width/2 -88,height/2 +40);

        //System.out.println(MouseInfo.getPointerInfo().getLocation().x);

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseY = e.getY();
    }
}
