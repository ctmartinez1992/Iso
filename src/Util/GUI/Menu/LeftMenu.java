package Util.GUI.Menu;

import Util.GUI.Button.Construction.*;
import Util.GUI.Button.Tile.*;
import Util.AABB;
import Util.GUI.Button.*;
import Util.GUI.Button.Peasant.*;
import Util.GUI.GUI;
import Util.Loader.ImageLoader;
import Util.Math.Float2;
import Util.UV;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 *
 * @author carlos
 */
public class LeftMenu extends Menu {
    
    private ArrayList<Button> constructionButtons;
    private ArrayList<Button> tileButtons;
    private ArrayList<Button> peasantButtons;
    
    public LeftMenu() {
        super();
        constructionButtons = new ArrayList();
        tileButtons = new ArrayList();
        peasantButtons = new ArrayList();
    }

    @Override
    public void init(GameContainer container) {
        this.background = ImageLoader.loadImage("res/ui/menu/left_menu_1024_768_2.png");
        this.aabb = new AABB(new Float2(1, (container.getHeight() - this.background.getHeight())), new Float2(this.background.getWidth(), this.background.getHeight()));
        
        UV.leftMenuWidth = this.background.getWidth();
        UV.leftMenuHeight = this.background.getHeight();
        
        UV.leftMenuResolutionWidth = container.getWidth() - UV.leftMenuWidth;
        UV.leftMenuResolutionHeight = container.getHeight() - UV.leftMenuHeight;
        
        this.buttons.add(new ConstructionButton());
        this.buttons.add(new TileButton());
        this.buttons.add(new PeasantButton());
        
        this.constructionButtons.add(new BakeryButton());
        this.constructionButtons.add(new CubeButton());
        this.constructionButtons.add(new StorageButton());
        
        this.tileButtons.add(new GrassButton());
        this.tileButtons.add(new DirtButton());
        this.tileButtons.add(new RoadButton());
        
        this.peasantButtons.add(new SerfButton());
        this.peasantButtons.add(new BuilderButton());
        
        for (Button button : buttons) {
            button.init(container, this.background);
        }
        
        for (Button button : constructionButtons) {
            button.init(container, this.background);
        }
        
        for (Button button : tileButtons) {
            button.init(container, this.background);
        }
        
        for (Button button : peasantButtons) {
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
            
            if (GUI.showConstructionLeftMenu) {
                for (Button button : constructionButtons) {
                    button.update(container, delta);
                }
            }
            
            if (GUI.showTileLeftMenu) {
                for (Button button : tileButtons) {
                    button.update(container, delta);
                }
            }
            
            if (GUI.showPeasantLeftMenu) {
                for (Button button : peasantButtons) {
                    button.update(container, delta);
                }
            }
            
        } else if (aabb.contains(UV.mouseX, UV.mouseY)) {
            UV.mouseOnMenu = true;
            for (Button button : buttons) {
                button.update(container, delta);
            }
            
            if (GUI.showConstructionLeftMenu) {
                for (Button button : constructionButtons) {
                    button.update(container, delta);
                }
            }
            
            if (GUI.showTileLeftMenu) {
                for (Button button : tileButtons) {
                    button.update(container, delta);
                }
            }
            
            if (GUI.showPeasantLeftMenu) {
                for (Button button : peasantButtons) {
                    button.update(container, delta);
                }
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
        
        if (GUI.showConstructionLeftMenu) {
            for (Button button : constructionButtons) {
                button.render(graphics, container);
            }
        }
        
        if (GUI.showTileLeftMenu) {
            for (Button button : tileButtons) {
                button.render(graphics, container);
            }
        }
        
        if (GUI.showPeasantLeftMenu) {
            for (Button button : peasantButtons) {
                button.render(graphics, container);
            }
        }
    }
    
    @Override
    public void mousePressed(int buttonPressed, int x, int y) {
        for (Button button : buttons) {
            button.mousePressed(buttonPressed, x, y);
        }
        
        if (GUI.showConstructionLeftMenu) {
            for (Button button : constructionButtons) {
                button.mousePressed(buttonPressed, x, y);
            }
        }
        
        if (GUI.showTileLeftMenu) {
            for (Button button : tileButtons) {
                button.mousePressed(buttonPressed, x, y);
            }
        }
        
        if (GUI.showPeasantLeftMenu) {
            for (Button button : peasantButtons) {
                button.mousePressed(buttonPressed, x, y);
            }
        }
    }
    
    @Override
    public void mouseReleased(int buttonPressed, int x, int y) {
        for (Button button : buttons) {
            button.mouseReleased(buttonPressed, x, y);
        }
        
        if (GUI.showConstructionLeftMenu) {
            for (Button button : constructionButtons) {
                button.mouseReleased(buttonPressed, x, y);
            }
        }
        
        if (GUI.showTileLeftMenu) {
            for (Button button : tileButtons) {
                button.mouseReleased(buttonPressed, x, y);
            }
        }
        
        if (GUI.showPeasantLeftMenu) {
            for (Button button : peasantButtons) {
                button.mouseReleased(buttonPressed, x, y);
            }
        }
    }
}
