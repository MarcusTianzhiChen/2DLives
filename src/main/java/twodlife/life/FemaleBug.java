package twodlife.life;

import org.jcodec.common.logging.Logger;
import twodlife.LifeBase;
import twodlife.environments.Jungle;
import twodlife.environments.Point;
import twodlife.food.Grass;
import twodlife.utils.DNA;

import java.util.Map;
import java.util.function.Predicate;

public class FemaleBug extends Bug implements Female {


    int OFF_SPRING = 3; // this can be in dna as well;

    public FemaleBug(Jungle jungle, Map<String, Object> attr) {
        super(jungle, attr);
    }


    @Override
    public Predicate<? super LifeBase> getSearchPredicate() {
        return o -> o instanceof Grass;
    }

    @Override
    public int getColor() {
        return 0xFF0000;
    }

    @Override
    public void receive(Map<String, Object> dna) {
//        Logger.info("reproducing!");
        for (int i = 0; i < OFF_SPRING; i++) {
            if(consumeEnergy(20)) return;
            Point p = jungle.whereAmI(this);
            Map<String, Object> offspringDna = DNA.mixAttr(dna, this.dna);
            jungle.create(BugFactory.makeBug(offspringDna, this.jungle), p);
        }
    }
}
