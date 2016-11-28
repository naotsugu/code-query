package code.core.query.impl;

import code.core.query.CriteriaContext;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import java.util.Objects;

/**
 * Implementation of {@link CriteriaContext}.
 *
 * @param <E>  entity type of root
 * @param <R>  result type of query
 * @author Naotsugu Kobayashi
 */
public class CriteriaContextImpl<E, R> implements CriteriaContext<E, R> {

    /** A root in the from clause. */
    public final Root<E> root;

    /** A top-level queries (or subqueries). */
    public final AbstractQuery<R> query;

    /** CriteriaBuilder. */
    public final CriteriaBuilder builder;


    /**
     * Create a new {@link CriteriaContext} implementation.
     *
     * @param root  root in the from clause
     * @param query  queries
     * @param builder  criteria builder
     */
    private CriteriaContextImpl(Root<E> root, AbstractQuery<R> query, CriteriaBuilder builder) {
        this.root = Objects.requireNonNull(root);
        this.query = Objects.requireNonNull(query);
        this.builder = Objects.requireNonNull(builder);
    }


    /**
     * Create a new {@link CriteriaContext} implementation.
     *
     * @param root  root in the from clause
     * @param query  queries
     * @param builder  criteria builder
     * @param <E>  entity type of root
     * @param <R>  result type of query
     * @return CriteriaContext
     */
    public static <E, R> CriteriaContext<E, R> of(Root<E> root, AbstractQuery<R> query, CriteriaBuilder builder) {
        return new CriteriaContextImpl<>(root, query, builder);
    }


    @Override
    public Root<E> root() {
        return root;
    }


    @Override
    public AbstractQuery<R> query() {
        return query;
    }


    @Override
    public CriteriaBuilder builder() {
        return builder;
    }

}
