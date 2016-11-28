package code.core.domain.page.jpa;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

/**
 * Convert path to expression function interface.
 *
 * @param <E> type of entity
 * @author Naotsugu Kobayashi
 */
@FunctionalInterface
public interface PathTo<E> {

    /**
     * Apply this function.
     *
     * @param path  path
     * @return  Expression
     */
    Expression<?> apply(Path<E> path);
}
