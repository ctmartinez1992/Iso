package Util.Math;

/**
 *
 * @author carlos
 */
public class Double2 {
    
    private double x;
    private double y;
    private boolean flag;
    
    public Double2() {
        this.x = 0;
        this.y = 0;
        this.flag = true;
    }
    
    public Double2(double x, double y) {
        this.x = x;
        this.y = y;
        this.flag = true;
    }
    
    public Double2(Double2 clone) {
        this.x = clone.x;
        this.y = clone.y;
        this.flag = true;
    }
    
    public Double2(double x, double y, boolean flag) {
        this.x = x;
        this.y = y;
        this.flag = flag;
    }
    
    public void set(Double2 clone) {
        this.x = clone.x;
        this.y = clone.y;
    }
    
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }
    
    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public double getY() {
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
