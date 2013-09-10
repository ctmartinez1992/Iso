package Entity.WorkerEntity;

import Build.Build;
import Entity.Entity;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 *
 * @author carlos
 */
public abstract class Worker extends Entity {
    
    protected Build buildAssigned;
    protected boolean working;
    
    protected int moveX;
    protected int moveY;
    protected boolean moving;
    
    public Worker() {
        
    }

    @Override
    public abstract Worker init(int x, int y, int gridX, int gridY);
    
    @Override
    public abstract void update(GameContainer container, ArrayList<Build> builds);
    
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

    public Build getBuildAssigned() {
        return buildAssigned;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public boolean isWorking() {
        return working;
    }

    public void setMoveX(int moveX) {
        this.moveX = moveX;
    }

    public int getMoveX() {
        return moveX;
    }

    public void setMoveY(int moveY) {
        this.moveY = moveY;
    }

    public int getMoveY() {
        return moveY;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isMoving() {
        return moving;
    }
}
