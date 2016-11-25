package code.core.query;

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


}
