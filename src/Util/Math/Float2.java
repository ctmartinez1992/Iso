package Util.Math;

/**
 *
 * @author carlos
 */
public class Float2 {
    
    private float x;
    private float y;
    private boolean flag;
    
    public Float2() {
        this.x = 0;
        this.y = 0;
        this.flag = true;
    }
    
    public Float2(float x, float y) {
        this.x = x;
        this.y = y;
        this.flag = true;
    }
    
    public Float2(Float2 clone) {
        this.x = clone.x;
        this.y = clone.y;
        this.flag = true;
    }
    
    public Float2(float x, float y, boolean flag) {
        this.x = x;
        this.y = y;
        this.flag = flag;
    }
    
    public Float2 add(float xFactor, float yFactor) {
        set(this.x + xFactor, this.y + yFactor);
        return this;
    }
    
    public Float2 add(Float2 factor) {
        set(this.x + factor.getX(), this.y + factor.getY());
        return this;
    }
    
    public Float2 add(float factor) {
        set(this.x + factor, this.y + factor);
        return this;
    }
    
    public Float2 scale(float factor) {
        set(this.x * factor, this.y * factor);
        return this;
    }

    public Float2 copy() {
        return new Float2(this.x, this.y, this.flag);
    }
    
    public void set(Float2 clone) {
        this.x = clone.x;
        this.y = clone.y;
    }
    
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }
    
    public float getX() {
        return x;
    }

    public void setY(float y) {
        this.y = y;
    }
    
    public float getY() {
        return y;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }
    
    public void print() {
        System.out.println("X: " + x + " - Y: " + y + " - Flag: " + flag);
    }
}
