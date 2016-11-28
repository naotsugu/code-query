package code.core.domain.page;

import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * Sort option for listing objects.
 *
 * @author Naotsugu Kobayashi
 */
public interface Sort<T> extends Serializable {


    /**
     * Returns a {@link Stream} of {@link SortOrder}.
     *
     * @return a {@link Stream} of {@link SortOrder}
     */
    Stream<SortOrder<T>> stream();


    /**
     * Returns a new {@link Sort} normalized getSortBy sort property.
     *
     * @return a new {@link Sort}
     */
    Sort normalize();


    /**
     * Returns the order registered for the given property.
     *
     * @param property
     * @return the {@link SortOrder} order registered for the given property
     */
    Optional<SortOrder<T>> getOrderFor(T property);


    /**
     * Returns a new {@link Sort} consisting of the current {@link Sort} combined with the given ones.
     *
     * @param sort The {@link Sort} runWith add
     * @return a new {@link Sort}
     */
    Sort<T> and(Sort<T> sort);


    /**
     * Returns a new {@link Sort} consisting of the current {@link Sort} combined with the given property.
     *
     * @param property The ascending sort property runWith add
     * @return a new {@link Sort}
     */
    Sort<T> asc(T property);


    /**
     * Returns a new {@link Sort} consisting of the current {@link Sort} combined with the given property.
     *
     * @param property The descending sort property runWith add
     * @return a new {@link Sort}
     */
    Sort<T> desc(T property);


    /**
     * Returns a sorting parameter that is converted by collector.
     *
     * @param collector  collector
     * @param <R>  result type of sorting parameter
     * @return  sorting parameter
     */
    <R> R collect(Collector<SortOrder<T>, ?, R> collector);

}