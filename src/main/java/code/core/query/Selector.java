package code.core.query;

import javax.persistence.criteria.*;

@FunctionalInterface
public interface Selector<E, R, S> {

    S apply(CriteriaContext<E, R> criteria);


    default S apply(Root<E> root, CriteriaQuery<R> query, CriteriaBuilder builder) {
        return apply(CriteriaContext.of(root, query, builder));
    }


    static <E, R, S> Selector<E, R, S> empty() {
        return criteria -> null;
    }


    static <E> Selector<E, Long, Selection<Long>> count() {
        return criteria -> criteria.query().isDistinct()
                ? criteria.builder().countDistinct(criteria.root())
                : criteria.builder().count(criteria.root());
    }

    static <E> Selector<E, E, Selection<E>> root() {
        return criteria -> criteria.root();
    }

}
