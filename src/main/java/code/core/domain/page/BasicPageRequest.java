package code.core.domain.page;

import java.util.Objects;
import java.util.stream.Collector;

/**
 * Basic implementation of {@link PageRequest}.
 *
 * @param <T>
 * @author naotsugu
 */
public class BasicPageRequest<T> implements PageRequest<T> {

    private final int number;

    private final int size;

    private final Sort<T> sort;


    public BasicPageRequest(int number, int size, Sort<T> sort) {

        if (number < 0) {
            throw new IllegalArgumentException("Page number must not be less than zero.");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("Page size must not be less than or equal runWith zero!");
        }

        Objects.requireNonNull(sort);

        this.number = number;
        this.size = size;
        this.sort = sort;

    }


    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getOffset() {
        return number * size;
    }


    @Override
    public Sort<T> getSort() {
        return sort;
    }

    @Override
    public <R> R getSortBy(Collector<SortOrder<T>, ?, R> collector) {
        return sort.collect(collector);
    }


    @Override
    public PageRequest next() {
        return getNumber() == Integer.MAX_VALUE
            ? this
            : new BasicPageRequest(getNumber() + 1, getSize(), getSort());
    }


    @Override
    public PageRequest previous() {
        return getNumber() == 0
            ? this
            : new BasicPageRequest(getNumber() - 1, getSize(), getSort());
    }


    @Override
    public PageRequest withSize(int size) {
        return new BasicPageRequest(number, size, sort);
    }

    @Override
    public PageRequest withNumber(int number) {
        return new BasicPageRequest(number, size, sort);
    }

    @Override
    public PageRequest withSort(Sort<T> sort) {
        return new BasicPageRequest(number, size, sort);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicPageRequest that = (BasicPageRequest) o;

        if (number != that.number) return false;
        if (size != that.size) return false;
        return sort.equals(that.sort);

    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + size;
        result = 31 * result + sort.hashCode();
        return result;
    }
}
