package Entity;

import Build.Build;
import Util.Math.Int2;
import java.util.ArrayList;
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
    protected Int2 gridPosition;
    
    protected Image image;
    
    protected String name;
    protected boolean showName;
    
    public Entity() {
        
    }
    
    public abstract Entity init(int x, int y, int gridX, int gridY);
    public abstract void update(GameContainer container, ArrayList<Build> builds);
    public abstract void render(GameContainer container, Graphics graphics);
    public abstract void renderTranslucent(GameContainer container, Graphics graphics, boolean doPeasant);
    
    public void setPosition(Vector2f position) {
        if (this.position != null) {
            this.position.set(position);
        }
    }
    
    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }
    
    public Vector2f getPosition() {
        return this.position;
    }

    public void setGridPosition(Int2 gridPosition) {
        this.gridPosition = gridPosition.copy();
    }

    public void setGridPosition(int x, int y) {
        if (this.gridPosition != null) {
            this.gridPosition.set(x, y);
        }
    }
    
    public Int2 getgridPosition() {
        return this.gridPosition;
    }
}
