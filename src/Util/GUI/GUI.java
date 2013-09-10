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
    
    /*
     * 
     * buttonBeingClicked...        controls when a mouse is over a certain button and is being clicked/pressed...
     * buttonChoosed...             controls if a button was clicked and should stay clicked until the user clicks on something else...
     * 
     */
    
    //Menus modifiers.
        public static boolean showConstructionLeftMenu = false;
        public static boolean showTileLeftMenu = false;
        public static boolean showPeasantLeftMenu = false;
    
        public static boolean leftMenuNeedsUpdate = false;
        
    //False Methods.
        public static void falseShowVariables() {
            showConstructionLeftMenu = false;
            showTileLeftMenu = false;
            showPeasantLeftMenu = false;
        }
        
        public static void falseMainVariables() {
            buttonBeingClickedBuilding = false;
            buttonChoosedBuilding = false;
            
            buttonBeingClickedTile = false;
            buttonChoosedTile = false;
            
            buttonBeingClickedPeasant = false;
            buttonChoosedPeasant = false;
        }
        
        public static void falseContructionVariables() {
            buttonBeingClickedBakery = false;
            buttonChoosedBakery = false;
            
            buttonBeingClickedCube = false;
            buttonChoosedCube = false;
            
            buttonBeingClickedStorage = false;
            buttonChoosedStorage = false;
        }
        
        public static void falseTileVariables() {
            buttonBeingClickedGrassTile = false;
            buttonChoosedGrassTile = false;
            
            buttonBeingClickedDirtTile = false;
            buttonChoosedDirtTile = false;
            
            buttonBeingClickedRoadTile = false;
            buttonChoosedRoadTile = false;
        }
        
        public static void falsePeasantVariables() {
            buttonBeingClickedSerf = false;
            buttonChoosedSerf = false;
            buttonBeingClickedBuilder = false;
            buttonChoosedBuilder = false;
        }
        
        public static void falseShowBuildMenuVariables() {
            buttonShowMenuBakery = false;
        }
        
    //Buttons modifiers.  
        //Icons.
            //Building Icon.
                public static boolean buttonBeingClickedBuilding = false;
                public static boolean buttonChoosedBuilding = false;

            //Tile Icon.
                public static boolean buttonBeingClickedTile = false;
                public static boolean buttonChoosedTile = false;

            //Peasant Icon.
                public static boolean buttonBeingClickedPeasant = false;
                public static boolean buttonChoosedPeasant = false;
    
        //Builds. 
            //Bakery Build.
                public static boolean buttonBeingClickedBakery = false;
                public static boolean buttonChoosedBakery = false;
                public static boolean buttonShowMenuBakery = false;

            //Cube Build.
                public static boolean buttonBeingClickedCube = false;
                public static boolean buttonChoosedCube = false;

            //Storage Build.
                public static boolean buttonBeingClickedStorage = false;
                public static boolean buttonChoosedStorage = false;
    
        //Tiles.
            //Grass Tile.
                public static boolean buttonBeingClickedGrassTile = false;
                public static boolean buttonChoosedGrassTile = false;

            //Dirt Tile.
                public static boolean buttonBeingClickedDirtTile = false;
                public static boolean buttonChoosedDirtTile = false;

            //Road Tile.
                public static boolean buttonBeingClickedRoadTile = false;
                public static boolean buttonChoosedRoadTile = false;
    
        //Peasants. 
            //Serf Dude.
                public static boolean buttonBeingClickedSerf = false;
                public static boolean buttonChoosedSerf = false;
                
            //Builder Dude.
                public static boolean buttonBeingClickedBuilder = false;
                public static boolean buttonChoosedBuilder = false;
    
    
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
