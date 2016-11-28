package code.core.query;

import javax.persistence.criteria.*;

/**
 * Specification.
 *
 * @param <E> type of root entity
 * @author Naotsugu Kobayashi
 */
@FunctionalInterface
public interface Specification<E> {

    /** A empty specification. */
    Specification EMPTY = criteria -> null;


    /**
     * Creates a 'where' clause for a query of the referenced entity in form of a {@link Predicate}.
     *
     * @param criteria  {@link CriteriaContext}
     * @return {@link Predicate}
     */
    Predicate toPredicate(CriteriaContext<E, ?> criteria);


    /**
     * Creates a 'where' clause for a query of the referenced entity in form of a {@link Predicate}.
     *
     * @param root  a root type in the from clause
     * @param query  AbstractQuery
     * @param builder  CriteriaBuilder
     * @return  {@link Predicate}
     */
    default Predicate toPredicate(Root<E> root, AbstractQuery<?> query, CriteriaBuilder builder) {
        return toPredicate(CriteriaContext.of(root, query, builder));
    }


    /**
     * Returns whether this specification is empty.
     *
     * @return whether this specification is empty
     */
    default boolean isEmpty() {
        return this == EMPTY;
    }


    /**
     * Returns a empty {@link Specifications}.
     */
    static <E> Specification<E> empty() {
        return EMPTY;
    }

}
