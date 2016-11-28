package code.core.domain.page;

/**
 * Companion object of {@link PageRequest}.
 *
 * @author Naotsugu Kobayashi
 */
public interface PageRequests {

    /** default of page window size. */
    int DEFAULT_PAGE_SIZE = 100;


    /**
     * Create a first page request with default page size and non-specify sort order.
     *
     * @return a first page request
     */
    static PageRequest of() {
        return new BasicPageRequest(0, DEFAULT_PAGE_SIZE, new BasicSort<>(null));
    }


    /**
     * Create a first page request with specified page size.
     *
     * @param size  page size
     * @return  a first page request
     */
    static PageRequest of(int size) {
        return new BasicPageRequest(0, size, new BasicSort<>(null));
    }


    /**
     * Create a first page request with specified sort order.
     *
     * @param sort  sort order
     * @return  a first page request
     */
    static PageRequest of(Sort sort) {
        return new BasicPageRequest(0, DEFAULT_PAGE_SIZE, sort);
    }


    /**
     * Create page request.
     *
     * @param number  page number(index).
     * @param size  page size
     * @param sort  sort order
     * @return  page request
     */
    static PageRequest of(int number, int size, Sort sort) {
        return new BasicPageRequest(number, size, sort);
    }


}
