package com.wsmt.middleware.students.dao.repository;

import com.wsmt.middleware.students.dao.entity.GradeEntity;
import com.wsmt.middleware.students.dao.entity.StudentSchoolObject;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GradeRepository implements IAbstractRepository<StudentSchoolObject, GradeEntity> {
    @Override
    public void save(GradeEntity gradeEntity) {
        log.info("Trying to save a new Grade {}", gradeEntity);
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(gradeEntity);
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            rollback(transaction);
        }
    }

    @Override
    public void update(GradeEntity gradeEntity) {
        log.info("Trying to update grade ");
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(gradeEntity);
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            rollback(transaction);
        }
    }

    @Override
    public void delete(GradeEntity gradeEntity) {

    }

    @Override
    public GradeEntity[] findAll() {
        return new GradeEntity[0];
    }

    @Override
    public GradeEntity findByID(StudentSchoolObject studentSchoolObject) {
        log.info("Trying to find Grade by id {}", studentSchoolObject);
        GradeEntity gradeEntity = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            gradeEntity = session.get(GradeEntity.class, studentSchoolObject);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return gradeEntity;
    }

    public GradeEntity[] getCatalogForStudent(final String student) {
        log.info("Trying to find catalog for student {}", student);
        List<GradeEntity> grades = new ArrayList<>();
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            grades = session.createQuery("select g from GradeEntity g where " +
                            "g.id.student.name = '" + student + "' order by g.id.schoolObject.name",
                            GradeEntity.class).getResultList();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("Grades found: {}", grades.size());
        GradeEntity[] gradesArray = new GradeEntity[grades.size()];
        return grades.toArray(gradesArray);
    }

    public GradeEntity[] getCatalogForSchoolObject(final String schoolObject) {
        log.info("Trying to get catalog for school object {}", schoolObject);
        List<GradeEntity> grades = new ArrayList<>();
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            grades = session.createQuery("select g from GradeEntity g where " +
                    "g.id.schoolObject.name = '" + schoolObject + "' order by g.id.student.name",
                    GradeEntity.class).getResultList();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("Grades found: {}", grades.size());
        GradeEntity[] gradesArray = new GradeEntity[grades.size()];
        return grades.toArray(gradesArray);
    }
}
