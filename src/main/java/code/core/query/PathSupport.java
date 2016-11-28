package code.core.query;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

/**
 * Path selection support.
 *
 * @author Naotsugu Kobayashi
 */
public interface PathSupport<E> {

    /**
     * Get a root.
     *
     * @return Root
     */
    Root<E> root();


    /**
     *  Create a path corresponding to the referenced single-valued attribute.
     *
     *  @param attribute single-valued attribute
     *  @return path corresponding to the referenced attribute
     */
    default <Y> Path<Y> get(SingularAttribute<? super E, Y> attribute) {
        return root().get(attribute);
    }

}
