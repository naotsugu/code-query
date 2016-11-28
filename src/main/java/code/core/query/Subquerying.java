package code.core.query;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.Objects;

/**
 * Building subquery support.
 *
 * @param <E1> type of outer query root entity
 * @param <E2> type of subquery's root entity
 * @author Naotsugu Kobayashi
 */
public class Subquerying<E1, E2> {

    /** context of outer query. */
    private final CriteriaContext<E1, ?> context;

    /** root entity of subquery. */
    private final Class<E2> rootClass;

    /** specifications. */
    private final Specifications<E2> specs;


    /**
     * Create a new {@link Subquerying}.
     *
     * @param context  context of outer query
     * @param rootClass  root entity of subquery
     * @param specs  specifications
     */
    private Subquerying(CriteriaContext<E1, ?> context, Class<E2> rootClass, Specifications<E2> specs) {
        this.context = context;
        this.rootClass = Objects.requireNonNull(rootClass);
        this.specs = Objects.requireNonNull(specs);
    }


    /**
     * Create a new {@link Subquerying}.
     *
     * @param context  context of outer query
     * @param rootClass  root entity of subquery
     */
    private Subquerying(CriteriaContext<E1, ?> context, Class<E2> rootClass) {
        this(context, rootClass, Specifications.empty());
    }


    /**
     * Create a new {@link Subquerying}.
     *
     * @param context  context of outer query
     * @param rootClass  root entity of subquery
     * @param <E1> type of outer query root entity
     * @param <E2> type of subquery's root entity
     * @return {@link Subquerying}
     */
    public static <E1, E2> Subquerying<E1, E2> of(CriteriaContext<E1, ?> context, Class<E2> rootClass) {
        return new Subquerying<>(context, rootClass);
    }


    /**
     * Filtering by the given specification.
     *
     * @param spec  specification
     * @return {@link Subquerying}
     */
    public Subquerying<E1, E2> filter(Specification<E2> spec) {
        return new Subquerying<>(context, rootClass, specs.and(spec));
    }


    /**
     * Specify projection, and gets a {@link Subquery}.
     *
     * @param resultClass  projection result class
     * @param selector  selector
     * @param <R2> type of subquerying result
     * @return {@link Subquery}
     */
    public <R2> Subquery<R2> select(Class<R2> resultClass, Selector<E2, R2, ? extends Expression<R2>> selector) {
        Subquery<R2> subquery = context.query().subquery(resultClass);
        Root<E2> root = subquery.from(rootClass);
        subquery.select(selector.apply(root, subquery, context.builder()));
        subquery.where(specs.toPredicate(root, subquery, context.builder()));
        return subquery;
    }

}
