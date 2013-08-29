package Entity.WorkerEntity;

import Entity.Entity;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 *
 * @author carlos
 */
public abstract class Worker extends Entity {
    
    public Worker() {
        
    }

    @Override
    public abstract Worker init(int x, int y);
    
    @Override
    public abstract void update(GameContainer container);
    
    @Override
    public abstract void render(GameContainer container, Graphics graphics);

    @Override
    public abstract void renderTranslucent(GameContainer container, Graphics graphics, boolean doPeasant);
    
    
    public void move(int newX, int newY) {
        if (newX != 0 && newY != 0) {
            move(newX, 0);
            move(0, newY);
            return;
        }
        
//        if (xa > 0) {
//            direction = 1;
//        }
//        if (xa < 0) {
//            direction = 3;
//        }
//        if (ya > 0) {
//            direction = 0;
//        }
//        if (ya < 0) {
//            direction = 2;
//        }
        
//        if (!collision(xa, ya, spriteSize)) {
            //this.position.add(newX, newY);
//        }
    }
}
