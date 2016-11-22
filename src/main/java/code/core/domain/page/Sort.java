package code.core.domain.page;

import javax.persistence.criteria.Order;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * Sort option for listing objects.
 * @author naotsugu
 */
public interface Sort<T> extends Serializable {


    /**
     * Returns a {@link Stream} of {@link SortOrder}.
     *
     * @return a {@link Stream} of {@link SortOrder}
     */
    Stream<SortOrder<T>> stream();


    /**
     * Returns a new {@link Sort} noralized getSortBy sort property.
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



    <R> R collect(Collector<SortOrder<T>, ?, R> collector);

}