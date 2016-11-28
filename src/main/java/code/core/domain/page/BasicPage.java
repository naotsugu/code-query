package code.core.domain.page;

import java.util.List;
import java.util.Objects;

/**
 * Basic implementation of {@link Page}.
 *
 * @author Naotsugu Kobayashi
 */
public class BasicPage<E> implements Page<E> {

    /** A slice of data. */
    private final Slice<E> slice;

    /** Total count of elements. */
    private final long totalElements;


    /**
     * Create new page implementation instance.
     *
     * @param content  contents of this page
     * @param pageable  pageable
     * @param totalElements  total count of elements
     */
    public BasicPage(List<E> content, Pageable pageable, long totalElements) {

        Objects.requireNonNull(pageable);

        this.slice = new BasicSlice<>(content, pageable,
                totalElements > pageable.getOffset() + pageable.getSize());
        this.totalElements = totalElements;

    }


    @Override
    public int getTotalPages() {
        return getSize() == 0
                ? 1
                : (int) Math.ceil((double) totalElements / (double) getSize());
    }


    @Override
    public long getTotalElements() {
        return totalElements;
    }


    @Override
    public boolean isLast() {
        return !slice.hasNext();
    }


    @Override
    public boolean hasNext() {
        return slice.hasNext();
    }


    @Override
    public List<E> getContent() {
        return slice.getContent();
    }


    @Override
    public int getNumber() {
        return slice.getNumber();
    }


    @Override
    public int getSize() {
        return slice.getSize();
    }


    @Override
    public int getOffset() {
        return slice.getOffset();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicPage<?> basicPage = (BasicPage<?>) o;

        if (totalElements != basicPage.totalElements) return false;
        return slice.equals(basicPage.slice);

    }


    @Override
    public int hashCode() {
        int result = slice.hashCode();
        result = 31 * result + (int) (totalElements ^ (totalElements >>> 32));
        return result;
    }

}
