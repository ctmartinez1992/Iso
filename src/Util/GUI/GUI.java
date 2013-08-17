package Util.GUI;

import Util.GUI.Menu.*;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 *
 * @author carlos
 */
public class GUI {
    
    //Menus modifiers.
        public static boolean leftMenuNeedsUpdate = false;
    
    //Buttons modifiers.
        public static void falseAllVariables() {
            buttonBeingClickedMarshallsLoft = false;
            buttonChoosedMarshallsLoft = false;
            buttonBeingClickedCube = false;
            buttonChoosedCube = false;}
    
        //Marshall's Loft.
            public static boolean buttonBeingClickedMarshallsLoft = false;
            public static boolean buttonChoosedMarshallsLoft = false;
        
        //Cube.
            public static boolean buttonBeingClickedCube = false;
            public static boolean buttonChoosedCube = false;
    
    
    private ArrayList<Menu> menus;
    
    public GUI() {
        menus = new ArrayList();
    }
    
    public void init(GameContainer container) {
        menus.add(new SpecialInfoMenu());
        menus.add(new DebugMenu());
        menus.add(new LeftMenu());
        
        for (Menu menu : menus) {
            menu.init(container);
        }
    }
    
    public void update(GameContainer container, int delta) {
        for (Menu menu : menus) {
            menu.update(container, delta);
        }
    }
    
    public void render(Graphics graphics, GameContainer container) {
        for (Menu menu : menus) {
            menu.render(graphics, container);
        }
    }
    
    public void mousePressed(int button, int x, int y) {
        for (Menu menu : menus) {
            menu.mousePressed(button, x, y);
        }
    }
    
    public void mouseReleased(int button, int x, int y) {
        for (Menu menu : menus) {
            menu.mouseReleased(button, x, y);
        }
    }
    
}
