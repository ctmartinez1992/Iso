package Main;

import Map.Map;
import Util.GUI.GUI;
import Util.Math.Int2;
import Util.Options;
import Util.UV;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class IsoGame extends BasicGame {
    
    private Map map;
    private GUI gui;
    
    /*
     * 
     * INPUT VARIABLES
     * 
     */
    
    private boolean mouseLeft;
    private boolean mouseMiddle;
    private boolean mouseRight;

    public IsoGame() {
        super("Isometric Engine");
        
        this.map = new Map(20, 20, 64);
        this.gui = new GUI();
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        container.setShowFPS(false);
        
        this.map.init(container);
        this.gui.init(container);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        UV.mouseX = container.getInput().getMouseX();
        UV.mouseY = container.getInput().getMouseY();
        
        Int2 coords = map.getGridCoords(UV.mouseX, UV.mouseY);
        UV.mapX = coords.getX();
        UV.mapY = coords.getY();
        
        gui.update(container, delta);
        
        if (!UV.mouseOnMenu) {
            map.update(container, delta);
        }
    }

    @Override
    public void render(GameContainer container, Graphics graphics) throws SlickException {
        map.render(container, graphics);
        gui.render(graphics, container);
    }
    
    @Override
    public void mouseMoved(int oldX, int oldY, int newX, int newY) {
        if (!UV.mouseOnMenu) {
            map.mouseMoved(oldX, oldY, newX, newY);
        }
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        if (button == Input.MOUSE_LEFT_BUTTON) {
            mouseLeft = true;
            if (!UV.mouseOnMenu) {
                map.mousePressed(button, x, y);
            } else {
                gui.mousePressed(button, x, y);
            }
        }
    }
    
    @Override
    public void mouseDragged(int oldX, int oldY, int newX, int newY) {
        if (mouseLeft) {
            mouseLeft = true;
            if (!UV.mouseOnMenu) {
                map.mouseDragged(oldX, oldY, newX, newY);
            }
        }
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        if (button == Input.MOUSE_LEFT_BUTTON) {
            if (!UV.mouseOnMenu) {
                map.mouseReleased(button, x, y);
            } else {
                gui.mouseReleased(button, x, y);
            }
            
            mouseLeft = false;
        }
    }

    @Override
    public boolean closeRequested() {
        return true;
    }

    public static void main(String[] args) throws Exception {
        Options.getInstance().readFile();
        
        AppGameContainer app = new AppGameContainer(new IsoGame());

        app.setDisplayMode(Options.getInstance().getWidth(), Options.getInstance().getHeight(), Options.getInstance().isFullscreen());
        app.setVSync(Options.getInstance().isVsync());
        app.start();
    }
}
