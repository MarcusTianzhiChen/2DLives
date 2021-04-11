package twodlife.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class DNA {

    Map<String, Object> attributes; // the type of attributes should be either enum or double / int

    public DNA(Map<String, Object> attr) {
        this.attributes = attr;
    }


    enum NEW_ATTR_TYPE {
//        PICK_ONE_SIDE,
        AVERAGE,
        MUTATION,
//        PICK_HIDDEN
    }


    public static Map<String, Object> mixAttr(Map<String, Object> male, Map<String, Object> female) {
        // todo: most important!

        Set<String> attrNames = male.keySet();
//        attrNames.addAll(female.keySet()); // todo: may not be necessary if we assume they always have the same attributes

        Map<String, Object> rt = new HashMap<String, Object>();
        for (String name : attrNames) {
            Object maleAttr = male.get(name);
            Object femaleAttr = female.get(name);

            // todo: support other types as well
            Object newAttr;

            NEW_ATTR_TYPE t = Functions.randomEnum(NEW_ATTR_TYPE.class);

            if (maleAttr instanceof Double) {
                if (t == NEW_ATTR_TYPE.AVERAGE) {
                    newAttr = (((Double) maleAttr + (Double) femaleAttr)) / 2;
                } else if (t == NEW_ATTR_TYPE.MUTATION) {

                    Double avg = (((Double) maleAttr + (Double) femaleAttr)) / 2;
                    // between 0 ~ 2;
                    newAttr = (avg * RandomnessOfLife.random.nextDouble() * 2);
                } else {
                    throw new Error("Type of attribute not supported");
                }
            } else {
                throw new Error("Type of attribute not supported");
            }
            rt.put(name, newAttr);
        }
        return rt;
    }


}
