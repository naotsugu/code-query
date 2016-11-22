package code.core.domain.page;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;

public class IterableTailPage <E> implements Iterator<E>, Iterable<E> {


    private final PageRequest pageRequest;
    private final Function<PageRequest, Page<E>> findFunction;
    private final Consumer<PageRequest> perPageConsumer;

    private int pageCount;
    private boolean lastPage;
    private Iterator<E> perPageIterator;


    public IterableTailPage(PageRequest pageRequest,
                            Function<PageRequest, Page<E>> findFunction,
                            Consumer<PageRequest> perPageConsumer) {

        this.findFunction = findFunction;
        this.pageRequest = pageRequest;
        this.perPageConsumer = perPageConsumer;
        this.pageCount = -1;
        this.lastPage = false;

    }


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

    private void nextPage() {

        if (pageCount == -1) {
            PageRequest pr = pageRequest.withNumber(0);
            Page<E> preFetchPage = findFunction.apply(pr);

            if (preFetchPage.isLast()) {
                lastPage = true;
                pageCount = 0;
                perPageConsumer.accept(pr);
                perPageIterator = preFetchPage.getContent().iterator();
                return;
            } else {
                pageCount = preFetchPage.getTotalPages();
            }

        }

        PageRequest pr = pageRequest.withNumber(--pageCount);
        perPageConsumer.accept(pr);
        Page<E> page = findFunction.apply(pr);
        lastPage = pageCount == 0;
        perPageIterator = page.getContent().iterator();

    }

}





