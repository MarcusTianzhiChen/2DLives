package twodlife.life;

import twodlife.LifeBase;
import twodlife.environments.Cell;
import twodlife.environments.Jungle;
import twodlife.environments.Point;
import twodlife.food.Food;
import twodlife.food.Grass;

import java.util.*;
import java.util.function.Predicate;


// todo: idea: everything is just life with a a bunch of attributes, which is more true to reality.
public abstract class Bug extends Movable implements Food {
    static public double DEFAULT_SMELL_DISTANCE = 5.0;
    static Map<String, Object> getDefaultAttributes() {
        Map<String, Object> attr = new HashMap<String, Object>();
        attr.put(Attribute.SMELL_DISTANCE, DEFAULT_SMELL_DISTANCE);
        return attr;
    }

    double energy;
    Jungle jungle;

    public double getSmellDistance() {
        return smellDistance;
    }

    double smellDistance;

    public double getEnergyConsumption() {
        return energyConsumption;
    }

    double energyConsumption;
    double lifeSpan;
    double age;

    public Bug(Jungle jungle, Map<String, Object> attr) {
        super(attr);
        this.energy = 40;
        this.lifeSpan = 300;
        this.jungle = jungle;
        this.smellDistance = (double) attr.get(Attribute.SMELL_DISTANCE);
        this.energyConsumption = 2.0 * (smellDistance / DEFAULT_SMELL_DISTANCE) ;
        this.age = 0;
    }

    public List<Food> searchForFood(Cell c) {

        List<Food> rt = new LinkedList<Food>();
        for (LifeBase o : c.objects) {
            if (o instanceof Grass) {
                rt.add((Food) o);
            }
        }
        return rt;
    }

    public abstract Predicate<? super LifeBase> getSearchPredicate();

    @Override
    public void move() {

        if (age >= lifeSpan) {
            jungle.dies(this);
        }

        Point currentLocation = jungle.whereAmI(this);
        if (currentLocation == null) return; // dead alreay;

        Cell c = jungle.getCell(currentLocation); // todo: idea should be able to sense more
        List<Food> food = searchForFood(c);
        if (food.size() != 0) {
//            Logger.info("food is being eaten!");
            if (consumeEnergy(energyConsumption)) return;
            Food toBeEaten = food.get(0);

            energy += toBeEaten.getCarbs();
            jungle.dies((LifeBase) toBeEaten);
        } else {
            Action action = Action.MOVE; // todo: change to sometimes stay to preserve engergy
            if (action == Action.MOVE) {
                if (consumeEnergy(energyConsumption)) return;
                //todo: p1 only to valid location
                // todo: smell distance should be a trait & member variable
                Map<Point, Cell> surroundings = jungle.smellSurroundings(smellDistance, currentLocation);
                Point foodLocation = filterSurrounding(surroundings, getSearchPredicate());
                if (foodLocation != null) {
                    Point next = getNextPointToward(currentLocation, foodLocation);
                    jungle.moveTo(this, next);
                } else {
//                    Logger.info("random move");
                    jungle.moveTo(this,
                            new Point(
                                    currentLocation.x + getRandomDirection(),
                                    currentLocation.y + getRandomDirection()
                            ));
                }

            } else {
                // stay put
            }
        }
        age += 1;
    }

    Point getSurroundingFoodLocation(Map<Point, Cell> surroundings) {

        // todo: perhaps it should go to the nearest

        for (Map.Entry<Point, Cell> entry : surroundings.entrySet()) {
            if (entry.getValue().objects.stream().filter(o -> o instanceof Grass).toArray().length > 0) {
                return entry.getKey();
            }
        }
        return null;
    }

    Point filterSurrounding(Map<Point, Cell> surroundings, Predicate<? super LifeBase> predicate) {
        for (Map.Entry<Point, Cell> entry : surroundings.entrySet()) {
            if (entry.getValue().objects.stream().filter(predicate).toArray().length > 0) {
                return entry.getKey();
            }
        }

        return null;
    }

    Point getNextPointToward(Point start, Point end) {
        Point next = null;
        int max = Integer.MAX_VALUE;

        for (int i : DIRECTIONS) {
            for (int j : DIRECTIONS) {
                Point candidate = new Point(start.x + i, start.y + j);
                int distance = distance(candidate, end);
                if (distance < max) {
                    next = candidate;
                    max = distance;
                }
            }
        }
        return next;
    }

    int distance(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    boolean consumeEnergy(double i) {
        this.energy -= i;
        if (this.energy < 0) {
            jungle.dies(this);
            return true;
        }
        return false;
    }

    @Override
    public int getCarbs() {
        return 0;
    }

    @Override
    public int getProtein() {
        return 0;
    }

    @Override
    public int getFat() {
        return 0;
    }
}
