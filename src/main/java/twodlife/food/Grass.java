package twodlife.food;

import twodlife.LifeBase;

public class Grass extends LifeBase implements Food {

    public Grass() {
        super(null);
    }

    @Override
    public int getCarbs() {
        return 20;
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
