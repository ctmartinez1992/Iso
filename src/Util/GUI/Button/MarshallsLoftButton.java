package Util.GUI.Button;

import Build.TestBuilds.MarshallsLoftBuild;
import Util.AABB;
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
public class MarshallsLoftButton extends Button {
    
    public MarshallsLoftButton() {
        super();
    }

    @Override
    public void init(GameContainer container, Image menu) {
        this.background = ImageLoader.loadImage("res/ui/button/normal_button_1024_768.png");
        this.backgroundHover = ImageLoader.loadImage("res/ui/button/normal_button_hover_1024_768.png");
        this.backgroundClicked = ImageLoader.loadImage("res/ui/button/normal_button_clicked_1024_768.png");
        this.backgroundCurrent = this.background;
        this.content = ImageLoader.loadImage("res/build/marshalls_loft.png");
        
        this.position = new AABB(new Float2(4, (container.getHeight() - menu.getHeight() + 4)), new Float2(this.background.getWidth(), this.background.getHeight()));
        this.contentPosition = new Int2(8, (container.getHeight() - menu.getHeight()) + 5);
    }

    @Override
    public void update(GameContainer container, int delta) {
        if (position.contains(UV.mouseX, UV.mouseY) && GUI.buttonBeingClickedMarshallsLoft) {
            this.backgroundCurrent = this.backgroundClicked;
        } else if (GUI.buttonChoosedMarshallsLoft) {
            this.backgroundCurrent = this.backgroundClicked;
        } else if (position.contains(UV.mouseX, UV.mouseY) && !GUI.buttonBeingClickedMarshallsLoft) {
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
            GUI.buttonBeingClickedMarshallsLoft = true;
        }
    }
    
    @Override
    public void mouseReleased(int buttonPressed, int x, int y) {
        GUI.buttonBeingClickedMarshallsLoft = false;
        if (position.contains(UV.mouseX, UV.mouseY)) {
            GUI.falseAllVariables();
            GUI.buttonChoosedMarshallsLoft = true;
            UV.choosingBuild = new MarshallsLoftBuild();
            UV.initChoosedBuild = true;
        }
    }
    
}
