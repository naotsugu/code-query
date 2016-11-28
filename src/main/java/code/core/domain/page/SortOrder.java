package code.core.domain.page;

import java.io.Serializable;

/**
 * Sort property implements the pairing of an {@link Direction} and a property.
 *
 * @param <T> type of property
 * @author Naotsugu Kobayashi
 */
public interface SortOrder<T> extends Serializable {

    /** Default sort direction. */
    Direction DEFAULT_DIRECTION = Direction.ASC;


    /**
     * Sort property.
     *
     * @return sort property
     */
    T getProperty();


    /**
     * Sort direction.
     *
     * @return sort direction
     */
    Direction getDirection();


}
