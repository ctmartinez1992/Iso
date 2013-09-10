package Util.GUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 *
 * @author carlos
 */
public class FillBar {
    
    private int x;
    private int y;
    
    private int width;
    private int height;
    
    private float completion;
    
    public FillBar() {
        this.x = 0;
        this.y = 0;

        this.width = 0;
        this.height = 0;

        this.completion = 0;
        
    }
    
    public void render(Graphics graphics, GameContainer container, int x, int y, int width, int height, Color color) {
        graphics.setColor(color);
            graphics.fillRect(x, y, width, height);
        graphics.setColor(Color.white);
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setCompletion(float completion) {
        this.completion = completion;
    }
    
    public float getCompletion() {
        return completion;
    }
}
