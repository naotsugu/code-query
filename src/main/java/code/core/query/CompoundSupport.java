package code.core.query;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;

public interface CompoundSupport {

    CriteriaBuilder builder();

    default <R> CompoundSelection<R> construct(Class<R> resultClass, Selection<?>...selections) {
        return builder().construct(resultClass, selections);
    }

    default CompoundSelection<Tuple> tuple(Selection<?>...selections) {
        return builder().tuple(selections);
    }

}
