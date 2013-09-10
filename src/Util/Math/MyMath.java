package Util.Math;

/**
 *
 * @author carlos
 */
public class MyMath {
    
    /**
     * Checks for a minimum and a maximum and maintains the given value in 
     * those parameters.
     *
     * @param value the value to compare.
     * @param min the minimum value acceptable.
     * @param max the maximum value acceptable.
     * @return a value between the minimum and the maximum.
     */
    public static int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }
    
    /**
     * Checks for a minimum and a maximum and maintains the given value in 
     * those parameters.
     *
     * @param value the value to compare.
     * @param min the minimum value acceptable.
     * @param max the maximum value acceptable.
     * @return a value between the minimum and the maximum.
     */
    public static float clamp(float value, float min, float max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }
    
    /**
     * Checks for a minimum and a maximum and maintains the given value in 
     * those parameters.
     *
     * @param value the value to compare.
     * @param min the minimum value acceptable.
     * @param max the maximum value acceptable.
     * @return a value between the minimum and the maximum.
     */
    public static double clamp(double value, double min, double max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }
    
    /**
     * Checks for a minimum and a maximum and maintains the given value in 
     * those parameters.
     *
     * @param value the value to compare.
     * @param min the minimum value acceptable.
     * @param max the maximum value acceptable.
     * @return true if the value is between the minimum and the maximum.
     */
    public static boolean interval(int value, int min, int max) {
        if (value < min) {
            return false;
        } else if (value > max) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Checks for a minimum and a maximum and maintains the given value in 
     * those parameters.
     *
     * @param value the value to compare.
     * @param min the minimum value acceptable.
     * @param max the maximum value acceptable.
     * @return true if the value is between the minimum and the maximum.
     */
    public static boolean interval(float value, float min, float max) {
        if (value < min) {
            return false;
        } else if (value > max) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Checks for a minimum and a maximum and maintains the given value in 
     * those parameters.
     *
     * @param value the value to compare.
     * @param min the minimum value acceptable.
     * @param max the maximum value acceptable.
     * @return true if the value is between the minimum and the maximum.
     */
    public static boolean interval(double value, double min, double max) {
        if (value < min) {
            return false;
        } else if (value > max) {
            return false;
        } else {
            return true;
        }
    }
}
