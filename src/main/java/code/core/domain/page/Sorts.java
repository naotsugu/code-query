package code.core.domain.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Companion object of {@link Sort}.
 *
 * @author naotsugu
 */
public interface Sorts {

    static <T> Sort<T> of(List<SortOrder<T>> orders) {
        return new BasicSort(orders);
    }


    static <T> Sort<T> ascOf(T... properties) {
        return new BasicSort<>(
                Arrays.asList(properties).stream()
                        .map(p -> new BasicSortOrder<>(p, Direction.ASC))
                        .collect(Collectors.toList()));
    }


    static <T> Sort<T> descOf(T... properties) {
        return new BasicSort<>(
                Arrays.asList(properties).stream()
                        .map(p -> new BasicSortOrder<>(p, Direction.DESC))
                        .collect(Collectors.toList()));
    }



    static <T> Sort<T> asc(T property) {
        return new BasicSort<>(Arrays.asList(new BasicSortOrder<>(property, Direction.ASC)));
    }


    static <T> Sort<T> desc(T property) {
        return new BasicSort<>(Arrays.asList(new BasicSortOrder<>(property, Direction.DESC)));
    }


}
