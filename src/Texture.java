import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Texture {

    BufferedImage image;

    Texture(String f) {
        try {
            image = ImageIO.read(new File(f));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Color getPixel(int x, int y) {
        return new Color(image.getRGB(x,y));
    }

}
