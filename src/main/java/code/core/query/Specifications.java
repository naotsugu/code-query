package code.core.query;

import javax.persistence.criteria.Predicate;
import java.util.function.BiFunction;

public class Specifications<T> implements Specification<T> {

    private final Specification<T> spec;


    protected Specifications(Specification<T> spec) {
        this.spec = (spec == null) ? Specification.empty() : spec;
    }


    public static <T> Specifications<T> of(Specification<T> spec) {
        return new Specifications(spec);
    }


    public static <T> Specifications<T> empty() {
        return new Specifications(null);
    }


    @Override
    public Predicate toPredicate(CriteriaContext<T, ?> criteria) {
        return spec.toPredicate(criteria);
    }


    public Specifications<T> and(final Specification<T> arg) {

        final Specification<T> other = (arg == null) ? Specification.empty() : arg;

        return new Specifications<>(criteria -> {

            final Predicate self = spec.toPredicate(criteria);
            final Predicate that = other.toPredicate(criteria);

            return compose(self, that, (lhs, rhs) -> criteria .builder().and(lhs, rhs));
        });
    }


    public Specifications<T> or(final Specification<T> arg) {

        final Specification<T> other = (arg == null) ? Specification.empty() : arg;

        return new Specifications<>(criteria -> {

            Predicate self = spec.toPredicate(criteria);
            Predicate that = other.toPredicate(criteria);

            return compose(self, that, (lhs, rhs) -> criteria.builder().or(lhs, rhs));
        });
    }


    public static <T> Specifications<T> not(final Specification<T> spec) {

        return new Specifications<T>(spec) {

            @Override
            public Predicate toPredicate(CriteriaContext<T, ?> criteria) {
                final Predicate self = spec.toPredicate(criteria);
                return (self == null) ? null : criteria.builder().not(self);
            }
        };
    }


    private Predicate compose(Predicate lhs, Predicate rhs,
                              BiFunction<Predicate, Predicate, Predicate> function) {

        if (lhs == null && rhs == null) return null;
        else if (lhs == null) return rhs;
        else if (rhs == null) return lhs;
        else return function.apply(lhs, rhs);

    }
}
