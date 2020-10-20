package twodlife.life;

import org.jcodec.common.logging.Logger;
import twodlife.ObjectBase;
import twodlife.environments.Cell;
import twodlife.environments.Jungle;
import twodlife.environments.Point;
import twodlife.food.Food;
import twodlife.food.Grass;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Bug extends Food implements Movable {
    @Override
    public int getColor() {
        return 0xFF0000;
    }

    // todo move to utils
    private static final Random random = new Random();

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    int[] DIRECTIONS = new int[]{-1, 0, +1};

    int getRandomDirection() {
        return DIRECTIONS[random.nextInt(3)];
    }

    enum Action {
        MOVE,
        STAY
    }


    int energy;
    Jungle jungle;

    public Bug(Jungle jungle) {
        this.energy = 100;
        this.jungle = jungle;
    }

    public List<Food> searchForFood(Cell c) {

        List<Food> rt = new LinkedList<Food>();
        for (ObjectBase o : c.objects) {
            if (o instanceof Grass) {
                rt.add((Food) o);
            }
        }
        return rt;
    }

    @Override
    public void move() {

        Point currentLocation = jungle.whereAmI(this);

        Cell c = jungle.getCell(currentLocation); // todo: idea should be able to sense more
        List<Food> food = searchForFood(c);
        if (food.size() != 0) {
            Logger.info("food is being eaten!");
            if (consumeEnergy(1)) return;
            Food toBeEaten = food.get(0);

            energy += toBeEaten.fiber;
            jungle.dies(toBeEaten);
        } else {
            Action action = randomEnum(Action.class);
            if (action == Action.MOVE) {
                if (consumeEnergy(1)) return;
                //todo: move toward food
                //todo: p1 only to valid location
                jungle.moveTo(this,
                        new Point(currentLocation.x + getRandomDirection(),
                                currentLocation.y + getRandomDirection()));
            } else {
                // stay put
            }
        }
    }

    private boolean consumeEnergy(int i) {
        this.energy -= i;
        if (this.energy < 0) {
            jungle.dies(this);
            return true;
        }
        return false;
    }
}
