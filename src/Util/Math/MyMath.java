package Util.Math;

import Util.SmartRandom;

/**
 *
 * @author carlos
 */
public class MyMath {
    
    private static SmartRandom random = new SmartRandom();
    
    public static boolean boxIntersect(Int2 box1, Int2 box1Measures, Int2 box2, Int2 box2Measures) {
        return (Math.abs(box1.getX() - box2.getX()) * 2 < (box1Measures.getX() + box2Measures.getX())) 
            && (Math.abs(box1.getY() - box2.getY()) * 2 < (box1Measures.getY() + box2Measures.getY()));
    }
}
