package code.core.domain.page;

import java.util.Objects;

/**
 * Implements of SortOrder.
 * @param <T>
 */
public class BasicSortOrder<T> implements SortOrder<T> {

    private final T property;

    private final Direction direction;


    public BasicSortOrder(T property, Direction direction) {

        this.property = Objects.requireNonNull(property);
        this.direction = Objects.requireNonNull(direction);

    }

    @Override
    public T getProperty() {
        return property;
    }


    @Override
    public Direction getDirection() {
        return direction;
    }


    @Override
    public String toString() {
        return String.format("%s[%s, %s]", getClass().getSimpleName(), property, direction);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicSortOrder<?> sortOrder = (BasicSortOrder<?>) o;

        if (!property.equals(sortOrder.property)) return false;
        return direction == sortOrder.direction;

    }


    @Override
    public int hashCode() {
        int result = property.hashCode();
        result = 31 * result + direction.hashCode();
        return result;
    }

}
