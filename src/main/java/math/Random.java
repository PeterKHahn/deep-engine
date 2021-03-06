package math;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Random {

    private Random() {
    }

    public static <T> T choice(Collection<T> collection) {
        List<T> list = new ArrayList<>(collection);
        return choice(list);

    }

    public static <T> T choiceRemove(Collection<T> collection) {
        List<T> list = new ArrayList<>(collection);
        T element = choice(list);
        collection.remove(element);
        return element;
    }

    public static <T> T choice(List<T> list) {
        int random = randInt(list.size() - 1);

        return list.get(random);
    }


    public static <T> T choiceRemove(List<T> list) {
        int random = randInt(list.size() - 1);
        return list.remove(random);
    }

    public static <T> T choice(List<T> list1, List<T> list2) {
        int size = list1.size() + list2.size();


        int index = Random.randInt(size - 1);
        if (index < list1.size()) {
            return list1.get(index);
        } else {
            int newIndex = index - list1.size();
            return list2.get(newIndex);
        }
    }

    public static int randInt(int range) {
        return (int) (Math.random() * range);

    }

}
