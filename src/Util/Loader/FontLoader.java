package Util.Loader;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author carlos
 */
public class FontLoader {
    
    public static TrueTypeFont arialFont;
    
    public static void init() {
        arialFont = getTrueTypeFont("arial", Font.BOLD, 10);
    }

    private static TrueTypeFont getTrueTypeFont(String name, int type, int size) {
        return new TrueTypeFont(new Font(name, type, size), false);
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
