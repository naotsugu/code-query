package code.core.query.impl;

import code.core.domain.page.Page;
import code.core.domain.page.PageQualifier;
import code.core.domain.page.Slice;
import code.core.domain.page.jpa.JpaPaths;
import code.core.query.*;

import javax.persistence.Tuple;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Objects;

public class QueryingImpl<E> implements Querying<E> {

    private final Class<E> rootClass;
    private final Specifications<E> specs;


    private QueryingImpl(Class<E> rootClass, Specifications<E> specs) {
        this.rootClass = Objects.requireNonNull(rootClass);
        this.specs = Objects.requireNonNull(specs);
    }


    private QueryingImpl(Class<E> rootClass) {
        this(rootClass, Specifications.empty());
    }


    public static <E> QueryingImpl<E> of(Class<E> rootClass) {
        return new QueryingImpl<>(rootClass);
    }


    @Override
    public Querying<E> filter(Specification<E> spec) {
        return new QueryingImpl<>(rootClass, specs.and(spec));
    }


    @Override
    public <R> Reading<E, R> map(Class<R> resultClass, Selector<E, R, ? extends Selection<R>> selector) {
        return ReadingImpl.of(resultClass, entityRoot(), selector);
    }

    @Override
    public <R> Reading<E, R> map(Selector<E, R, ? extends Selection<R>> selector) {
        @SuppressWarnings("unchecked")
        Class<R> resultClass = (Class<R>) Object.class;
        return ReadingImpl.of(resultClass, entityRoot(), selector);
    }

    @Override
    public Reading<E, Tuple> mapToTuple(Selector<E, Tuple, CompoundSelection<Tuple>> selector) {
        return ReadingImpl.of(Tuple.class, entityRoot(), selector);
    }

    @Override
    public Query<Slice<E>> toSlice(PageQualifier<?> pageQualifier) {
        return em -> ReadingImpl.of(rootClass, entityRoot(), Selector.root())
                .toSlice(pageQualifier).runWith(em);
    }


    @Override
    public Query<Page<E>> toPage(PageQualifier<?> pageQualifier) {
        return em -> ReadingImpl.of(rootClass, entityRoot(), Selector.root())
                .toPage(pageQualifier).runWith(em);
    }

    @Override
    public Query<Long> count() {
        return em -> ReadingImpl.of(Long.class, entityRoot(), Selector.count())
                .singleResult().runWith(em);
    }


    private EntityRoot<E> entityRoot() {
        return (sortable, query, cb) -> {
            final Root<E> root = query.from(rootClass);
            final Predicate predicate = specs.toPredicate(root, query, cb);
            if (predicate != null) {
                query.where(predicate);
            }
            if (sortable != null) {
                query.orderBy(sortable.getSortBy(JpaPaths.toOrder(root, cb)));
            }
            return root;
        };
    }

}