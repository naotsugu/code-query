package code.core.query;

import code.core.query.impl.CriteriaContextImpl;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

/**
 * Criteria context that used by .
 *
 * @param <E> type of entity
 * @param <R> result type
 * @author Naotsugu Kobayashi
 */
public interface CriteriaContext<E, R> extends
        EqualSupport, LikeSupport, ConstructionSupport, AggregateSupport,
        PathSupport<E> {

    /**
     * Gets a CriteriaBuilder.
     *
     * @return Root
     */
    Root<E> root();


    /**
     * Gets an AbstractQuery.
     *
     * @return Query
     */
    AbstractQuery<R> query();


    /**
     * Gets a CriteriaBuilder.
     *
     * @return CriteriaBuilder
     */
    CriteriaBuilder builder();


    /**
     * Create a new {@link CriteriaContext} implementation.
     *
     * @param root  a root in the from clause
     * @param query  AbstractQuery
     * @param builder  CriteriaBuilder
     * @param <E>  type of root entity
     * @param <R>  type of query result
     * @return a new {@link CriteriaContext}
     */
    static <E, R> CriteriaContext<E, R> of(Root<E> root, AbstractQuery<R> query, CriteriaBuilder builder) {
        return CriteriaContextImpl.of(root, query, builder);
    }


    /**
     * Create a new {@link Subquerying} implementation.
     *
     * @param entityClass  root entity in subquery
     * @param <U>  result of subquerying
     * @return a new {@link Subquerying}
     */
    default <U> Subquerying<E, U> subqueryOf(Class<U> entityClass) {
        return Subquerying.of(this, entityClass);
    }

}
