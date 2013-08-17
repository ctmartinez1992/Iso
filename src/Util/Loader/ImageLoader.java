package Util.Loader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author carlos
 */
public class ImageLoader {
    
    public static Image loadImage(String path) {
        Image image = null;
        try {
            image = new Image(path);
        } catch (SlickException ex) {
            ex.printStackTrace();
        }
        
        return image;
    }    
    
    public static BufferedImage loadBufferedImage(String path, int type) {
        BufferedImage image = null;
        try {
            BufferedImage tmp = ImageIO.read(ImageLoader.class.getResource(path));
            int width = tmp.getWidth();
            int height = tmp.getHeight();
            image = new BufferedImage(width, height, type);
            image = ImageIO.read(ImageLoader.class.getResource(path));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return image;
    }    
}
