package com.wsmt.middleware.students.service.impl;

import com.wsmt.middleware.students.dao.entity.StudentEntity;
import com.wsmt.middleware.students.dao.repository.HibernateUtil;
import com.wsmt.middleware.students.dao.repository.StudentRepository;
import com.wsmt.middleware.students.dao.entity.GradeEntity;
import com.wsmt.middleware.students.dao.entity.SchoolObjectEntity;
import com.wsmt.middleware.students.dao.entity.StudentSchoolObject;
import com.wsmt.middleware.students.dao.repository.GradeRepository;
import com.wsmt.middleware.students.dao.repository.SchoolObjectRepository;
import com.wsmt.middleware.students.service.helper.GradeTransformerIDL;
import com.wsmt.middleware.students.service.helper.SchoolObjectTransformerIDL;
import com.wsmt.middleware.students.service.helper.StudentTransformerIDL;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.wsmt.middleware.students.service.abstracts.ExecIdlIntePOA;
import com.wsmt.middleware.students.service.abstracts.Grade;
import com.wsmt.middleware.students.service.abstracts.SchoolObject;
import com.wsmt.middleware.students.service.abstracts.Student;

@Slf4j
public class JavaIDLServiceImpl extends ExecIdlIntePOA {
    private GradeRepository getGradeRepository() {
        return new GradeRepository();
    }

    private StudentRepository getStudentRepository() {
        return new StudentRepository();
    }

    private SchoolObjectRepository getSchoolObjectRepository() {
        return new SchoolObjectRepository();
    }

    @Override
    public void addStudent(Student student) {
        StudentEntity s = getStudentRepository().findByID(student.name);
        if (s == null) {
            StudentEntity studentToSave = StudentTransformerIDL.transformStudentFromIDL(student);
            getStudentRepository().save(studentToSave);
        } else {
            log.info("Student with id {} already exists", student.name);
        }
    }

    @Override
    public void updateStudent(Student student) {
        log.info("Trying to update student ...");
        StudentEntity s = getStudentRepository().findByID(student.name);
        if (s != null) {
            StudentTransformerIDL.fillStudentEntityFromIDL(student, s);
            getStudentRepository().update(s);
            log.info("Student updated.");
        } else {
            log.info("Student to update does not exists");
        }
    }

    @Override
    public void deleteStudent(String id) {
        log.info("Trying to delete student with id {}", id);
        StudentEntity s = getStudentRepository().findByID(id);
        if (s != null) {
            GradeEntity[] catalog = getGradeRepository().getCatalogForStudent(id);
            Transaction transaction = null;
            try(Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                for (GradeEntity g : catalog) {
                    session.delete(g);
                }
                session.delete(s);
                transaction.commit();
            } catch (Exception e) {
                log.error(e.getMessage());
                getStudentRepository().rollback(transaction);
            }
        } else {
            log.info("Student to delete not found");
        }
    }

    @Override
    public Student[] getAllStudents() {
        log.info("Trying to find all students");
        StudentEntity[] studentEntities = getStudentRepository().findAll();
        log.info("Students found: {}", studentEntities.length);
        return StudentTransformerIDL.transformStudentEntitiesToIDL(studentEntities);
    }

    @Override
    public void addSchoolObject(SchoolObject schoolObject) {
        SchoolObjectEntity s = getSchoolObjectRepository().findByID(schoolObject.name);
        if (s == null) {
            SchoolObjectEntity schoolObjectToSave = SchoolObjectTransformerIDL.transformSchoolObjectFromIDL(schoolObject);
            getSchoolObjectRepository().save(schoolObjectToSave);
        } else {
            log.info("School object with id {} already exists", schoolObject.name);
        }
    }

    @Override
    public void updateSchoolObject(SchoolObject schoolObject) {
        SchoolObjectEntity s = getSchoolObjectRepository().findByID(schoolObject.name);
        if (s != null) {
            SchoolObjectTransformerIDL.fillSchoolObjectEntityFromIDL(schoolObject, s);
            getSchoolObjectRepository().update(s);
            log.info("School object updated");
        } else {
            log.info("School object with id {} does not exists", schoolObject.name);
        }
    }

    @Override
    public void deleteSchoolObject(String id) {
        SchoolObjectEntity s = getSchoolObjectRepository().findByID(id);
        if (s != null) {
            Transaction transaction = null;
            try(Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                GradeEntity[] catalog = getGradeRepository().getCatalogForSchoolObject(id);
                for (GradeEntity g : catalog) {
                    session.delete(g);
                }
                getSchoolObjectRepository().delete(s);
                transaction.commit();
            } catch (Exception e) {
                log.error(e.getMessage());
                getSchoolObjectRepository().rollback(transaction);
            }
            getSchoolObjectRepository().delete(s);
            log.info("School object deleted");
        } else {
            log.info("School object to delete not found");
        }
    }

    @Override
    public SchoolObject[] getAllSchoolObjects() {
        SchoolObjectEntity[] schoolObjectEntities = getSchoolObjectRepository().findAll();
        log.info("School objects found: {}", schoolObjectEntities.length);
        return SchoolObjectTransformerIDL.transformSchoolObjectEntitiesToIDL(schoolObjectEntities);
    }

    @Override
    public void addGrade(Grade grade) {
        log.info("Trying to add a new grade ...");
        GradeEntity g = findById(grade);
        if (g == null) {
            GradeEntity gradeEntity = GradeTransformerIDL.transformGradeFromIDL(grade);
            StudentSchoolObject id = getIDFromGrade(grade);
            if (id != null) {
                gradeEntity.setId(id);
                getGradeRepository().save(gradeEntity);
            }
        } else {
            log.info("Grade for this school object already exists. Update it!");
        }
    }

    @Override
    public void updateGrade(Grade grade) {
        log.info("Trying to update grade ...");
        StudentSchoolObject id = getIDFromGrade(grade);
        if (id != null) {
            GradeEntity g = getGradeRepository().findByID(id);
            if (g != null) {
                GradeTransformerIDL.fillGradeEntityFromIDL(grade, g);
                getGradeRepository().update(g);
            } else {
                log.info("Grade to update does not exists!");
            }
        }
    }

    @Override
    public Grade[] getCatalogForStudent(String name) {
        GradeEntity[] grades = getGradeRepository().getCatalogForStudent(name);
        return GradeTransformerIDL.transformGradeEntitiesToIDL(grades);
    }

    @Override
    public Grade[] getCatalogForSchoolObject(String schoolObjectName) {
        GradeEntity[] grades = getGradeRepository().getCatalogForSchoolObject(schoolObjectName);
        return GradeTransformerIDL.transformGradeEntitiesToIDL(grades);
    }

    private GradeEntity findById(Grade grade) {
        StudentSchoolObject id = getIDFromGrade(grade);
        if (id == null) {
            return null;
        }
        return getGradeRepository().findByID(id);
    }

    private StudentSchoolObject getIDFromGrade(Grade grade) {
        StudentEntity student = getStudentRepository().findByID(grade.student.name);
        SchoolObjectEntity schoolObject = getSchoolObjectRepository().findByID(grade.schoolObject.name);
        if (student == null || schoolObject == null) {
            return null;
        }
        StudentSchoolObject id = new StudentSchoolObject();
        id.setSchoolObject(schoolObject);
        id.setStudent(student);
        return id;
    }
}
