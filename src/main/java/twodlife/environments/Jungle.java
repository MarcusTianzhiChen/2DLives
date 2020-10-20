package twodlife.environments;

import twodlife.ObjectBase;
import twodlife.life.Movable;
import twodlife.utils.RandomnessOfLife;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class Jungle {

    int x, y;
    Cell[][] internalJungle;
    Map<ObjectBase, Point> reverseMap = new HashMap<>();

    public Jungle(int x, int y) {
        this.x = x;
        this.y = y;
        this.internalJungle = new Cell[x][y];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                this.internalJungle[i][j] = new Cell(new ArrayList<>());
            }
        }
    }

    public Map<Point, Cell> smellSurroundings(int distance, Point p) {
        return null;
    } // todo

    public Point lookupReverseMap(ObjectBase o) {
        return reverseMap.get(o);
    }

    public void addToReverseMap(ObjectBase o, Point p) {
        reverseMap.put(o, p);
    }

    public Point deleteFromReverseMap(ObjectBase o) {
        return reverseMap.remove(o);
    }

    public void moveTo(Movable bug, Point point) {
        if (point.x < x && point.x >= 0 && point.y < y && point.y >= 0) {
            deleteFromReverseMap(bug);
            addToReverseMap(bug, point);
        } else {
            // todo: do something if it is not valid
        }

    }

    public Point whereAmI(ObjectBase o) {
        return lookupReverseMap(o);
    }

    public void create(ObjectBase o, Point p) {
        internalJungle[p.x][p.y].objects.add(o);
        addToReverseMap(o, p);
    }

    public Point getRandomPoint() {
        return new Point(RandomnessOfLife.random.nextInt(x), RandomnessOfLife.random.nextInt(y));
    }

    public void create(ObjectBase o) {
        Point p = getRandomPoint();
        internalJungle[p.x][p.y].objects.add(o);
        addToReverseMap(o, p);
    }

    public void dies(ObjectBase o) {
        Point p = lookupReverseMap(o);
        internalJungle[p.x][p.y].objects.remove(o);
        deleteFromReverseMap(o);
    }

    public BufferedImage toImage(String name) {
        BufferedImage bufferedImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);

        Set<ObjectBase> objects = reverseMap.keySet();
        for (ObjectBase o : objects) {
            Point p = reverseMap.get(o);
            try {
                bufferedImage.setRGB(p.x, p.y, o.getColor()); // todo: each object should have its color
            } catch (Exception e) {
                // do nothing
            }
        }
        return bufferedImage;
    }

    public List<Movable> getAllMovables() {
        List<Movable> rt = new LinkedList<>();
        for (ObjectBase o : reverseMap.keySet()) {
            if (o instanceof Movable) {
                rt.add((Movable) o);
            }
        }
        return rt;
    }

    public Cell getCell(Point p) {
        // todo: make the returned read only;
        return internalJungle[p.x][p.y];
    }
}
