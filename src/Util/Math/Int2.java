package Util.Math;

/**
 *
 * @author carlos
 */
public class Int2 {
    
    private int x;
    private int y;
    private boolean flag;
    
    public Int2() {
        this.x = 0;
        this.y = 0;
        this.flag = true;
    }
    
    public Int2(int x, int y) {
        this.x = x;
        this.y = y;
        this.flag = true;
    }
    
    public Int2(Int2 clone) {
        this.x = clone.x;
        this.y = clone.y;
        this.flag = true;
    }
    
    public Int2(int x, int y, boolean flag) {
        this.x = x;
        this.y = y;
        this.flag = flag;
    }
    
    public Int2 copy() {
        return new Int2(x, y, flag);
    }
    
    public void add(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public boolean compare(Int2 other) {
        return (x == other.x && y == other.y);
    }
    
    public void set(int x, int y) {
        setX(x);
        setY(y);
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
