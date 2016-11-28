package code.core.domain.page;

import java.util.stream.Collector;

/**
 * Page qualifier.
 *
 * @param <T>  type of sort property
 * @author Naotsugu Kobayashi
 */
public interface PageQualifier<T> extends Pageable, Sortable<T> {

    /**
     * Returns {@code true} if {@code PageQualifier} is {@link #EMPTY} instance.
     *
     * @return Returns {@code true} if {@code PageQualifier} is empty, otherwise {@code false}
     */
    default boolean isEmpty() {
        return this == EMPTY;
    }


    /**
     * Returns {@link #EMPTY} instance of {@code PageQualifier}.
     *
     * @param <T>  type of sort property
     * @return {@link #EMPTY} instance
     */
    static <T> PageQualifier<T> empty() {
        return EMPTY;
    }


    /**
     * A Empty instance of {@code PageQualifier}.
     */
    PageQualifier EMPTY = new PageQualifier() {

        @Override
        public Sort getSort() {
            return null;
        }

        @Override
        public Object getSortBy(Collector collector) {
            return null;
        }

        @Override
        public int getNumber() {
            return 0;
        }

        @Override
        public int getSize() {
            return 0;
        }

        @Override
        public int getOffset() {
            return 0;
        }

    };


}
