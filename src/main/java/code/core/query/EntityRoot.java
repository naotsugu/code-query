package code.core.query;

import code.core.domain.page.Sortable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@FunctionalInterface
public interface EntityRoot<E> {

    Root<E> apply(Sortable<?> sortable, CriteriaQuery<?> query, CriteriaBuilder cb);

}
