package twodlife;

import java.util.HashMap;
import java.util.Map;

public class LifeBase {

    public Map<String, Object> dna = new HashMap<>();

    public LifeBase(Map<String, Object> dna){
        this.dna = dna;
    }


    Map<String, Object> getDna() {
        return dna;
    }

    public int getColor() {
        return 0xFFFFFF;
    }
}
