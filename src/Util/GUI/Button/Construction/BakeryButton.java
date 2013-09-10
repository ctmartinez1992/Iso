package Util.GUI.Button.Construction;

import Build.Production.BakeryBuild;
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
public class BakeryButton extends Button {
    
    public BakeryButton() {
        super();
    }

    @Override
    public void init(GameContainer container, Image menu) {
        this.background = ImageLoader.loadImage("res/ui/button/normal_button_1024_768.png");
        this.backgroundHover = ImageLoader.loadImage("res/ui/button/normal_button_hover_1024_768.png");
        this.backgroundClicked = ImageLoader.loadImage("res/ui/button/normal_button_clicked_1024_768.png");
        this.backgroundCurrent = this.background;
        this.content = ImageLoader.loadImage("res/build/house/house_BR.png");
        
        this.position = new AABB(new Float2(6, (container.getHeight() - menu.getHeight() + 88)), new Float2(this.background.getWidth(), this.background.getHeight()));
        this.contentPosition = new Int2(10, (container.getHeight() - menu.getHeight()) + 92);
    }

    @Override
    public void update(GameContainer container, int delta) {
        if (position.contains(UV.mouseX, UV.mouseY) && GUI.buttonBeingClickedBakery) {
            this.backgroundCurrent = this.backgroundClicked;
        } else if (GUI.buttonChoosedBakery) {
            this.backgroundCurrent = this.backgroundClicked;
        } else if (position.contains(UV.mouseX, UV.mouseY) && !GUI.buttonBeingClickedBakery) {
            this.backgroundCurrent = this.backgroundHover;
        } else {
            this.backgroundCurrent = this.background;
        }
    }

    @Override
    public void render(Graphics graphics, GameContainer container) {
        graphics.drawImage(this.backgroundCurrent, this.position.getPosition().getX(), this.position.getPosition().getY());
        graphics.drawImage(this.content.getScaledCopy(0.2f), this.contentPosition.getX(), this.contentPosition.getY());
    }
    
    @Override
    public void mousePressed(int buttonPressed, int x, int y) {
        if (position.contains(UV.mouseX, UV.mouseY)) {
            GUI.buttonBeingClickedBakery = true;
            GUI.falseTileVariables();
            GUI.falsePeasantVariables();
        }
    }
    
    @Override
    public void mouseReleased(int buttonPressed, int x, int y) {
        GUI.buttonBeingClickedBakery = false;
        if (position.contains(UV.mouseX, UV.mouseY)) {
            GUI.falseContructionVariables();
            GUI.falseTileVariables();
            GUI.falsePeasantVariables();
            GUI.falseShowBuildMenuVariables();
            
            GUI.buttonChoosedBakery = true;
            
            UV.choosingBuild = new BakeryBuild();
            UV.initChoosedBuild = true;
            
            UV.choosingTile = null;
            UV.initChoosedTile = false;
            UV.choosingPeasant = null;
            UV.initChoosedPeasant = false;
        }
    }
    
}
