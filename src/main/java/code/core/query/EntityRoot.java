package code.core.query;

import code.core.domain.page.Sortable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Gets a root function.
 *
 * @param <E> type of root entity
 * @author Naotsugu Kobayashi
 */
@FunctionalInterface
public interface EntityRoot<E> {

    /**
     * Apply the given condition and gets root.
     *
     * @param sortable  {@link Sortable}
     * @param query  {@link CriteriaQuery}
     * @param cb  {@link CriteriaBuilder}
     * @return {@link Root}
     */
    Root<E> apply(Sortable<?> sortable, CriteriaQuery<?> query, CriteriaBuilder cb);

}
