package code.core.domain.page;

import java.util.stream.Collector;

/**
 * Page qualifier.
 *
 * @param <T>
 * @author naotsugu
 */
public interface PageQualifier<T> extends Pageable, Sortable<T> {


    default boolean isEmpty() {
        return this == EMPTY;
    }


    static <T> PageQualifier<T> empty() {
        return EMPTY;
    }


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
