package code.core.domain.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Companion object of {@link Sort}.
 *
 * @author Naotsugu Kobayashi
 */
public interface Sorts {

    /**
     * Create a new {@link Sort} from list of {@link SortOrder}.
     *
     * @param orders  list of {@link SortOrder}
     * @param <T>  type of sort properties
     * @return  new instance of {@link Sort}
     */
    static <T> Sort<T> of(List<SortOrder<T>> orders) {
        return new BasicSort<>(orders);
    }


    /**
     * Create a new {@link Sort} as ascending direction.
     * All properties is treated as ascending.
     *
     * @param properties  sort properties
     * @param <T>  type of sort properties
     * @return  new instance of {@link Sort}
     */
    static <T> Sort<T> ascOf(T... properties) {
        return new BasicSort<>(
                Arrays.stream(properties)
                      .map(p -> new BasicSortOrder<>(p, Direction.ASC))
                      .collect(Collectors.toList()));
    }


    /**
     * Create a new {@link Sort} as descending direction.
     * All properties is treated as descending.
     *
     * @param properties  sort properties
     * @param <T>  type of sort properties
     * @return  new instance of {@link Sort}
     */
    static <T> Sort<T> descOf(T... properties) {
        return new BasicSort<>(
                Arrays.stream(properties)
                      .map(p -> new BasicSortOrder<>(p, Direction.DESC))
                      .collect(Collectors.toList()));
    }


    /**
     * Create a new {@link Sort} as ascending direction.
     *
     * @param property  sort properties
     * @param <T>  type of sort properties
     * @return  new instance of {@link Sort}
     */
    static <T> Sort<T> asc(T property) {
        return new BasicSort<>(Arrays.asList(new BasicSortOrder<>(property, Direction.ASC)));
    }


    /**
     * Create a new {@link Sort} as descending direction.
     *
     * @param property  sort properties
     * @param <T>  type of sort properties
     * @return  new instance of {@link Sort}
     */
    static <T> Sort<T> desc(T property) {
        return new BasicSort<>(Arrays.asList(new BasicSortOrder<>(property, Direction.DESC)));
    }


}
