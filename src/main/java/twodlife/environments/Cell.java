package twodlife.environments;

import twodlife.LifeBase;

import java.util.ArrayList;

public class Cell implements Cloneable {

    public ArrayList<LifeBase> objects;

    public Cell(ArrayList<LifeBase> objects) {
        this.objects = objects;
    }

}
