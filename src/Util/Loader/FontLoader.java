package Util.Loader;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author carlos
 */
public class FontLoader {
    
    public static Font squareFont;
    
    public static void init() {
        squareFont = getFont("/fonts/kenpixel_square.ttf");
    }

    private static Font getFont(String name) {
        Font font = null;
        try {
            InputStream inputStream = FontLoader.class.getResourceAsStream(name);
            font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (FontFormatException | IOException ex) {
            ex.printStackTrace();
        }
        
        return font;
    }
    
}
