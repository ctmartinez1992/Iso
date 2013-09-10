package Util.Vault;

import Util.Loader.ImageLoader;
import org.newdawn.slick.Image;

/**
 *
 * @author carlos
 */
public class ImageVault {
    
    public static Image doorOpenedIcon;
    public static Image doorClosedIcon;
    
    public static Image builderIcon;
    public static Image bakerIcon;
    
    //Food
    public static Image birdIcon;
    public static Image birdCookedIcon;
    public static Image breadIcon;
    public static Image fishIcon;
    public static Image flourIcon;
    public static Image frogIcon;
    public static Image frogCookedIcon;
    public static Image grapesIcon;
    public static Image squirrelIcon;
    public static Image squirrelCookedIcon;
    public static Image wheatIcon;
    public static Image wineIcon;
    public static Image wormAliveIcon;
    
    //Resource
    public static Image boulderIcon;
    public static Image logIcon;
    public static Image plankIcon;
    public static Image seedIcon;
    public static Image stoneIcon;
    public static Image waterIcon;
    
    //Utility
    public static Image bandageIcon;
    public static Image barrelIcon;
    public static Image bedIcon;
    public static Image bookIcon;
    public static Image bowIcon;
    public static Image bowlIcon;
    public static Image bucketIcon;
    public static Image chairIcon;
    public static Image fishingRodIcon;
    public static Image forkIcon;
    public static Image knifeIcon;
    public static Image medicineIcon;
    public static Image spearIcon;
    public static Image spoonIcon;
    public static Image stoveIcon;
    
    public static void load() {
        loadIcons();
    }
    
    public static void loadIcons() {
        doorOpenedIcon = ImageLoader.loadImage("res/icons/door_opened.png");
        doorClosedIcon = ImageLoader.loadImage("res/icons/door_closed.png");
        
        builderIcon = ImageLoader.loadImage("res/icons/builder.png");
        bakerIcon = ImageLoader.loadImage("res/icons/baker.png");
                
        birdIcon = ImageLoader.loadImage("res/icons/items/bird.png");
        birdCookedIcon = ImageLoader.loadImage("res/icons/items/bird_cooked.png");
        fishIcon = ImageLoader.loadImage("res/icons/items/fish.png");
        frogIcon = ImageLoader.loadImage("res/icons/items/frog.png");
        frogCookedIcon = ImageLoader.loadImage("res/icons/items/frog_cooked.png");
        grapesIcon = ImageLoader.loadImage("res/icons/items/grapes.png");
        squirrelIcon = ImageLoader.loadImage("res/icons/items/squirrel.png");
        squirrelCookedIcon = ImageLoader.loadImage("res/icons/items/squirrel_cooked.png");
        wheatIcon = ImageLoader.loadImage("res/icons/items/wheat.png");
        wineIcon = ImageLoader.loadImage("res/icons/items/wine.png");
        wormAliveIcon = ImageLoader.loadImage("res/icons/items/worm.png");

        boulderIcon = ImageLoader.loadImage("res/icons/items/boulder.png");
        breadIcon = ImageLoader.loadImage("res/icons/items/bread.png");
        flourIcon = ImageLoader.loadImage("res/icons/items/flour.png");
        logIcon = ImageLoader.loadImage("res/icons/items/log.png");
        plankIcon = ImageLoader.loadImage("res/icons/items/plank.png");
        seedIcon = ImageLoader.loadImage("res/icons/items/seed.png");
        stoneIcon = ImageLoader.loadImage("res/icons/items/stone.png");
        waterIcon = ImageLoader.loadImage("res/icons/items/water.png");

        bandageIcon = ImageLoader.loadImage("res/icons/items/bandage.png");
        barrelIcon = ImageLoader.loadImage("res/icons/items/barrel.png");
        bedIcon = ImageLoader.loadImage("res/icons/items/bed.png");
        bookIcon = ImageLoader.loadImage("res/icons/items/book.png");
        bowIcon = ImageLoader.loadImage("res/icons/items/bow.png");
        bowlIcon = ImageLoader.loadImage("res/icons/items/bowl.png");
        bucketIcon = ImageLoader.loadImage("res/icons/items/bucket.png");
        chairIcon = ImageLoader.loadImage("res/icons/items/chair.png");
        fishingRodIcon = ImageLoader.loadImage("res/icons/items/fishing_rod.png");
        forkIcon = ImageLoader.loadImage("res/icons/items/fork.png");
        knifeIcon = ImageLoader.loadImage("res/icons/items/knife.png");
        medicineIcon = ImageLoader.loadImage("res/icons/items/medicine.png");
        spearIcon = ImageLoader.loadImage("res/icons/items/spear.png");
        spoonIcon = ImageLoader.loadImage("res/icons/items/spoon.png");
        stoveIcon = ImageLoader.loadImage("res/icons/items/stove.png");
    }
    
}
