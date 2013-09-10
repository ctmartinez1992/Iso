package Util.GUI.Button.Peasant;

import Entity.WorkerEntity.Serf;
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
public class SerfButton extends Button {
    
    public SerfButton() {
        super();
    }

    @Override
    public void init(GameContainer container, Image menu) {
        this.background = ImageLoader.loadImage("res/ui/button/normal_button_1024_768.png");
        this.backgroundHover = ImageLoader.loadImage("res/ui/button/normal_button_hover_1024_768.png");
        this.backgroundClicked = ImageLoader.loadImage("res/ui/button/normal_button_clicked_1024_768.png");
        this.backgroundCurrent = this.background;
        this.content = ImageLoader.loadImage("res/people/serf/serf.png");
        
        this.position = new AABB(new Float2(6, (container.getHeight() - menu.getHeight() + 88)), new Float2(this.background.getWidth(), this.background.getHeight()));
        this.contentPosition = new Int2(24, (container.getHeight() - menu.getHeight()) + 94);
    }

    @Override
    public void update(GameContainer container, int delta) {
        if (position.contains(UV.mouseX, UV.mouseY) && GUI.buttonBeingClickedSerf) {
            this.backgroundCurrent = this.backgroundClicked;
        } else if (GUI.buttonChoosedSerf) {
            this.backgroundCurrent = this.backgroundClicked;
        } else if (position.contains(UV.mouseX, UV.mouseY) && !GUI.buttonBeingClickedSerf) {
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
            GUI.buttonBeingClickedSerf = true;
            GUI.falseContructionVariables();
            GUI.falseTileVariables();
        }
    }
    
    @Override
    public void mouseReleased(int buttonPressed, int x, int y) {
        GUI.buttonBeingClickedSerf = false;
        if (position.contains(UV.mouseX, UV.mouseY)) {
            GUI.falseContructionVariables();
            GUI.falseTileVariables();
            GUI.falsePeasantVariables();
            GUI.falseShowBuildMenuVariables();
            
            GUI.buttonChoosedSerf = true;
            
            UV.choosingPeasant = new Serf();
            UV.initChoosedPeasant = true;
            
            UV.choosingBuild = null;
            UV.initChoosedBuild = false;
            UV.choosingTile = null;
            UV.initChoosedTile = false;
        }
    }
    
}
