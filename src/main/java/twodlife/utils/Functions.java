package twodlife.utils;

import java.util.Random;

public class Functions {
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = RandomnessOfLife.random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

}
