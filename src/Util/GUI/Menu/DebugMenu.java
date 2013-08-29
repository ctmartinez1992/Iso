package Util.GUI.Menu;

import Util.AABB;
import Util.GUI.Button.Button;
import Util.Loader.ImageLoader;
import Util.Math.Float2;
import Util.UV;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 *
 * @author carlos
 */
public class DebugMenu extends Menu {
    
    private String time;
    
    public DebugMenu() {
        super();
    }

    @Override
    public void init(GameContainer container) {
        this.background = ImageLoader.loadImage("res/ui/menu/debug_menu_1024_768.png");
        this.aabb = new AABB(new Float2(1, 1), new Float2(this.background.getWidth(), this.background.getHeight()));
    }

    @Override
    public void update(GameContainer container, int delta) {
        if (aabb.contains(UV.mouseX, UV.mouseY)) {
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
        
        graphics.setColor(new Color(UV.highlightColor.getX(), UV.highlightColor.getY(), UV.highlightColor.getZ()));
        graphics.drawString(String.format("FPS: %d", container.getFPS()), 5, 0);
        graphics.drawString(String.format("Mouse X: %d Y: %d", UV.mouseX, UV.mouseY), 5, 15);
        graphics.drawString(String.format("Grid X: %d Y: %d", UV.mapX, UV.mapY), 5, 30);
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
