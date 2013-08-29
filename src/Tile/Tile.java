package Tile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class Tile {

    protected Vector2f position;
    protected Image image;
    
    //Can you see the image in this tile?
    protected boolean visible;
    
    //Is this tile highlighted?
    protected boolean highlight;

    public Tile() {
        this.highlight = false;
        this.visible = true;
    }
    
    public void init(Vector2f position) {
    }

    public void render(GameContainer container, Graphics graphics) {
    }

    public void renderTranslucent(GameContainer container, Graphics graphics, boolean doTile) {
    }
    
    public Tile cloneTile(Vector2f position) {
        return null;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }
    
    public Vector2f getPosition() {
        return position;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }
}
