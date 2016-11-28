package code.core.domain.page.jpa;

import code.core.domain.page.Direction;
import code.core.domain.page.SortOrder;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Utility for JPA.
 *
 * @author Naotsugu Kobayashi
 */
public class JpaPaths {


    /**
     * Create new collector for JPA sort order.
     * @param root  root
     * @param cb  CriteriaBuilder
     * @param <T>  type of sort property
     * @return Collector
     */
    public static <T> Collector<SortOrder<T>, ?, List<Order>> toOrder(final Root<?> root, final CriteriaBuilder cb) {

        BiConsumer<List<Order>, SortOrder<T>> accumulator = (list, o) ->
                list.add((o.getDirection() == Direction.ASC) ?
                        cb.asc(toPath(root, o.getProperty())) :
                        cb.desc(toPath(root, o.getProperty())));

        BinaryOperator<List<Order>> combiner = (l1, l2) -> { l1.addAll(l2); return l1; };


        return Collector.of(ArrayList::new, accumulator, combiner);
    }


    /**
     * Convert to sort expression.
     *
     * @param path  base path
     * @param prop  sort property
     * @return expression
     */
    private static Expression<?> toPath(final Path<?> path, final Object prop) {

        if (prop == null) {

            return null;

        } else if (prop instanceof PathTo) {

            PathTo pathTo = (PathTo) prop;
            return pathTo.apply(path);

        } else if (prop instanceof Path) {

            return (Path) prop;

        } else if (prop instanceof String) {

            String props = (String) prop;
            if (props.length() == 0) {
                return null;
            }

            int pos = props.indexOf(".");
            String head = (pos == -1) ? props : props.substring(0, pos);
            String tail = (pos == -1) ? "" : props.substring(pos);

            if (tail.length() > 0) {
                return toPath(path.get(head), tail);
            }

            return path.get(head);

        } else {

            throw new IllegalArgumentException();
        }
    }


}
