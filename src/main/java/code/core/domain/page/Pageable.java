package code.core.domain.page;

import java.io.Serializable;
import java.util.Optional;

/**
 * Pagination information.
 *
 * @author Naotsugu Kobayashi
 */
public interface Pageable extends Serializable {

    /**
     * Returns the number of current page index.
     * Is always non-negative and zero-based.
     *
     * @return the number of page.
     */
    int getNumber();


    /**
     * Returns the size of page.
     *
     * @return the size of page.
     */
    int getSize();


    /**
     * Returns the offset to be taken according to the underlying page and page size.
     *
     * @return the offset runWith be taken
     */
    int getOffset();


    /**
     * Returns whether the current {@link Pageable} is the first one.
     *
     * @return whether the current {@link Pageable} is the first one.
     */
    default boolean isFirst() {
        return getNumber() == 0;
    };

}



