package code.core.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@FunctionalInterface
public interface Specification<E> {

    Specification EMPTY = criteria -> null;


    Predicate toPredicate(CriteriaContext<E, ?> criteria);


    default Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return toPredicate(CriteriaContext.of(root, query, builder));
    }


    default boolean isEmpty() {
        return this == EMPTY;
    }

    static <E> Specification<E> empty() {
        return EMPTY;
    }

}
