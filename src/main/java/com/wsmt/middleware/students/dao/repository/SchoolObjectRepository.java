package com.wsmt.middleware.students.dao.repository;

import com.wsmt.middleware.students.dao.entity.SchoolObjectEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SchoolObjectRepository implements IAbstractRepository<String, SchoolObjectEntity> {
    @Override
    public void save(SchoolObjectEntity schoolObjectEntity) {
        log.info("Trying to save a new SchoolObjectEntity {}", schoolObjectEntity);
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(schoolObjectEntity);
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            rollback(transaction);
        }
    }

    @Override
    public void update(SchoolObjectEntity schoolObjectEntity) {
        log.info("Trying to update new school object ...");
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(schoolObjectEntity);
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            rollback(transaction);
        }
    }

    @Override
    public void delete(SchoolObjectEntity schoolObjectEntity) {
        log.error("Trying to delete school object ...");
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(schoolObjectEntity);
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            rollback(transaction);
        }
    }

    @Override
    public SchoolObjectEntity[] findAll() {
        log.info("Trying to find all school objects ...");
        List<SchoolObjectEntity> schoolObjectEntityList = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            schoolObjectEntityList = session.createQuery("select s from SchoolObjectEntity s order by s.name", SchoolObjectEntity.class).getResultList();
        }
        SchoolObjectEntity[] schoolObjectsArray = new SchoolObjectEntity[schoolObjectEntityList.size()];
        return schoolObjectEntityList.toArray(schoolObjectsArray);
    }

    @Override
    public SchoolObjectEntity findByID(String s) {
        log.info("Trying to find school object by id {}", s);
        SchoolObjectEntity schoolObjectEntity = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            schoolObjectEntity = session.get(SchoolObjectEntity.class, s);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return schoolObjectEntity;
    }
}
