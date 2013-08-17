package Util.GUI.Menu;

import Util.AABB;
import Util.GUI.Button.*;
import Util.GUI.GUI;
import Util.Loader.ImageLoader;
import Util.Math.Float2;
import Util.UV;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 *
 * @author carlos
 */
public class LeftMenu extends Menu {
    
    private String time;
    
    public LeftMenu() {
        super();
    }

    @Override
    public void init(GameContainer container) {
        this.background = ImageLoader.loadImage("res/ui/menu/left_menu_1024_768.png");
        this.aabb = new AABB(new Float2(1, (container.getHeight() - this.background.getHeight())), new Float2(this.background.getWidth(), this.background.getHeight()));
        
        this.buttons.add(new MarshallsLoftButton());
        this.buttons.add(new CubeButton());
        
        for (Button button : buttons) {
            button.init(container, this.background);
        }
    }

    @Override
    public void update(GameContainer container, int delta) {
        if (GUI.leftMenuNeedsUpdate) {
            GUI.leftMenuNeedsUpdate = false;
            for (Button button : buttons) {
                button.update(container, delta);
            }
        } else if (aabb.contains(UV.mouseX, UV.mouseY)) {
            UV.mouseOnMenu = true;
            for (Button button : buttons) {
                button.update(container, delta);
            }
        } else {
            UV.mouseOnMenu = false;
        }
    }

    @Override
    public void render(Graphics graphics, GameContainer container) {
        graphics.drawImage(this.background, this.aabb.getPosition().getX(), this.aabb.getPosition().getY());
        
        for (Button button : buttons) {
            button.render(graphics, container);
        }
    }
    
    @Override
    public void mousePressed(int buttonPressed, int x, int y) {
        for (Button button : buttons) {
            button.mousePressed(buttonPressed, x, y);
        }
    }
    
    @Override
    public void mouseReleased(int buttonPressed, int x, int y) {
        for (Button button : buttons) {
            button.mouseReleased(buttonPressed, x, y);
        }
    }
}
