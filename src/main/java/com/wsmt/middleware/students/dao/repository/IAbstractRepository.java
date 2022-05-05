package com.wsmt.middleware.students.dao.repository;

import com.wsmt.middleware.students.dao.entity.IHasID;
import org.hibernate.Session;
import org.hibernate.Transaction;

public interface IAbstractRepository<ID, E extends IHasID<ID>> {
    void save(E e);
    void update(E e);
    void delete(E e);
    E[] findAll();
    E findByID(ID id);

    default void rollback(final Transaction transaction) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
    }
}
