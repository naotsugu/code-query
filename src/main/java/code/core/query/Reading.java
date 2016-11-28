package code.core.query;

import code.core.domain.page.Page;
import code.core.domain.page.PageQualifier;
import code.core.domain.page.Slice;

import javax.persistence.criteria.Expression;
import java.util.List;

/**
 * Build {@link Query} helper.
 *
 * @param <E> type of root entity
 * @param <R> type of query result
 * @author Naotsugu Kobayashi
 */
public interface Reading<E, R> {


    /**
     * Specify the expressions via selector that are used to form groups
     * over the query results.
     *
     * @param group selector
     * @return {@link Reading}
     */
    Reading<E, R> groupBy(Selector<E, R, ? extends Expression<?>> group);


    /**
     * Specify the expressions via selector that are used to form groups
     * over the query results.
     *
     * @param groups list of selector
     * @return {@link Reading}
     */
    Reading<E, R> groupBy(List<Selector<E, R, ? extends Expression<?>>> groups);


    /**
     * Create {@link Query} that will be return the {@link Slice}.
     *
     * @param qualifier {@link PageQualifier}
     * @return {@link Query}
     */
    Query<Slice<R>> toSlice(PageQualifier<?> qualifier);


    /**
     * Create {@link Query} that will be return the {@link Page}.
     *
     * @param qualifier {@link PageQualifier}
     * @return {@link Query}
     */
    Query<Page<R>> toPage(PageQualifier<?> qualifier);


    /**
     * Create {@link Query} that will be return count of recode.
     *
     * @return {@link Query}
     */
    Query<Long> count();


    /**
     * Create {@link Query} that will be return single result.
     *
     * @return {@link Query}
     */
    Query<R> singleResult();

}
