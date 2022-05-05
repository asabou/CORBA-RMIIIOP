package com.wsmt.middleware.students.dao.repository;

import com.wsmt.middleware.students.dao.entity.StudentEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StudentRepository implements IAbstractRepository<String, StudentEntity> {
    @Override
    public void save(StudentEntity student) {
        log.info("Trying to save a new student {}", student);
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(student);
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            rollback(transaction);
        }
    }

    @Override
    public void update(StudentEntity student) {
        log.info("Trying to update a student ...");
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(student);
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            rollback(transaction);
        }
    }

    @Override
    public void delete(StudentEntity student) {
        log.info("Trying to delete student ...");
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(student);
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            rollback(transaction);
        }
    }

    @Override
    public StudentEntity[] findAll() {
        log.info("Trying to find all students");
        List<StudentEntity> students = new ArrayList<>();
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            students = session.createQuery("select s from StudentEntity s order by s.name", StudentEntity.class)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        StudentEntity[] studentsArray = new StudentEntity[students.size()];
        return students.toArray(studentsArray);
    }

    @Override
    public StudentEntity findByID(String s) {
        log.info("Trying to find student by id {}", s);
        StudentEntity student = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            student = session.get(StudentEntity.class, s);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return student;
    }
}
