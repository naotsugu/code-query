package code.core.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

/**
 * Aggregate expression support.
 *
 * @author Naotsugu Kobayashi
 */
public interface AggregateSupport {

    /**
     * Gets a CriteriaBuilder.
     *
     * @return CriteriaBuilder
     */
    CriteriaBuilder builder();


    /**
     * Create an aggregate expression applying the avg operation.
     *
     * @param x  expression representing input value to avg operation
     * @return avg expression
     */
    default <N extends Number> Expression<Double> avg(Expression<N> x) {
        return builder().avg(x);
    }


    /**
     * Create an aggregate expression applying the sum operation.
     *
     * @param x  expression representing input value to sum operation
     * @return sum expression
     */
    default <N extends Number> Expression<N> sum(Expression<N> x) {
        return builder().sum(x);
    }


    /**
     * Create an aggregate expression applying the sum operation to an
     * Integer-valued expression, returning a Long result.
     *
     * @param x  expression representing input value to sum operation
     * @return sum expression
     */
    default Expression<Long> sumAsLong(Expression<Integer> x) {
        return builder().sumAsLong(x);
    }


    /**
     * Create an aggregate expression applying the sum operation to a
     * Float-valued expression, returning a Double result.
     *
     * @param x  expression representing input value to sum operation
     * @return sum expression
     */
    default Expression<Double> sumAsDouble(Expression<Float> x) {
        return builder().sumAsDouble(x);
    }


    /**
     * Create an aggregate expression applying the numerical max operation.
     *
     * @param x  expression representing input value to max operation
     * @return max expression
     */
    default <N extends Number> Expression<N> max(Expression<N> x) {
        return builder().max(x);
    }


    /**
     * Create an aggregate expression applying the numerical min operation.
     *
     * @param x  expression representing input value to min operation
     * @return min expression
     */
    default <N extends Number> Expression<N> min(Expression<N> x) {
        return builder().min(x);
    }


    /**
     * Create an aggregate expression for finding the greatest of
     * the values (strings, dates, etc).
     *
     * @param x  expression representing input value to greatest operation
     * @return greatest expression
     */
    default <X extends Comparable<? super X>> Expression<X> greatest(Expression<X> x) {
        return builder().greatest(x);
    }


    /**
     * Create an aggregate expression for finding the least of
     * the values (strings, dates, etc).
     *
     * @param x  expression representing input value to least operation
     * @return least expression
     */
    default <X extends Comparable<? super X>> Expression<X> least(Expression<X> x) {
        return builder().least(x);
    }


}
