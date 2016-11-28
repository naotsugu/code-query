package code.core.query;

import code.core.domain.page.Page;
import code.core.domain.page.PageQualifier;
import code.core.domain.page.Slice;
import code.core.query.impl.QueryingImpl;

import javax.persistence.Tuple;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.Selection;

/**
 *
 * @param <E>
 * @author Naotsugu Kobayashi
 */
public interface Querying<E> {

    /**
     * Filtering by the given specification.
     *
     * @param spec  specification
     * @return {@link Querying}
     */
    Querying<E> filter(Specification<E> spec);


    /**
     * Specify projection via given selector.
     *
     * @param resultClass result class
     * @param selector selector
     * @param <R> type of result
     * @return {@link Reading}
     */
    <R> Reading<E, R> select(Class<R> resultClass, Selector<E, R, ? extends Selection<R>> selector);


    /**
     * Specify projection via given selector.
     *
     * @param selector selector
     * @param <R> type of result
     * @return {@link Reading}
     */
    <R> Reading<E, R> select(Selector<E, R, ? extends Selection<R>> selector);


    /**
     * Create the tuple projection.
     *
     * @param selector selector
     * @return {@link Reading}
     */
    Reading<E, Tuple> selectTuple(Selector<E, Tuple, CompoundSelection<Tuple>> selector);


    /**
     * Create {@link Query} that will be return the {@link Slice}.
     *
     * @param qualifier {@link PageQualifier}
     * @return {@link Query}
     */
    Query<Slice<E>> toSlice(PageQualifier<?> qualifier);


    /**
     * Create {@link Query} that will be return the {@link Page}.
     *
     * @param qualifier {@link PageQualifier}
     * @return {@link Query}
     */
    Query<Page<E>> toPage(PageQualifier<?> qualifier);


    /**
     * Gets the count {@link Query}.
     *
     * @return the count {@link Query}
     */
    Query<Long> count();


    /**
     * Create a new {@link Querying}.
     * @param entity root entity
     * @param <E> type of root entity
     * @return {@link Querying}
     */
    static <E> Querying<E> of(Class<E> entity) {
        return QueryingImpl.of(entity);
    }

}
