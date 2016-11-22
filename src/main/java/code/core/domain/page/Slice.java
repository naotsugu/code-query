package code.core.domain.page;

import java.util.List;

/**
 * A slice of data.
 * It contains content of objects.
 *
 * @param <E> Type of contents
 * @author naotsugu
 */
public interface Slice<E> extends Pageable {

    /**
     * Returns the page content as {@link List}.
     * @return the page content as {@link List}
     */
    List<E> getContent();


    /**
     * Returns the number of elements currently on this {@link Slice}.
     * @return the number of elements
     */
    default int getNumberOfElements() {
        return getContent().size();
    };


    /**
     * Returns if there is a next {@link Page}.
     *
     * @return {@code true} if there is a next {@link Page}.
     */
    boolean hasNext();

}
