package code.core.query;

import javax.persistence.EntityManager;

@FunctionalInterface
public interface Query<R> {

    R runWith(EntityManager em);

}
