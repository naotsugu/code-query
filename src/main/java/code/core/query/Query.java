package code.core.query;

import javax.persistence.EntityManager;

/**
 * Executable query.
 *
 * @param <R>  type of querying result
 * @author Naotsugu Kobayashi
 */
@FunctionalInterface
public interface Query<R> {

    /**
     * Run query with EntityManager.
     *
     * @param em  EntityManager
     * @return result of query
     */
    R runWith(EntityManager em);

}
