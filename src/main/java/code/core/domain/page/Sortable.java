package code.core.domain.page;

import java.io.Serializable;
import java.util.stream.Collector;

/**
 * Sortable.
 * @author naotsugu
 */
public interface Sortable<T> extends Serializable {

    /**
     * Returns the sorting parameters of this {@link Pageable}.
     * @return the sorting parameters of this {@link Pageable}
     */
    Sort<T> getSort();


    <R> R getSortBy(Collector<SortOrder<T>, ?, R> collector);
}
