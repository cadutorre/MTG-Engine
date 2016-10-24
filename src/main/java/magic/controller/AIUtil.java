package magic.controller;

import java.util.List;

public class AIUtil {
    public static <T> T randomIndex(List<T> list) {
        return list.get((int)(Math.random()*list.size()));
    }
}
