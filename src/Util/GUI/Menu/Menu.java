package Util.GUI.Menu;

import Util.AABB;
import Util.GUI.Button.Button;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author carlos
 */
public abstract class Menu {
    
    protected Image background;
    protected AABB aabb;
    
    protected ArrayList<Button> buttons;
    
    public Menu() {
        buttons = new ArrayList();
    }
    
    public abstract void init(GameContainer container);
    public abstract void update(GameContainer container, int delta);
    public abstract void render(Graphics graphics, GameContainer container);
    public abstract void mousePressed(int button, int x, int y);
    public abstract void mouseReleased(int button, int x, int y);
    
    public Image getBackground() {
        return background;
    }
}
