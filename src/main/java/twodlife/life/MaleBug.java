package twodlife.life;

import org.jcodec.common.logging.Logger;
import twodlife.LifeBase;
import twodlife.environments.Cell;
import twodlife.environments.Jungle;
import twodlife.environments.Point;
import twodlife.food.Food;
import twodlife.food.Grass;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class MaleBug extends Bug implements Male {


    public MaleBug(Jungle jungle, Map<String, Object> attr) {
        super(jungle, attr);
    }

    @Override
    public Predicate<? super LifeBase> getSearchPredicate() {
        return o -> o instanceof FemaleBug || o instanceof Grass;
    }

    public int getColor() {
        return 0x00FF00;
    }

    public void move() {
        // todo: need to simplify this;

        if (age > 40) {
            Point currentLocation = jungle.whereAmI(this);
            Cell c = jungle.getCell(currentLocation);
            List<FemaleBug> partners = searchForFemaleBug(c);
            if (partners.size() > 0) {
                consumeEnergy( 20 );
                penetrate(partners.get(0));
            } else {
                super.move();
            }
        } else {
            super.move();
        }


    }

    public List<FemaleBug> searchForFemaleBug(Cell c) {

        List<FemaleBug> rt = new LinkedList<FemaleBug>();
        for (LifeBase o : c.objects) {
            if (o instanceof FemaleBug) {
                FemaleBug fb = (FemaleBug) o;
                if (fb.age > 40) {
                    rt.add(fb);
                }
            }
        }
        return rt;
    }

    @Override
    public void penetrate(Female female) {
        female.receive(this.dna);
    }

}

