package code.core.query;

import code.core.query.impl.CriteriaContextImpl;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;

public interface CriteriaContext<E, R> extends EqualSupport, LikeSupport, CompoundSupport {

    Root<E> root();

    CriteriaQuery<R> query();

    CriteriaBuilder builder();


    static <E, R> CriteriaContext<E, R> of(Root<E> root, CriteriaQuery<R> query, CriteriaBuilder builder) {
        return CriteriaContextImpl.of(root, query, builder);
    }


    default <Y> Path<Y> get(SingularAttribute<? super E, Y> attr) {
        return root().get(attr);
    }

    default <U> Subquery<U> subquery(Class<U> domainClass) {
        Subquery<U> subquery = query().subquery(domainClass);
        return subquery;
    }

}
