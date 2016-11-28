package code.core.query;

import javax.persistence.criteria.*;

/**
 * Construct function of select clause and orderby clause.
 *
 * @param <E> type of entity
 * @param <R> type of result
 * @param <S> type of apply result
 * @author Naotsugu Kobayashi
 */
@FunctionalInterface
public interface Selector<E, R, S> {

    /**
     * Apply the given context to.
     *
     * @param criteria {@link CriteriaContext}
     * @return result(Path, Expression ..)
     */
    S apply(CriteriaContext<E, R> criteria);


    /**
     * Apply the given context to.
     *
     * @param root {@link Root}
     * @param query  {@link AbstractQuery}
     * @param builder  {@link CriteriaBuilder}
     * @return result(Path, Expression ..)
     */
    default S apply(Root<E> root, AbstractQuery<R> query, CriteriaBuilder builder) {
        return apply(CriteriaContext.of(root, query, builder));
    }


    /**
     * Gets an empty selector.
     *
     * @param <E> type of entity
     * @param <R> type of result
     * @param <S> type of apply result
     * @return an empty selector
     */
    static <E, R, S> Selector<E, R, S> empty() {
        return criteria -> null;
    }


    /**
     * Create a count selector.
     * @param <E> type of root enitty
     * @return {@link Selector}
     */
    static <E> Selector<E, Long, Selection<Long>> count() {
        return criteria -> criteria.query().isDistinct()
                ? criteria.builder().countDistinct(criteria.root())
                : criteria.builder().count(criteria.root());
    }


    /**
     * Gets a root selector.
     *
     * @param <E> type of entity
     * @return root selector
     */
    static <E> Selector<E, E, Selection<E>> root() {
        return criteria -> criteria.root();
    }

}
