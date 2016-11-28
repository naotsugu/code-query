package code.core.query;

import code.core.domain.page.Page;
import code.core.domain.page.PageRequests;
import code.core.domain.page.Sorts;
import code.core.domain.page.jpa.PathTo;
import code.core.query.model.Customer;
import code.core.query.model.Customer_;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Tuple;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class QueryingTest {

    static EntityManagerFactory emf;

    static EntityManager em;


    /**
     * Create testing data.
     * <p>
     * <pre>
     * Customer is
     *   firstName0  lastName0  20
     *   firstName1  lastName1  21
     *   firstName2  lastName2  22
     *   firstName3  lastName3  23
     *   firstName4  lastName4  24
     *   firstName5  lastName5  20
     *    ...
     *   firstName23 lastName23 23
     *   firstName24 lastName24 24
     * </pre>
     */
    @BeforeAll static void initAll() {
        emf = Persistence.createEntityManagerFactory("testUnit");
        em = emf.createEntityManager();

        em.getTransaction().begin();
        IntStream.range(0, 25).forEach(i -> em.persist(
                new Customer("firstName" + i, "lastName" + i, 20 + i % 5)));

        em.getTransaction().commit();
    }


    @AfterAll static void tearDownAll() {
        em.close();
        emf.close();
    }


    @Test void testGetPage() {

        Page<Customer> page = Querying.of(Customer.class)
                .toPage(PageRequests.of(10))
                .runWith(em);

        assertThat(page.getTotalElements()).isEqualTo(25L);
        assertThat(page.getSize()).isEqualTo(10);
        assertThat(page.getTotalPages()).isEqualTo(3);
        assertThat(page.getContent().size()).isEqualTo(10);

    }


    @Test void testFilterToGetPage() {

        Page<Customer> page = Querying.of(Customer.class)
                .filter(firstNameEqualTo("firstName3"))
                .toPage(PageRequests.of())
                .runWith(em);

        assertThat(page.getTotalElements()).isEqualTo(1L);
        assertThat(page.getContent().get(0).getFirstName()).isEqualTo("firstName3");

    }


    @Test void test2FilterToGetPage() {

        Page<Customer> page = Querying.of(Customer.class)
                .filter(firstNameEqualTo("firstName5"))
                .filter(lastNameEqualTo("lastName5"))
                .toPage(PageRequests.of())
                .runWith(em);

        assertThat(page.getTotalElements()).isEqualTo(1L);
        assertThat(page.getContent().get(0).getFirstName()).isEqualTo("firstName5");
        assertThat(page.getContent().get(0).getLastName()).isEqualTo("lastName5");

    }


    @Test void testCounting() {

        Long count = Querying.of(Customer.class).count().runWith(em);

        assertThat(count).isEqualTo(25L);

    }


    @Test void testSortingByPath() {

        PathTo<Customer> age = root -> root.get(Customer_.age);
        PathTo<Customer> firstName = root -> root.get(Customer_.firstName);

        Page<Customer> page = Querying.of(Customer.class)
                .toPage(PageRequests.of(Sorts.desc(age).asc(firstName)))
                .runWith(em);

        assertThat(page.getContent().get(0).getAge()).isEqualTo(24);
        assertThat(page.getContent().get(0).getFirstName()).isEqualTo("firstName14");
    }


    @Test void testSortingByString() {

        Page<Customer> page = Querying.of(Customer.class)
                .toPage(PageRequests.of(Sorts.desc("age").asc("firstName")))
                .runWith(em);

        assertThat(page.getContent().get(0).getAge()).isEqualTo(24);
        assertThat(page.getContent().get(0).getFirstName()).isEqualTo("firstName14");
    }


    @Test void testSelecting() {

        Page<Integer> page = Querying.of(Customer.class)
                .select(customerAge)
                .groupBy(customerAge)
                .toPage(PageRequests.of(Sorts.desc("age")))
                .runWith(em);

        assertThat(page.getContent().get(0)).isEqualTo(24);

    }

    @Test void testTuple() {

        Page<Tuple> page = Querying.of(Customer.class)
                .filter(lastNameEqualTo("lastName20"))
                .select(Tuple.class, c -> c.tuple(
                        c.get(Customer_.firstName).alias("a1"),
                        c.get(Customer_.age).alias("a2")))
                .toPage(PageRequests.of())
                .runWith(em);

        assertThat(page.getTotalElements()).isEqualTo(1L);
        assertThat(page.getContent().get(0).get("a1", String.class)).isEqualTo("firstName20");
        assertThat(page.getContent().get(0).get("a2", Integer.class)).isEqualTo(20);

    }

    @Test void testConstruct() {

        Page<Dto> page = Querying.of(Customer.class)
                .filter(lastNameEqualTo("lastName10"))
                .select(toDto)
                .toPage(PageRequests.of())
                .runWith(em);

        assertThat(page.getTotalElements()).isEqualTo(1L);
        assertThat(page.getContent().get(0).value1).isEqualTo("firstName10");
        assertThat(page.getContent().get(0).value2).isEqualTo("lastName10");

    }


    @Test void testSubquery() {

        Page<Customer> page = Querying.of(Customer.class)
                .filter(c -> {
                    Subquery<Integer> sq = c.query().subquery(Integer.class);
                    Root<Customer> subRoot = sq.from(Customer.class);
                    sq.where(c.leftMatch(c.get(Customer_.firstName), "firstName2"));
                    sq.select(c.builder().max(subRoot.get(Customer_.age)));
                    return c.equal(c.get(Customer_.age), sq);
                })
                .toPage(PageRequests.of())
                .runWith(em);

        assertThat(page.getContent().get(0).getAge()).isEqualTo(24);

    }

    @Test void testSubquery2() {

        Page<Customer> page = Querying.of(Customer.class)
                .filter(c -> {
                    Subquery<Integer> sq = c.subqueryOf(Customer.class)
                            .filter(firstNameLeftMatchTo("firstName2"))
                            .select(Integer.class, sc -> sc.max(sc.get(Customer_.age)));
                    return c.equal(c.get(Customer_.age), sq);
                })
                .toPage(PageRequests.of())
                .runWith(em);

        assertThat(page.getContent().get(0).getAge()).isEqualTo(24);

    }



    static Specification<Customer> firstNameEqualTo(final String firstName) {
        return c -> c.equal(c.get(Customer_.firstName), firstName);
    }

    static Specification<Customer> lastNameEqualTo(final String lastName) {
        return c -> c.equal(c.get(Customer_.lastName), lastName);
    }

    static Specification<Customer> firstNameLeftMatchTo(final String firstName) {
        return c -> c.leftMatch(c.get(Customer_.firstName), firstName);
    }

    static Selector<Customer, Integer, Path<Integer>> customerAge = c -> c.get(Customer_.age);


    static Selector<Customer, Dto, CompoundSelection<Dto>> toDto =
            c -> c.construct(Dto.class, c.get(Customer_.firstName), c.get(Customer_.lastName));



    public static class Dto {
        String value1;
        String value2;
        public Dto(String value1, String value2) {
            this.value1 = value1;
            this.value2 = value2;
        }
        @Override
        public String toString() {
            return String.format("Dto[value1='%s', value2='%s']", value1, value2);
        }

    }

}