package Util.GUI.Button.Tile;

import Tile.GroundTiles.NormalDirtTile;
import Util.AABB;
import Util.GUI.Button.Button;
import Util.GUI.GUI;
import Util.Loader.ImageLoader;
import Util.Math.Float2;
import Util.Math.Int2;
import Util.UV;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author carlos
 */
public class DirtButton extends Button {
    
    public DirtButton() {
        super();
    }

    @Override
    public void init(GameContainer container, Image menu) {
        this.background = ImageLoader.loadImage("res/ui/button/normal_button_1024_768.png");
        this.backgroundHover = ImageLoader.loadImage("res/ui/button/normal_button_hover_1024_768.png");
        this.backgroundClicked = ImageLoader.loadImage("res/ui/button/normal_button_clicked_1024_768.png");
        this.backgroundCurrent = this.background;
        this.content = ImageLoader.loadImage("res/tiles/dirt.png");
        
        this.position = new AABB(new Float2(58, (container.getHeight() - menu.getHeight() + 88)), new Float2(this.background.getWidth(), this.background.getHeight()));
        this.contentPosition = new Int2(60, (container.getHeight() - menu.getHeight()) + 100);
    }

    @Override
    public void update(GameContainer container, int delta) {
        if (position.contains(UV.mouseX, UV.mouseY) && GUI.buttonBeingClickedDirtTile) {
            this.backgroundCurrent = this.backgroundClicked;
        } else if (GUI.buttonChoosedDirtTile) {
            this.backgroundCurrent = this.backgroundClicked;
        } else if (position.contains(UV.mouseX, UV.mouseY) && !GUI.buttonBeingClickedDirtTile) {
            this.backgroundCurrent = this.backgroundHover;
        } else {
            this.backgroundCurrent = this.background;
        }
    }

    @Override
    public void render(Graphics graphics, GameContainer container) {
        graphics.drawImage(this.backgroundCurrent, this.position.getPosition().getX(), this.position.getPosition().getY());
        graphics.drawImage(this.content.getScaledCopy(0.7f), this.contentPosition.getX(), this.contentPosition.getY());
    }
    
    @Override
    public void mousePressed(int buttonPressed, int x, int y) {
        if (position.contains(UV.mouseX, UV.mouseY)) {
            GUI.buttonBeingClickedDirtTile = true;
            GUI.falseContructionVariables();
            GUI.falsePeasantVariables();
        }
    }
    
    @Override
    public void mouseReleased(int buttonPressed, int x, int y) {
        GUI.buttonBeingClickedDirtTile = false;
        if (position.contains(UV.mouseX, UV.mouseY)) {
            GUI.falseTileVariables();
            GUI.falseShowBuildMenuVariables();
            
            GUI.buttonChoosedDirtTile = true;
            
            UV.choosingTile = new NormalDirtTile();
            UV.initChoosedTile = true;
            
            UV.choosingBuild = null;
            UV.initChoosedBuild = false;
            UV.choosingPeasant = null;
            UV.initChoosedPeasant = false;
        }
    }
    
}
