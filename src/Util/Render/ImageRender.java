package Util.Render;

import Util.Math.Double2;
import Util.Math.Float2;
import Util.Math.Int2;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author carlos
 */
public class ImageRender {
    
    public static void renderIsometricBufferedImage(BufferedImage image, Graphics2D graphics, Float2 position) {
        AffineTransform transform = new AffineTransform();
            transform.translate(position.getX(), position.getY());
            transform.translate(image.getWidth() / 2, image.getWidth() / 2);
            transform.scale(1, 0.5);
            transform.rotate(Math.PI/4);
            transform.translate(-image.getWidth() / 2, -image.getWidth() / 2);
        graphics.drawImage(image, transform, null);
        //graphics.drawImage(image, position.getX(), position.getY(), image.getWidth(), image.getHeight(), null);
    }
    
    public static void renderBufferedImage(BufferedImage image, Graphics2D graphics, Int2 position) {
        graphics.drawImage(image, position.getX(), position.getY(), image.getWidth(), image.getHeight(), null);
    }
    
    public static void renderBufferedImage(BufferedImage image, Graphics2D graphics, Double2 position) {
        graphics.drawImage(image, (int) position.getX(), (int) position.getY(), image.getWidth(), image.getHeight(), null);
    }
    
    public static void renderBufferedImage(BufferedImage image, Graphics2D graphics, Float2 position) {
        graphics.drawImage(image, (int) position.getX(), (int) position.getY(), image.getWidth(), image.getHeight(), null);
    }
    
    public static void renderBufferedImage(BufferedImage image, Graphics2D graphics, Int2 position, Int2 dimension) {
        graphics.drawImage(image, position.getX(), position.getY(), dimension.getX(), dimension.getY(), null);
    }
    
    public static void renderBufferedImage(BufferedImage image, Graphics2D graphics, Double2 position, Int2 dimension) {
        graphics.drawImage(image, (int) position.getX(), (int) position.getY(), dimension.getX(), dimension.getY(), null);
    }
    
    public static void renderBufferedImage(BufferedImage image, Graphics2D graphics, Float2 position, Int2 dimension) {
        graphics.drawImage(image, (int) position.getX(), (int) position.getY(), dimension.getX(), dimension.getY(), null);
    }
    
    public static void renderText(String text, Graphics2D graphics, Int2 position) {
        graphics.drawString(text, position.getX(), position.getY());
    }
    
    public static void renderBufferedImageAndText(BufferedImage image, String text, Graphics2D graphics, Int2 position) {
        graphics.drawImage(image, position.getX(), position.getY(), image.getWidth(), image.getHeight(), null);
        graphics.drawString(text, position.getX() + image.getWidth() / 2, position.getY() + image.getHeight() / 2);
    }
    
    public static void renderTranslucentImage(BufferedImage image, Graphics2D graphics, Int2 position, float transperancy) {
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transperancy));
        graphics.drawImage(image, position.getX(), position.getY(), image.getWidth(), image.getHeight(), null);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
    
}
