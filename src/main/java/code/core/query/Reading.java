package code.core.query;

import code.core.domain.page.Page;
import code.core.domain.page.PageQualifier;
import code.core.domain.page.Slice;

import javax.persistence.criteria.Expression;
import java.util.List;

public interface Reading<E, R> {

    Reading<E, R> groupBy(Selector<E, R, ? extends Expression<?>> group);

    Reading<E, R> groupBy(List<Selector<E, R, ? extends Expression<?>>> groups);

    Query<Slice<R>> toSlice(PageQualifier<?> qualifier);

    Query<Page<R>> toPage(PageQualifier<?> qualifier);

    Query<Long> count();

    Query<R> singleResult();
}
