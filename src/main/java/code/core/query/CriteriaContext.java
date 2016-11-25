package code.core.query;

import code.core.query.impl.CriteriaContextImpl;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;

public interface CriteriaContext<E, R> extends
        EqualSupport, LikeSupport, ConstructionSupport, AggregateSupport,
        PathSupport<E> {

    Root<E> root();

    AbstractQuery<R> query();

    CriteriaBuilder builder();


    static <E, R> CriteriaContext<E, R> of(Root<E> root, AbstractQuery<R> query, CriteriaBuilder builder) {
        return CriteriaContextImpl.of(root, query, builder);
    }


    default <U> Subquerying<E, U> subqueryOf(Class<U> domainClass) {
        return Subquerying.of(this, domainClass);
    }

}
