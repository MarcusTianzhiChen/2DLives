package twodlife.life;

import twodlife.LifeBase;
import twodlife.utils.RandomnessOfLife;

import java.util.Map;

public abstract class Movable extends LifeBase  {
    public int color = 0xFF0000;

    public int[] DIRECTIONS = new int[]{-1, 0, +1};

    public Movable(Map<String, Object> dna) {
        super(dna);
    }

    public int getRandomDirection() {
        return DIRECTIONS[RandomnessOfLife.random.nextInt(3)];
    }
    enum Action {
        MOVE,
        STAY
    }
    public abstract void move();
}
