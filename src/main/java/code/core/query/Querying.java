package code.core.query;

import code.core.domain.page.Page;
import code.core.domain.page.PageQualifier;
import code.core.domain.page.Slice;
import code.core.query.impl.QueryingImpl;

import javax.persistence.Tuple;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.Selection;

public interface Querying<E> {

    Querying<E> filter(Specification<E> spec);

    <R> Reading<E, R> select(Class<R> resultClass, Selector<E, R, ? extends Selection<R>> selector);

    <R> Reading<E, R> select(Selector<E, R, ? extends Selection<R>> selector);

    Reading<E, Tuple> mapToTuple(Selector<E, Tuple, CompoundSelection<Tuple>> selector);

    Query<Slice<E>> toSlice(PageQualifier<?> qualifier);

    Query<Page<E>> toPage(PageQualifier<?> qualifier);

    Query<Long> count();

    static <E> Querying<E> of(Class<E> entity) {
        return QueryingImpl.of(entity);
    }

}
