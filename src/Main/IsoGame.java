package Main;

import Map.Map;
import Sound.SoundManager;
import Util.GUI.GUI;
import Util.Loader.FontLoader;
import Util.Math.Int2;
import Util.Options;
import Util.UV;
import Util.Vault.ColorVault;
import Util.Vault.ImageVault;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class IsoGame extends BasicGame {
    
    public static AppGameContainer app;
    
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
    
    private static IsoGame _instance;
    
    public static IsoGame getInstance() {
        if (_instance == null) {
            _instance = new IsoGame();
        }
        return _instance;
    }

    public IsoGame() {
        super("Isometric Engine");
        
        this.map = new Map(20, 20, 64);
        this.gui = new GUI();
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        container.setShowFPS(false);
        
        FontLoader.init();
        SoundManager.init();
        ImageVault.load();
        ColorVault.load();
        
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
        map.update(container, delta);
    }

    @Override
    public void render(GameContainer container, Graphics graphics) throws SlickException {
        map.render(container, graphics);
        gui.render(graphics, container);
        map.renderAfterGUI(container, graphics);
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
            mouseRight = false;
            if (!UV.mouseOnMenu) {
                map.mouseLeftPressed(button, x, y);
            } else {
                gui.mousePressed(button, x, y);
            }
        }
        
        if (button == Input.MOUSE_RIGHT_BUTTON) {
            mouseRight = true;
            mouseLeft = false;
            if (!UV.mouseOnMenu) {
                map.mouseRightPressed(button, x, y);
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
                map.mouseLeftReleased(button, x, y);
            } else {
                gui.mouseReleased(button, x, y);
            }
            
            mouseLeft = false;
        }
        
        if (button == Input.MOUSE_RIGHT_BUTTON) {
            if (!UV.mouseOnMenu) {
                map.mouseRightReleased(button, x, y);
            }
            
            mouseRight = false;
        }
    }
    
    @Override
    public void keyPressed(int key, char character) {
        
    }

    @Override
    public void keyReleased(int key, char character) {
        if (key == Input.KEY_P) {
            SoundManager.pauseMusic(SoundManager.ruins);
        }
        if (key == Input.KEY_O) {
            SoundManager.resumeMusic(SoundManager.ruins);
        }
        if (key == Input.KEY_UP) {
            SoundManager.increaseMusicVolume(SoundManager.ruins);
        }
        if (key == Input.KEY_DOWN) {
            SoundManager.decreaseMusicVolume(SoundManager.ruins);
        }
        
//        if (key == Input.KEY_A) {
//            SoundManager.playSound(SoundManager.clav);
//        }
//        if (key == Input.KEY_S) {
//            SoundManager.playSound(SoundManager.guiro);
//        }
//        if (key == Input.KEY_D) {
//            SoundManager.playSound(SoundManager.hat);
//        }
//        if (key == Input.KEY_F) {
//            SoundManager.playSound(SoundManager.kick);
//        }
//        if (key == Input.KEY_G) {
//            SoundManager.playSound(SoundManager.shake);
//        }
//        if (key == Input.KEY_H) {
//            SoundManager.playSound(SoundManager.toc);
//        }
//        if (key == Input.KEY_Z) {
//            SoundManager.playSound(SoundManager.tom1);
//        }
//        if (key == Input.KEY_X) {
//            SoundManager.playSound(SoundManager.tom2);
//        }
//        if (key == Input.KEY_C) {
//            SoundManager.playSound(SoundManager.tom3);
//        }
//        if (key == Input.KEY_V) {
//            SoundManager.playSound(SoundManager.tom4);
//        }
//        if (key == Input.KEY_B) {
//            SoundManager.playSound(SoundManager.triangle);
//        }
//        
//        if (key == Input.KEY_Q) {
//            SoundManager.playSound(SoundManager.tom4, 0.5f, 1.0);
//        }
//        if (key == Input.KEY_W) {
//            SoundManager.playSound(SoundManager.tom4, 0.5f, -1.0);
//        }
        
        if (key == Input.KEY_ESCAPE) {
             closeRequested();
             IsoGame.app.exit();
        }
        
        if (UV.selectedBuild != null) {
            if (UV.selectedBuild.getSpecifications().isConstructionDone()) {
                if (key == Input.KEY_Q) {
                    UV.selectedBuild.getSpecifications().addNeed1Quantity();
                }
                if (key == Input.KEY_A) {
                    UV.selectedBuild.getSpecifications().subNeed1Quantity();
                }
                if (key == Input.KEY_W) {
                    UV.selectedBuild.getSpecifications().addNeed2Quantity();
                }
                if (key == Input.KEY_S) {
                    UV.selectedBuild.getSpecifications().subNeed2Quantity();
                }
                if (key == Input.KEY_E) {
                    UV.selectedBuild.getSpecifications().addProduce1Quantity();
                }
                if (key == Input.KEY_D) {
                    UV.selectedBuild.getSpecifications().subProduce1Quantity();
                }
                if (key == Input.KEY_Y) {
                    UV.selectedBuild.getSpecifications().addWorkerQuantity();
                }
                if (key == Input.KEY_H) {
                    UV.selectedBuild.getSpecifications().subWorkerQuantity();
                }
            } else {
                if (key == Input.KEY_R) {
                    UV.selectedBuild.getSpecifications().addConstructionNeed1Quantity();
                }
                if (key == Input.KEY_F) {
                    UV.selectedBuild.getSpecifications().subConstructionNeed1Quantity();
                }
                if (key == Input.KEY_T) {
                    UV.selectedBuild.getSpecifications().addConstructionNeed2Quantity();
                }
                if (key == Input.KEY_G) {
                    UV.selectedBuild.getSpecifications().subConstructionNeed2Quantity();
                }
            }
        }
    }

    @Override
    public boolean closeRequested() {
        SoundManager.shutdown();
        return true;
    }

    public static void main(String[] args) throws Exception {
        Options.getInstance().readFile();
        
        app = new AppGameContainer(IsoGame.getInstance());

        app.setDisplayMode(Options.getInstance().getWidth(), Options.getInstance().getHeight(), Options.getInstance().isFullscreen());
        app.setVSync(Options.getInstance().isVsync());
        app.start();
    }
}
