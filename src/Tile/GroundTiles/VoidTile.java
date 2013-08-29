package Tile.GroundTiles;

import Tile.Tile;
import Util.Loader.ImageLoader;
import org.newdawn.slick.Color;
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
    
    @Override
    public void renderTranslucent(GameContainer container, Graphics graphics, boolean doTile) {
        if (getImage() != null && visible) {
            Color transparent = new Color(1, 1, 1, 0.8f);
            graphics.drawImage(getImage(), getPosition().x, getPosition().y - (image.getHeight() - 20), transparent);
//            
//            graphics.setLineWidth(2);
//            
//            if (doBuild) {
//                graphics.setColor(new Color(UV.highlightColor.getX(), UV.highlightColor.getY(), UV.highlightColor.getZ()));
//            } else {
//                graphics.setColor(new Color(UV.highlightUnavailableColor.getX(), UV.highlightUnavailableColor.getY(), UV.highlightUnavailableColor.getZ()));
//            }
//            
//            this.square.setLocation(getPosition());
//            graphics.draw(square);
        }
    }
    
    @Override
    public Tile cloneTile(Vector2f position) {
        VoidTile tmpTile = new VoidTile();
        tmpTile.init(position);
        return tmpTile;
    }
}
