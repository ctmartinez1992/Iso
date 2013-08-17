package Util.Set;

/**
 *
 * @author carlos
 */
public class BloodSet extends ColorSet {
    
    public BloodSet() {        
        colors.add(0xFFFF0000);
        colors.add(0xFFFD0000);
        colors.add(0xFFFB0000);
        colors.add(0xFFEE0000);
        colors.add(0xFFEB0000);
        colors.add(0xFFE20000);
        colors.add(0xFFDD0000);
        colors.add(0xFFDA0000);
        colors.add(0xFFD10000);
        colors.add(0xFFCB0000);
        colors.add(0xFFC30000);
        colors.add(0xFFBD0000);
        colors.add(0xFFB60000);
        colors.add(0xFFAC0000);
        colors.add(0xFFA30000);
        colors.add(0xFF9A0000);
        colors.add(0xFF900000);
        colors.add(0xFF800000);
        colors.add(0xFF750000);
        colors.add(0xFF6E0000);
    }    
    
    @Override
    public Integer returnRandomColor() {
        return colors.get(random.randomInt(0, 20));
    }
    
}
