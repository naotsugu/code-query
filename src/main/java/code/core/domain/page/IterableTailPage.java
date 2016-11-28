package code.core.domain.page;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Per page tailing iterator.
 * <p>
 * Page number treat as descending, and page contents treat as ascending.
 *
 * @param <E>  type of page elements
 * @author Naotsugu Kobayashi
 */
public class IterableTailPage<E> implements Iterator<E>, Iterable<E> {

    /** Base page request. */
    private final PageRequest pageRequest;

    /** Page contents find function. */
    private final Function<PageRequest, Page<E>> findFunction;

    /** Per page hook function. */
    private final Consumer<PageRequest> perPageHook;

    /** Current page count. */
    private int pageCount;

    /** Current page count. */
    private boolean lastPage;

    /** Per page iterator. */
    private Iterator<E> perPageIterator;


    /**
     * Create a new IterablePage.
     *
     * @param pageRequest  base page request
     * @param findFunction  page contents find function
     * @param perPageHook  per page hook function
     */
    public IterableTailPage(PageRequest pageRequest,
                            Function<PageRequest, Page<E>> findFunction,
                            Consumer<PageRequest> perPageHook) {

        this.findFunction = findFunction;
        this.pageRequest = pageRequest;
        this.perPageHook = perPageHook;
        this.pageCount = -1;
        this.lastPage = false;

    }


    /**
     * Create a new IterablePage.
     *
     * @param pageRequest  base page request
     * @param findFunction  page contents find function
     */
    public IterableTailPage(PageRequest pageRequest,
                            Function<PageRequest, Page<E>> findFunction) {
        this(pageRequest, findFunction, PageRequest -> {});
    }


    @Override
    public boolean hasNext() {

        if (pageCount == -1) {
            // initial read.
            nextPage();
        }

        if (!perPageIterator.hasNext() && !lastPage) {
            // Supply from page
            nextPage();
        }

        return !lastPage || perPageIterator.hasNext();
    }


    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return perPageIterator.next();
    }


    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported.");
    }


    @Override
    public Iterator<E> iterator() {
        return this;
    }


    /**
     * Read next page and create iterator of page.
     */
    private void nextPage() {

        if (pageCount == -1) {
            PageRequest pr = pageRequest.withNumber(0);
            Page<E> preFetchPage = findFunction.apply(pr);

            if (preFetchPage.isLast()) {
                lastPage = true;
                pageCount = 0;
                perPageHook.accept(pr);
                perPageIterator = preFetchPage.getContent().iterator();
                return;
            } else {
                pageCount = preFetchPage.getTotalPages();
            }

        }

        PageRequest pr = pageRequest.withNumber(--pageCount);
        perPageHook.accept(pr);
        Page<E> page = findFunction.apply(pr);
        lastPage = pageCount == 0;
        perPageIterator = page.getContent().iterator();

    }

}





