package code.core.domain.page;

/**
 * Abstract interface for page request.
 *
 * @param <T> type of sort properties
 * @author Naotsugu Kobayashi
 */
public interface PageRequest<T> extends PageQualifier<T> {

    /**
     * Create a new next page request.
     * @return a new next page request
     */
    PageRequest next();


    /**
     * Create a new previous page request.
     * If current page is zero, then returns same page request.
     * @return a new next page request
     */
    PageRequest previous();


    /**
     * Create a new page request that is renewed page size.
     *
     * @param size  new page size
     * @return a new next page request
     */
    PageRequest withSize(int size);


    /**
     * Create a new next page request that is renewed page number.
     *
     * @param number  new page number
     * @return a new next page request
     */
    PageRequest withNumber(int number);


    /**
     * Create a new next page request that is renewed sort condition.
     *
     * @param sort  new sort
     * @return a new next page request
     */
    PageRequest withSort(Sort<T> sort);

}
