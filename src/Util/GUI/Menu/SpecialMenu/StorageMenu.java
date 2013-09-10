package Util.GUI.Menu.SpecialMenu;

import Build.BuildSpec;
import Util.Loader.FontLoader;
import Util.Loader.ImageLoader;
import Util.UV;
import Util.Vault.ColorVault;
import Util.Vault.ImageVault;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 *
 * @author carlos
 */
public class StorageMenu extends SpecialMenu {
    
    public StorageMenu() {
        super();
        this.name = "Storage";
        
    }

    @Override
    public void init() {
        this.icon = ImageLoader.loadImage("res/icons/items/bread.png");
    }

    @Override
    public void update(GameContainer container, int delta) {
    }

    @Override
    public void render(Graphics graphics, GameContainer container, BuildSpec specs) {
        graphics.setFont(FontLoader.arialFont16);
        graphics.setLineWidth(1.0f);
        
        graphics.drawString(this.name, 130, UV.leftMenuResolutionHeight + 90);
        
        graphics.drawRect(45, UV.leftMenuResolutionHeight + 120, 39, 24);
        
        graphics.drawRect(90, UV.leftMenuResolutionHeight + 120, 135, 24);
        specs.getHpBar().render(graphics, container, 91, UV.leftMenuResolutionHeight + 121, (int) ((specs.getConstructionPer() / 100) * 133), 22, ColorVault.darkGreen);
        
        if (specs.isOpen()) {
            graphics.drawImage(ImageVault.doorOpenedIcon, 15, UV.leftMenuResolutionHeight + 120);
        } else {
            graphics.drawImage(ImageVault.doorClosedIcon, 15, UV.leftMenuResolutionHeight + 120);
        }
       
        graphics.setFont(FontLoader.arialFont10);
            graphics.drawString(((int) specs.getCurrentHP()) + "/" + (int) specs.getMaxHP(), 170, UV.leftMenuResolutionHeight + 125);
        graphics.setFont(FontLoader.arialFont16);
        
        if (!specs.isConstructionDone()) {
            //Food
            graphics.drawString("Food", 15, UV.leftMenuResolutionHeight + 160);
            graphics.drawLine(15, UV.leftMenuResolutionHeight + 180, 305, UV.leftMenuResolutionHeight + 180);
        
            graphics.setFont(FontLoader.arialFont10);
                //Wine
                graphics.drawRect(15, UV.leftMenuResolutionHeight + 190, 32, 42);
                graphics.drawImage(ImageVault.wineIcon, 22, UV.leftMenuResolutionHeight + 194);
                graphics.drawString(specs.getWine() + "", 29, UV.leftMenuResolutionHeight + 220);
                graphics.drawString("Wine", 20, UV.leftMenuResolutionHeight + 235);
                
                //Do the rest.
            graphics.setFont(FontLoader.arialFont16);
        } else {
            graphics.drawString("Worker:", 15, UV.leftMenuResolutionHeight + 190);
            graphics.drawString(specs.getNWorkersConstruction() + "x Builder", 80, UV.leftMenuResolutionHeight + 190);
            graphics.drawRect(170, UV.leftMenuResolutionHeight + 188, 24, 24);
            graphics.drawImage(ImageVault.builderIcon, 172, UV.leftMenuResolutionHeight + 190);
            
            graphics.drawString("Needs:", 15, UV.leftMenuResolutionHeight + 230);
            graphics.drawString(specs.getConstructionNeed1() + "x Plank", 80, UV.leftMenuResolutionHeight + 230);
            graphics.drawRect(170, UV.leftMenuResolutionHeight + 228, 24, 24);
            graphics.drawImage(ImageVault.plankIcon, 172, UV.leftMenuResolutionHeight + 232);
            graphics.drawString(specs.getConstructionNeed1Quantity() + "/" + specs.getConstructionNeed1(), 280, UV.leftMenuResolutionHeight + 230);

            graphics.drawString(specs.getConstructionNeed2() + "x Stone", 80, UV.leftMenuResolutionHeight + 270);
            graphics.drawRect(170, UV.leftMenuResolutionHeight + 268, 24, 24);
            graphics.drawImage(ImageVault.stoneIcon, 173, UV.leftMenuResolutionHeight + 271);
            graphics.drawString(specs.getConstructionNeed2Quantity() + "/" + specs.getConstructionNeed2(), 280, UV.leftMenuResolutionHeight + 270);
            
            graphics.drawString("Construction: " + ((double) Math.round(specs.getConstructionPer() * 100) / 100) + "%", 15, UV.leftMenuResolutionHeight + 400);
        }
//        
//        if (specs.isResting()) {
//            graphics.drawString("Resting...", 85, UV.leftMenuResolutionHeight + 400);
//        }
    }

    @Override
    public void mousePressed(int button, int x, int y) {
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
    }
}