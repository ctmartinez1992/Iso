package Util.Set;

import Util.SmartRandom;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlos
 */
public class ColorSet {
    
    public static BloodSet bloodSet = new BloodSet();
    
    protected SmartRandom random = new SmartRandom();
    protected List<Integer> colors = new ArrayList();
    
    public ColorSet() {
        
    }
    
    protected Integer returnRandomColor() {
        return 0xFF000000;
    }
    
}
