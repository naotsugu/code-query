package code.core.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 * Equality predicate support.
 *
 * @author naotsugu
 */
public interface EqualSupport {

    CriteriaBuilder builder();

    /**
     * Create a predicate for testing the arguments for equality.
     * @param expression1 expression
     * @param expression2 expression
     * @return equality predicate
     */
    default Predicate equal(Expression<?> expression1, Expression<?> expression2) {
        return builder().equal(expression1, expression2);
    }

    /**
     * Create a predicate for testing the arguments for equality.
     * @param expression  expression
     * @param object  object
     * @return equality predicate
     */
    default Predicate equal(Expression<?> expression, Object object) {
        return builder().equal(expression, object);
    }

    /**
     * Create a predicate for testing the arguments for inequality.
     * @param expression1 expression
     * @param expression2 expression
     * @return inequality predicate
     */
    default Predicate notEqual(Expression<?> expression1, Expression<?> expression2) {
        return builder().notEqual(expression1, expression2);
    }

    /**
     * Create a predicate for testing the arguments for inequality.
     * @param expression expression
     * @param object object
     * @return inequality predicate
     */
    default Predicate notEqual(Expression<?> expression, Object object) {
        return builder().notEqual(expression, object);
    }

    /**
     * Create a predicate testing for a true value.
     * @param expression expression to be tested
     * @return predicate
     */
    default Predicate isTrue(Expression<Boolean> expression) {
        return builder().isTrue(expression);
    }

    /**
     * Create a predicate testing for a false value.
     * @param expression expression to be tested
     * @return predicate
     */
    default Predicate isFalse(Expression<Boolean> expression) {
        return builder().isTrue(expression);
    }

    /**
     * Create a predicate to test whether the expression is null.
     * @param expression expression
     * @return is-null predicate
     */
    default Predicate isNull(Expression<Boolean> expression) {
        return builder().isNull(expression);
    }

    /**
     * Create a predicate to test whether the expression is not null.
     * @param expression expression
     * @return is-not-null predicate
     */
    default Predicate isNotNull(Expression<Boolean> expression) {
        return builder().isNotNull(expression);
    }


}
