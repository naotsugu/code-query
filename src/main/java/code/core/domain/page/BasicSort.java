package code.core.domain.page;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Basic implementation of {@link Sort}.
 *
 * @param <T>
 * @author naotsugu
 */
public class BasicSort<T> implements Sort<T> {

    private List<SortOrder<T>> orders;


    public BasicSort(List<SortOrder<T>> orders) {
        this.orders = (orders == null) ? new ArrayList<>() : orders;
    }


    @Override
    public Stream<SortOrder<T>> stream() {
        return orders.stream();
    }



    @Override
    public Sort<T> normalize() {
        final Set<T> set = new HashSet<>();
        return new BasicSort<>(orders.stream()
                .filter(o -> set.add(o.getProperty()))
                .collect(Collectors.toList()));
    }


    @Override
    public Optional<SortOrder<T>> getOrderFor(T property) {
        return orders.stream()
                .filter(o -> o.getProperty().equals(property))
                .findFirst();
    }


    @Override
    public Sort<T> and(Sort<T> sort) {
        List<SortOrder<T>> sortOrders = new ArrayList<>(orders);
        sort.stream().forEach(sortOrders::add);
        return new BasicSort<>(sortOrders);
    }


    @Override
    public Sort<T> asc(T property) {
        return and(new BasicSort<>(Arrays.asList(new BasicSortOrder<>(property, Direction.ASC))));
    }


    @Override
    public Sort<T> desc(T property) {
        return and(new BasicSort<>(Arrays.asList(new BasicSortOrder<>(property, Direction.DESC))));
    }

    @Override
    public <R> R collect(Collector<SortOrder<T>, ?, R> collector) {
        return orders.stream().collect(collector);
    }

    @Override
    public String toString() {
        return String.format("%s%s", getClass().getSimpleName(), Arrays.deepToString(orders.toArray()));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicSort<?> sort = (BasicSort<?>) o;

        return orders.equals(sort.orders);

    }

    @Override
    public int hashCode() {
        return orders.hashCode();
    }

}
