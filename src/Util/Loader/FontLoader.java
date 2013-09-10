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
    
    public static TrueTypeFont arialFont10;
    public static TrueTypeFont arialFont16;
    
    public static void init() {
        arialFont10 = getTrueTypeFont("arial", Font.BOLD, 10);
        arialFont16 = getTrueTypeFont("arial", Font.BOLD, 16);
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
    
    public static int getStringWidth(TrueTypeFont font, String string) {
        return font.getWidth(string);
    }
    
}
