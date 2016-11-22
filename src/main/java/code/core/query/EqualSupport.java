package code.core.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public interface EqualSupport {

    CriteriaBuilder builder();

    default Predicate equal(Expression<?> expression1, Expression<?> expression2) {
        return builder().equal(expression1, expression2);
    }

    default Predicate equal(Expression<?> expression, Object object) {
        return builder().equal(expression, object);
    }

    default Predicate notEqual(Expression<?> expression1, Expression<?> expression2) {
        return builder().notEqual(expression1, expression2);
    }

    default Predicate notEqual(Expression<?> expression, Object object) {
        return builder().notEqual(expression, object);
    }

    default Predicate isTrue(Expression<Boolean> expression) {
        return builder().isTrue(expression);
    }

    default Predicate isFalse(Expression<Boolean> expression) {
        return builder().isTrue(expression);
    }

    default Predicate isNull(Expression<Boolean> expression) {
        return builder().isNull(expression);
    }

    default Predicate isNotNull(Expression<Boolean> expression) {
        return builder().isNotNull(expression);
    }

}
