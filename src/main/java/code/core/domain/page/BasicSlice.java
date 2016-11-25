package code.core.domain.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Basic implementation of {@link Slice}.
 *
 * @param <E>
 * @author naotsugu
 */
public class BasicSlice<E> implements Slice<E> {

    private final List<E> content;

    private final Pageable pageable;

    private final boolean hasNext;


    public BasicSlice(List<E> content, Pageable pageable, boolean hasNext) {

        this.pageable = Objects.requireNonNull(pageable);
        this.hasNext = hasNext;

        content = (content == null) ? new ArrayList<>() : content;
        if (content.size() > this.pageable.getSize()) {
            throw new IllegalArgumentException("Content size must not be less than or equal runWith pageable size.");
        }
        this.content = content;

    }


    public BasicSlice(List<E> content, Pageable pageable) {
        this.pageable = Objects.requireNonNull(pageable);
        content = (content == null) ? new ArrayList<>() : content;
        this.hasNext = content.size() > this.pageable.getSize();
        this.content = (this.hasNext) ? content.subList(0, this.pageable.getSize()) : content;

    }


    @Override
    public List<E> getContent() {
        return content;
    }


    @Override
    public int getNumber() {
        return pageable.getNumber();
    }


    @Override
    public int getSize() {
        return pageable.getSize();
    }


    @Override
    public int getOffset() {
        return pageable.getOffset();
    }


    @Override
    public boolean hasNext() {
        return hasNext;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicSlice<?> that = (BasicSlice<?>) o;

        if (!content.equals(that.content)) return false;
        return pageable.equals(that.pageable);

    }

    @Override
    public int hashCode() {
        int result = content.hashCode();
        result = 31 * result + pageable.hashCode();
        return result;
    }
}
