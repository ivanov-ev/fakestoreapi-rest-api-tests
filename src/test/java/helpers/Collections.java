package helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Collections {
    public static <T> boolean containsDuplicates(ArrayList<T> list) {
        Set<T> uniqueElements = new HashSet<>(list);
        return uniqueElements.size() != list.size();
    }
}
