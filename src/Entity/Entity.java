package Entity;

import Util.Math.Int2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author carlos
 */
public abstract class Entity {
    
    protected Vector2f position;
    protected Image image;
    
    private Int2 namePosition;
    protected String name;
    protected boolean showName;
    
    public Entity() {
        
    }
    
    public abstract Entity init(int x, int y);
    public abstract void update(GameContainer container);
    public abstract void render(GameContainer container, Graphics graphics);
    public abstract void renderTranslucent(GameContainer container, Graphics graphics, boolean doPeasant);
    
    public void setPosition(Vector2f position) {
        this.position.set(position);
    }
    
    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }
    
    public Vector2f getPosition() {
        return this.position;
    }

    public void setNamePosition(Int2 namePosition) {
        this.namePosition = namePosition.copy();
    }

    public void setNamePosition(int x, int y) {
        this.namePosition.set(x, y);
    }
    
    public Int2 getNamePosition() {
        return this.namePosition;
    }
}
