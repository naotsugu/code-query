package code.core.query.impl;

import code.core.domain.page.*;
import code.core.query.EntityRoot;
import code.core.query.Query;
import code.core.query.Reading;
import code.core.query.Selector;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link Reading} implementation.
 *
 * @param <E> type of root entity
 * @param <R> type of query result
 * @author Naotsugu Kobayashi
 */
public class ReadingImpl<E, R> implements Reading<E, R> {

    /** A type of the query result. */
    private final Class<R> resultClass;

    /** A entity root. */
    private final EntityRoot<E> entityRoot;

    /** A selector for the from clause. */
    private final Selector<E, R, ? extends Selection<R>> selector;

    /** Selectors for the group by clause. */
    private final List<Selector<E, R, ? extends Expression<?>>> groups;


    /**
     * Create a new {@link Reading} implementation.
     *
     * @param resultClass  type of the query result
     * @param entityRoot  entity root
     * @param selector  selector for the from clause
     * @param groups selectors for the group by clause
     */
    private ReadingImpl(Class<R> resultClass,
                        EntityRoot<E> entityRoot,
                        Selector<E, R, ? extends Selection<R>> selector,
                        List<Selector<E, R, ? extends Expression<?>>> groups) {

        this.resultClass = resultClass;
        this.entityRoot = entityRoot;
        this.selector = selector;
        this.groups = groups;

    }


    /**
     * Create a new {@link Reading} implementation.
     *
     * @param resultClass  type of the query result
     * @param entityRoot  entity root
     * @param selector  selector for the from clause
     * @param <E> type of root entity
     * @param <R> type of query result
     * @return {@link Reading} implementation instance
     */
    public static <E, R> Reading<E, R> of(Class<R> resultClass,
                                          EntityRoot<E> entityRoot,
                                          Selector<E, R, ? extends Selection<R>> selector) {
        return new ReadingImpl<>(resultClass, entityRoot, selector, Collections.emptyList());
    }


    @Override
    public Reading<E, R> groupBy(Selector<E, R, ? extends Expression<?>> group) {
        List<Selector<E, R, ? extends Expression<?>>> newGroups = new ArrayList<>(groups);
        newGroups.add(group);
        return new ReadingImpl<>(resultClass, entityRoot, selector, newGroups);
    }


    @Override
    public Reading<E, R> groupBy(List<Selector<E, R, ? extends Expression<?>>> groups) {
        return new ReadingImpl<>(resultClass, entityRoot, selector, groups);
    }


    @Override
    public Query<Slice<R>> toSlice(PageQualifier<?> qualifier) {
        return em -> {

            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<R> query = cb.createQuery(resultClass);

            final Root<E> root = entityRoot.apply(qualifier, query, cb);
            query.select(selector.apply(root, query, cb));
            query.groupBy(groups.stream().map(g->g.apply(root, query, cb)).collect(Collectors.toList()));

            TypedQuery<R> typedQuery = em.createQuery(query);
            typedQuery.setFirstResult(qualifier.getOffset());
            typedQuery.setMaxResults(qualifier.getSize() + 1);

            return new BasicSlice<>(typedQuery.getResultList(), qualifier);
        };
    }


    @Override
    public Query<Page<R>> toPage(PageQualifier<?> pageQualifier) {
        return em -> {

            Long total = count().runWith(em);
            if (total <= pageQualifier.getOffset()) {
                return new BasicPage<>(Collections.emptyList(), pageQualifier, total);
            }

            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<R> query = cb.createQuery(resultClass);

            final Root<E> root = entityRoot.apply(pageQualifier, query, cb);
            query.select(selector.apply(root, query, cb));
            query.groupBy(groups.stream().map(g->g.apply(root, query, cb)).collect(Collectors.toList()));

            TypedQuery<R> typedQuery = em.createQuery(query);
            typedQuery.setFirstResult(pageQualifier.getOffset());
            typedQuery.setMaxResults(pageQualifier.getSize());

            return new BasicPage<>(typedQuery.getResultList(), pageQualifier, total);
        };
    }


    @Override
    public Query<Long> count() {
        return em -> ReadingImpl.of(Long.class, entityRoot, Selector.count())
                .singleResult().runWith(em);
    }


    @Override
    public Query<R> singleResult() {
        return em -> {
            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<R> query = cb.createQuery(resultClass);

            final Root<E> root = entityRoot.apply(null, query, cb);
            query.select(selector.apply(root, query, cb));
            query.groupBy(groups.stream().map(g->g.apply(root, query, cb)).collect(Collectors.toList()));

            return em.createQuery(query).getSingleResult();
        };
    }

}
