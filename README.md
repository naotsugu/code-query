# code-query

JPA querying library.



## Usage

Create query and run it.

```java
Page<Customer> page = Querying.of(Customer.class)
        .filter(firstNameEqualTo("Thomas"))
        .toPage(PageRequests.of())
        .runWith(em);
```

we get customer page result.


Specification is

```java
static Specification<Customer> firstNameEqualTo(final String firstName) {
    return c -> c.equal(c.get(Customer_.firstName), firstName);
}
```




## Other example


##### Count

```java
Long count = Querying.of(Customer.class).count().runWith(em);
```


##### Group by

```java
Selector<Customer, Integer, Path<Integer>> customerAge = c -> c.get(Customer_.age);

Page<Integer> page = Querying.of(Customer.class)
        .select(customerAge)
        .groupBy(customerAge)
        .toPage(PageRequests.of())
        .runWith(em);
```


##### Order by

```java
Page<Customer> page = Querying.of(Customer.class)
        .filter(firstNameEqualTo("Thomas"))
        .toPage(PageRequests.of(Sorts.desc("age")))
        .runWith(em);
```


##### Tuple 

```java
Page<Tuple> page = Querying.of(Customer.class)
        .select(Tuple.class, c -> c.tuple(
                c.get(Customer_.firstName).alias("firstName"),
                c.get(Customer_.age).alias("age")))
        .toPage(PageRequests.of())
        .runWith(em);
```


##### Subquery 

```java
Page<Customer> page = Querying.of(Customer.class)
        .filter(c -> {
            Subquery<Integer> sq = c.subqueryOf(Customer.class)
                    .filter(firstNameLeftMatchTo("firstName2"))
                    .select(Integer.class, sc -> sc.max(sc.get(Customer_.age)));
            return c.equal(c.get(Customer_.age), sq);
        })
        .toPage(PageRequests.of())
        .runWith(em);
```


and more..


