package Util;

import Build.Build;
import Util.Math.Int3;

/**
 * UV stands for "Universal Values"
 * It's a set of static values that are needed in a lot of different places and
 * instead of having them as static values in their respective instantiation
 * classes... i put them all here and use this as an universal vault of values.
 * 
 * @author carlos
 */
public class UV {
    
    //The build that's going to be constructed.
    public static Build choosingBuild;
    
    //Colors used to display tiles.
    public static Int3 normalColor = new Int3(128, 128, 128);
    public static Int3 highlightColor = new Int3(224, 224, 224);
    public static Int3 highlightUnavailableColor = new Int3(224, 16, 16);
    
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
    
    //Is the cursor on top of any menu, if so, only update the gui stuff.
    public static boolean mouseOnMenu = false;
    
    //Do i need to init the build that i chose in the Map class.
    public static boolean initChoosedBuild = false;
    
}
