package Util.Math;

/**
 *
 * @author carlos
 */
public class Int3 {
    
    private int x;
    private int y;
    private int z;
    private boolean flag;
    
    public Int3() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.flag = true;
    }
    
    public Int3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.flag = true;
    }
    
    public Int3(int x, int y, int z, boolean flag) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.flag = flag;
    }

    public void set(int x, int y, int z) {
        setX(x);
        setY(y);
        setZ(z);
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

    public void setZ(int z) {
        this.z = z;
    }
    
    public int getZ() {
        return z;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }
    
    public void print() {
        System.out.println("X: " + x + " - Y: " + y + " - Z: " + z + " - Flag: " + flag);
    }
}
