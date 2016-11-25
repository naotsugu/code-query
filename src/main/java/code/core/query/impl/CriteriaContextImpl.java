package code.core.query.impl;

import code.core.query.CriteriaContext;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class CriteriaContextImpl<T, R> implements CriteriaContext<T, R> {

    public final Root<T> root;
    public final AbstractQuery<R> query;
    public final CriteriaBuilder builder;


    private CriteriaContextImpl(Root<T> root, AbstractQuery<R> query, CriteriaBuilder builder) {
        this.root = root;
        this.query = query;
        this.builder = builder;
    }


    public static <T, R> CriteriaContext<T, R> of(Root<T> root, AbstractQuery<R> query, CriteriaBuilder builder) {
        return new CriteriaContextImpl<>(root, query, builder);
    }


    @Override
    public Root<T> root() {
        return root;
    }


    @Override
    public AbstractQuery<R> query() {
        return query;
    }


    @Override
    public CriteriaBuilder builder() {
        return builder;
    }

}
