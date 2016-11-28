package code.core.domain.page;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Per page iterator.
 *
 * @param <E> type of page elements
 * @author Naotsugu Kobayashi
 */
public class IterablePage<E> implements Iterator<E>, Iterable<E> {

    /** Base page request. */
    private final PageRequest pageRequest;

    /** Slice contents find function. */
    private final Function<PageRequest, Slice<E>> findFunction;

    /** Per page hook function. */
    private final Consumer<PageRequest> perPageHook;

    /** Current page count. */
    protected int pageCount;

    /** Current page count. */
    protected boolean lastPage;

    /** Per page iterator. */
    protected Iterator<E> perPageIterator;


    /**
     * Create a new IterablePage.
     *
     * @param pageRequest  base page request
     * @param findFunction  page contents find function
     * @param perPageHook  per page hook function
     */
    public IterablePage(PageRequest pageRequest,
                        Function<PageRequest, Slice<E>> findFunction,
                        Consumer<PageRequest> perPageHook) {

        this.pageRequest = pageRequest;
        this.findFunction = findFunction;
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
    public IterablePage(PageRequest pageRequest,
                        Function<PageRequest, Slice<E>> findFunction) {
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
    protected void nextPage() {
        pageCount++;
        PageRequest pr = pageRequest.withNumber(pageCount);
        perPageHook.accept(pr);
        Slice<E> slice = findFunction.apply(pr);
        lastPage = !slice.hasNext();
        perPageIterator = slice.getContent().iterator();
    }


}
