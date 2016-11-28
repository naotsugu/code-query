package code.core.query;

import javax.persistence.criteria.Predicate;
import java.util.function.BiFunction;

/**
 * Helper for combine {@link Specification} instances.
 *
 * @param <T>
 * @author Naotsugu Kobayashi
 */
public class Specifications<T> implements Specification<T> {

    /** specification */
    private final Specification<T> spec;


    /**
     * Creates a new {@link Specifications} wrapper for the given {@link Specification}.
     *
     * @param spec {@link Specification} to be wrapped
     */
    protected Specifications(Specification<T> spec) {
        this.spec = (spec == null) ? Specification.empty() : spec;
    }


    /**
     * Creates a new {@link Specifications} wrapper for the given {@link Specification}.
     *
     * @param spec {@link Specification} to be wrapped
     */
    public static <T> Specifications<T> of(Specification<T> spec) {
        return new Specifications(spec);
    }


    /**
     * Creates a empty {@link Specifications}.
     */
    public static <T> Specifications<T> empty() {
        return new Specifications(null);
    }


    @Override
    public Predicate toPredicate(CriteriaContext<T, ?> criteria) {
        return spec.toPredicate(criteria);
    }



    /**
     * AND operation.
     * ANDs the given specification to the current one.
     *
     * @param arg  operand
     * @return new {@link Specifications}
     */
    public Specifications<T> and(final Specification<T> arg) {

        final Specification<T> other = (arg == null) ? Specification.empty() : arg;

        return new Specifications<>(criteria -> {

            final Predicate self = spec.toPredicate(criteria);
            final Predicate that = other.toPredicate(criteria);

            return compose(self, that, (lhs, rhs) -> criteria .builder().and(lhs, rhs));
        });
    }


    /**
     * OR operation.
     * ORs the given specification to the current one.
     *
     * @param arg  operand
     * @return new {@link Specifications}
     */
    public Specifications<T> or(final Specification<T> arg) {

        final Specification<T> other = (arg == null) ? Specification.empty() : arg;

        return new Specifications<>(criteria -> {

            Predicate self = spec.toPredicate(criteria);
            Predicate that = other.toPredicate(criteria);

            return compose(self, that, (lhs, rhs) -> criteria.builder().or(lhs, rhs));
        });
    }


    /**
     * Negates the given {@link Specification}.
     *
     * @param spec  operand
     * @return new {@link Specifications}
     */
    public static <T> Specifications<T> not(final Specification<T> spec) {

        return new Specifications<T>(spec) {

            @Override
            public Predicate toPredicate(CriteriaContext<T, ?> criteria) {
                final Predicate self = spec.toPredicate(criteria);
                return (self == null) ? null : criteria.builder().not(self);
            }
        };
    }


    /**
     * Compose predicate by the given function.
     *
     * @param lhs  left-hand-side
     * @param rhs  right-hand-side
     * @param function  compose function
     * @return {@link Predicate}
     */
    private Predicate compose(Predicate lhs, Predicate rhs,
                              BiFunction<Predicate, Predicate, Predicate> function) {

        if (lhs == null && rhs == null) return null;
        else if (lhs == null) return rhs;
        else if (rhs == null) return lhs;
        else return function.apply(lhs, rhs);

    }

}
