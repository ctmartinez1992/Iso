package Util.GUI.Button;

import Util.AABB;
import Util.Math.Int2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author carlos
 */
public abstract class Button {
    
    protected Image background;
    protected Image backgroundHover;
    protected Image backgroundClicked;
    protected Image backgroundCurrent;
    protected AABB position;
    
    protected Image content;
    protected Int2 contentPosition;
    
    public Button() {
    }
    
    public abstract void init(GameContainer container, Image menu);
    public abstract void update(GameContainer container, int delta);
    public abstract void render(Graphics graphics, GameContainer container);
    public abstract void mousePressed(int button, int x, int y);
    public abstract void mouseReleased(int button, int x, int y);
    
    public Image getBackground() {
        return background;
    }
    
    public Image getBackgroundHover() {
        return backgroundHover;
    }
    
    public Image getContent() {
        return content;
    }
}
