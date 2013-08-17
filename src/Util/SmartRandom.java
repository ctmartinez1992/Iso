package Util;

import java.util.Random;

/**
 *
 * @author carlos
 */
public class SmartRandom {

    private Random random;

    public SmartRandom() {
        this.random = new Random();
    }

    public int randomInt(int upper) {
        return random.nextInt(upper);
    }

    public int randomInt(int lower, int upper) {
        return lower + random.nextInt(upper - lower);
    }

    public float randomFloat(float upper) {
        return random.nextFloat() * upper;
    }

    public float randomFloat(float lower, float upper) {
        return lower + random.nextFloat() * (upper - lower);
    }

    public double randomDouble(double upper) {
        return random.nextDouble() * upper;
    }

    public double randomDouble(double lower, double upper) {
        return lower + random.nextFloat() * (upper - lower);
    }

    public long randomLong() {
        return random.nextLong();
    }
    
    public boolean randomBoolean() {
        return random.nextBoolean();
    }
}
