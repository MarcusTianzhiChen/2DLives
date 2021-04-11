package twodlife.environments;

import twodlife.LifeBase;
import twodlife.food.Food;
import twodlife.food.Grass;
import twodlife.life.Bug;
import twodlife.life.FemaleBug;
import twodlife.life.MaleBug;
import twodlife.life.Movable;
import twodlife.utils.RandomnessOfLife;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class Jungle {

    int x, y, time;
    Cell[][] internalJungle;
    Map<LifeBase, Point> reverseMap = new HashMap<>();

    public Jungle(int x, int y) {
        this.time = 0;
        this.x = x;
        this.y = y;
        this.internalJungle = new Cell[x][y];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                this.internalJungle[i][j] = new Cell(new ArrayList<>());
            }
        }
    }

    public int time() {
        return time;
    }

    public Map<Point, Cell> smellSurroundings(double distance, Point p) {

        Map<Point, Cell> smelled = new HashMap<>();

        int intDistance = (int) distance;

        for (int i = p.x - intDistance; i <= p.x + distance; i++) {
            for (int j = p.y - intDistance; j <= p.y + distance; j++) {
                Point surroundingPoint = new Point(i, j);
                if (inJungle(new Point(i, j))) {
                    Cell c = internalJungle[surroundingPoint.x][surroundingPoint.y];
                    smelled.put(surroundingPoint, c);
                }
            }
        }
        return smelled;
    }

    boolean inJungle(Point point) {
        return point.x < x && point.x >= 0 && point.y < y && point.y >= 0;
    }

    public Point lookupReverseMap(LifeBase o) {
        return reverseMap.get(o);
    }

    public void addToReverseMap(LifeBase o, Point p) {
        reverseMap.put(o, p);
    }

    public Point deleteFromReverseMap(LifeBase o) {
        return reverseMap.remove(o);
    }

    public void moveTo(Movable creature, Point point) {
        if (inJungle(point)) {
            Point currentLocation = lookupReverseMap(creature);
            deleteFromReverseMap(creature);
            internalJungle[currentLocation.x][currentLocation.y].objects.remove(creature);

            addToReverseMap(creature, point);
            internalJungle[point.x][point.y].objects.add(creature);
        } else {
            // todo: do something if it is not valid
        }

    }

    public Point whereAmI(LifeBase o) {
        return lookupReverseMap(o);
    }

    public void create(LifeBase o, Point p) {
        internalJungle[p.x][p.y].objects.add(o);
        addToReverseMap(o, p);
    }

    public Point getRandomPoint() {
        return new Point(RandomnessOfLife.random.nextInt(x), RandomnessOfLife.random.nextInt(y));
    }

    public void create(LifeBase o) {
        Point p = getRandomPoint();
        create(o, p);
    }

    public void dies(LifeBase o) {
        Point p = lookupReverseMap(o);
        if (p != null) {
            internalJungle[p.x][p.y].objects.remove(o);
            deleteFromReverseMap(o);
        } else {
            // already dead;
        }
    }

    public BufferedImage toImage() {
        BufferedImage bufferedImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);

        Set<LifeBase> objects = reverseMap.keySet();
        for (LifeBase o : objects) {
            Point p = reverseMap.get(o);
            try {
                bufferedImage.setRGB(p.x, p.y, o.getColor()); // todo: each object should have its color
            } catch (Exception e) {
                // do nothing
            }
        }
        return bufferedImage;
    }

    public List<Dot> toPlottable() {
        List<Dot> dots = new ArrayList<>();
        Set<LifeBase> objects = reverseMap.keySet();
        for (LifeBase o : objects) {
            Point p = reverseMap.get(o);
            dots.add(new Dot(p.x, p.y, o.getColor()));
        }
        return dots;
    }

    public Map<String, Integer> getMetrics() {
        Map<String, Integer> rt = new HashMap<>();
        Set<LifeBase> objects = reverseMap.keySet();

        int maleBugCount = 0;
        int femaleBugCount = 0;
        int foodCount = 0;

        for (LifeBase o : objects) {

            if (o instanceof FemaleBug) {
                femaleBugCount++;
            } else if (o instanceof MaleBug) {
                maleBugCount++;
            } else if (o instanceof Food) {
                foodCount++;
            }

        }

        rt.put("maleBugCount", maleBugCount);
        rt.put("femaleBugCount", femaleBugCount);
        rt.put("foodCount", foodCount);

        return rt;
    }

    public List<Map<String, Double>> getAttributes() {


        List<Map<String, Double>> samples = new ArrayList<>();
        int MAX_SAMPLE_SIZE = 30;
        int count = 0;
        List<Object> objects = Arrays.asList(reverseMap.keySet().toArray());
        Collections.shuffle(Arrays.asList(reverseMap.keySet().toArray()));
        String attributes = "";
        for (Object o : objects) {
            if (o instanceof Bug) {

                Map<String, Double> attributesOfOne = new HashMap<>();
                count++;
                Bug b = (Bug) o;
                attributesOfOne.put("energyConsumption", b.getEnergyConsumption());
                attributesOfOne.put("smellDistance", b.getSmellDistance());
                samples.add(attributesOfOne);
//                attributes += "" + time + ", " + b.getEnergyConsumption() + ", " + b.getSmellDistance() + ",\n";
            }
            if (count >= MAX_SAMPLE_SIZE) {
                break;
            }
        }

        return samples;
    }

    public List<Movable> getAllMovables() {
        List<Movable> rt = new LinkedList<>();
        for (LifeBase o : reverseMap.keySet()) {
            if (o instanceof Movable) {
                rt.add((Movable) o);
            }
        }
        return rt;
    }

    public void move() {
        List<Movable> allMovables = getAllMovables();
        for (Movable m : allMovables) {
            m.move();
        }
        time++;
    }

    public Cell getCell(Point p) {
        return internalJungle[p.x][p.y];
    }
}
