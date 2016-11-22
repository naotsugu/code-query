package code.core.domain.page.jpa;

import code.core.domain.page.Direction;
import code.core.domain.page.SortOrder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * JPA Utility.
 */
public class JpaPaths {


    public static <T> Collector<SortOrder<T>, ?, List<Order>> toOrder(final Root<?> root, final CriteriaBuilder cb) {

        BiConsumer<List<Order>, SortOrder<T>> accumulator = (list, o) ->
                list.add((o.getDirection() == Direction.ASC) ?
                        cb.asc(toPath(root, o.getProperty())) :
                        cb.desc(toPath(root, o.getProperty())));

        BinaryOperator<List<Order>> combiner = (l1, l2) -> { l1.addAll(l2); return l1; };


        return Collector.of(ArrayList::new, accumulator, combiner);
    }



    private static Path<?> toPath(final Path<?> path, final Object prop) {

        if (prop == null) {
            return null;

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
