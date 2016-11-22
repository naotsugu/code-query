package code.core.query.impl;

import code.core.query.CriteriaContext;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class CriteriaContextImpl<T, R> implements CriteriaContext<T, R> {

    public final Root<T> root;
    public final CriteriaQuery<R> query;
    public final CriteriaBuilder builder;


    private CriteriaContextImpl(Root<T> root, CriteriaQuery<R> query, CriteriaBuilder builder) {
        this.root = root;
        this.query = query;
        this.builder = builder;
    }


    public static <T, R> CriteriaContext of(Root<T> root, CriteriaQuery<R> query, CriteriaBuilder builder) {
        return new CriteriaContextImpl(root, query, builder);
    }


    @Override
    public Root<T> root() {
        return root;
    }


    @Override
    public CriteriaQuery<R> query() {
        return query;
    }


    @Override
    public CriteriaBuilder builder() {
        return builder;
    }

}
