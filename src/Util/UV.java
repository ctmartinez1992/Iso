package Util;

import Build.Build;
import Entity.Entity;
import Tile.Tile;
import Util.Loader.ImageLoader;
import Util.Math.Int2;
import Util.Math.Int3;
import java.util.ArrayList;
import org.newdawn.slick.geom.Vector2f;

/**
 * UV stands for "Universal Values"
 * It's a set of static values that are needed in a lot of different places and
 * instead of having them as static values in their respective instantiation
 * classes... i put them all here and use this as an universal vault of values.
 * 
 * @author carlos
 */
public class UV {
    
    //Used for Isometric coordinates.
    public static Vector2f origin;
    
    //The build that's going to be constructed.
    public static Build choosingBuild;
    
    //A build that was clicked on.
    public static Build selectedBuild;
    
    //The tile that's going to be constructed.
    public static Tile choosingTile;
    public static ArrayList<Tile> choosingSquareTiles = new ArrayList();
    
    //The peasant that's going to be born.
    public static Entity choosingPeasant;
    
    //Colors used to display tiles.
    public static Int3 normalColor = new Int3(128, 128, 128);
    public static Int3 highlightColor = new Int3(224, 224, 224);
    public static Int3 highlightUnavailableColor = new Int3(224, 16, 16);
    
    //Where i started AND finished to drag my mouse to do a square of tiles.
    public static Int2 tileDrawStarted = new Int2();
    public static Int2 tileDrawStopped = new Int2();
    
    //Width and Height of the map.
    public static int gridWidth;
    public static int gridHeight;
    
    //Width and Height of a single tile.
    public static int tileWidth;
    public static int tileHeight;
    
    //Position of the mouse.
    public static int mouseX;
    public static int mouseY;
    
    //Position of the mouse in isometric coordinates.
    public static int mapX;
    public static int mapY;
    
    //Dimensions of the Left Menu.
    public static int leftMenuWidth;
    public static int leftMenuHeight;
    
    //Space in Resolution and Left Menu Dimensions
    public static int leftMenuResolutionWidth;
    public static int leftMenuResolutionHeight;
    
    //Is the cursor on top of any menu? If so, only update the gui stuff.
    public static boolean mouseOnMenu = false;
    
    //Do i need to init the build that i chose?
    public static boolean initChoosedBuild = false;
    
    //Do i need to init the tile that i chose?
    public static boolean initChoosedTile = false;
    
    //Do i need to init the peasant that i chose?
    public static boolean initChoosedPeasant = false;
    
    //Am i dragging the mouse to do a square of tiles?
    public static boolean doingSquareOfTiles = false;
}
