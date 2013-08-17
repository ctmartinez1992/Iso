package Tile.GroundTiles;

import Tile.Tile;
import Util.Loader.ImageLoader;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author carlos
 */
public class VoidTile extends Tile {
    
    public VoidTile() {
        super();
    }
    
    @Override
    public void init(Vector2f position) {
        this.image = ImageLoader.loadImage("res/tiles/void.png");
        this.position = position;
    }

    @Override
    public void render(GameContainer container, Graphics graphics) {
        if (getImage() != null && visible) {
            graphics.drawImage(getImage(), getPosition().x, getPosition().y - ((image.getHeight() - 1) / 2));
        }
    }
    
}
