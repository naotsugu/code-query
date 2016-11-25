package code.core.query;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.Objects;

public class Subquerying<E1, E2> {

    private final CriteriaContext<E1, ?> context;
    private final Class<E2> rootClass;
    private final Specifications<E2> specs;

    private Subquerying(CriteriaContext<E1, ?> context, Class<E2> rootClass, Specifications<E2> specs) {
        this.context = context;
        this.rootClass = Objects.requireNonNull(rootClass);
        this.specs = Objects.requireNonNull(specs);
    }

    private Subquerying(CriteriaContext<E1, ?> context, Class<E2> rootClass) {
        this(context, rootClass, Specifications.empty());
    }

    public static <E1, E2> Subquerying<E1, E2> of(CriteriaContext<E1, ?> context, Class<E2> rootClass) {
        return new Subquerying<>(context, rootClass);
    }

    public Subquerying<E1, E2> filter(Specification<E2> spec) {
        return new Subquerying<>(context, rootClass, specs.and(spec));
    }


    public <R2> Subquery<R2> map(Class<R2> resultClass, Selector<E2, R2, ? extends Expression<R2>> selector) {
        Subquery<R2> subquery = context.query().subquery(resultClass);
        Root<E2> root = subquery.from(rootClass);
        subquery.select(selector.apply(root, subquery, context.builder()));
        subquery.where(specs.toPredicate(root, subquery, context.builder()));
        return subquery;
    }
}
