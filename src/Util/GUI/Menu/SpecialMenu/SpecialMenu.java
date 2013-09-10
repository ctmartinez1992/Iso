package Util.GUI.Menu.SpecialMenu;

import Build.BuildSpec;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author carlos
 */
public abstract class SpecialMenu {
    
    protected Image icon;
    
    protected String name;
    
    protected boolean displayMenu;
    
    public SpecialMenu() {
        displayMenu = false;
    }
    
    public abstract void init();
    public abstract void update(GameContainer container, int delta);
    public abstract void render(Graphics graphics, GameContainer container, BuildSpec specs);
    public abstract void mousePressed(int button, int x, int y);
    public abstract void mouseReleased(int button, int x, int y);

    public String getName() {
        return name;
    }

    public void setDisplayMenu(boolean displayMenu) {
        this.displayMenu = displayMenu;
    }

    public boolean isDisplayMenu() {
        return displayMenu;
    }
    
}
