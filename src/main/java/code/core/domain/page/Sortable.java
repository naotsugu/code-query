package code.core.domain.page;

import java.io.Serializable;
import java.util.stream.Collector;

/**
 * Sortable.
 *
 * @param <T> type of sort property
 * @author Naotsugu Kobayashi
 */
public interface Sortable<T> extends Serializable {

    /**
     * Returns the sorting parameters of this {@link Pageable}.
     *
     * @return the sorting parameters of this {@link Pageable}
     */
    Sort<T> getSort();


    /**
     * Returns tha sorting parameters that is converted by collector.
     *
     * @param collector  Collector
     * @param <R>  result type of sorting parameter
     * @return  sorting parameters
     */
    <R> R getSortBy(Collector<SortOrder<T>, ?, R> collector);

}
