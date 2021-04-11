package twodlife.life;

import twodlife.environments.Jungle;
import twodlife.utils.RandomnessOfLife;

import java.util.Map;


public class BugFactory {

    public static Bug makeDefaultBug(Jungle j) {
        return makeBug(Bug.getDefaultAttributes(), j);
    }

    public static Bug makeBug(Map<String, Object> attr, Jungle j) {

        if (RandomnessOfLife.random.nextInt() % 2 == 0) {
            return new MaleBug(j, attr);
        } else {
            return new FemaleBug(j, attr);
        }
    }
}
