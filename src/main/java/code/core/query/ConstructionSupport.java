package code.core.query;

import javax.persistence.Tuple;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Selection;

/**
 * Selection construction support.
 *
 * @author Naotsugu Kobayashi
 */
public interface ConstructionSupport {

    /**
     * Gets a CriteriaBuilder.
     *
     * @return CriteriaBuilder
     */
    CriteriaBuilder builder();


    /**
     * Create a selection item corresponding to a constructor.
     *
     * @param resultClass  class whose instance is to be constructed
     * @param selections  arguments to the constructor
     * @return compound selection item
     */
    default <R> CompoundSelection<R> construct(Class<R> resultClass, Selection<?>...selections) {
        return builder().construct(resultClass, selections);
    }


    /**
     * Create a tuple-valued selection item.
     *
     * @param selections  selection items
     * @return tuple-valued compound selection
     */
    default CompoundSelection<Tuple> tuple(Selection<?>...selections) {
        return builder().tuple(selections);
    }

}
