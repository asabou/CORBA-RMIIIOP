package com.wsmt.middleware.students.service.impl;

import com.wsmt.middleware.students.dao.entity.GradeEntity;
import com.wsmt.middleware.students.dao.entity.SchoolObjectEntity;
import com.wsmt.middleware.students.dao.entity.StudentEntity;
import com.wsmt.middleware.students.dao.entity.StudentSchoolObject;
import com.wsmt.middleware.students.dao.repository.GradeRepository;
import com.wsmt.middleware.students.dao.repository.HibernateUtil;
import com.wsmt.middleware.students.dao.repository.SchoolObjectRepository;
import com.wsmt.middleware.students.dao.repository.StudentRepository;
import com.wsmt.middleware.students.service.helper.GradeTransformerRMIIIOP;
import com.wsmt.middleware.students.service.helper.SchoolObjectTransformerRMIIIOP;
import com.wsmt.middleware.students.service.helper.StudentTransformerRMIIIOP;
import com.wsmt.middleware.students.service.model.GradeDTO;
import com.wsmt.middleware.students.service.model.SchoolObjectDTO;
import com.wsmt.middleware.students.service.model.StudentDTO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;

@Slf4j
public class JavaRMIIIOPServiceImpl extends PortableRemoteObject implements ExecRmiInte {
    public JavaRMIIIOPServiceImpl() throws RemoteException {
        super();
    }

    private StudentRepository getStudentRepository() {
        return new StudentRepository();
    }

    private SchoolObjectRepository getSchoolObjectRepository() {
        return new SchoolObjectRepository();
    }

    private GradeRepository getGradeRepository() {
        return new GradeRepository();
    }

    @Override
    public void addStudent(StudentDTO student) {
        StudentEntity s = getStudentRepository().findByID(student.getName());
        if (s == null) {
            StudentEntity studentToSave = StudentTransformerRMIIIOP.transformStudentFromDTO(student);
            getStudentRepository().save(studentToSave);
        } else {
            log.info("Student with id {} already exists", student.getName());
        }
    }

    @Override
    public void updateStudent(StudentDTO student) {
        log.info("Trying to update student ...");
        StudentEntity s = getStudentRepository().findByID(student.getName());
        if (s != null) {
            StudentTransformerRMIIIOP.fillStudentEntityFromDTO(student, s);
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
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
    public StudentDTO[] getAllStudents() {
        log.info("Trying to find all students");
        StudentEntity[] studentEntities = getStudentRepository().findAll();
        log.info("Students found: {}", studentEntities.length);
        return StudentTransformerRMIIIOP.transformStudentEntitiesToDTO(studentEntities);

    }

    @Override
    public void addSchoolObject(SchoolObjectDTO schoolObject) {
        SchoolObjectEntity s = getSchoolObjectRepository().findByID(schoolObject.getName());
        if (s == null) {
            SchoolObjectEntity schoolObjectToSave = SchoolObjectTransformerRMIIIOP.transformSchoolObjectFromDTO(schoolObject);
            getSchoolObjectRepository().save(schoolObjectToSave);
        } else {
            log.info("School object with id {} already exists", schoolObject.getName());
        }
    }

    @Override
    public void updateSchoolObject(SchoolObjectDTO schoolObject) {
        SchoolObjectEntity s = getSchoolObjectRepository().findByID(schoolObject.getName());
        if (s != null) {
            SchoolObjectTransformerRMIIIOP.fillSchoolObjectEntityFromDTO(schoolObject, s);
            getSchoolObjectRepository().update(s);
            log.info("School object updated");
        } else {
            log.info("School object with id {} does not exists", schoolObject.getName());
        }
    }

    @Override
    public void deleteSchoolObject(String id) {
        SchoolObjectEntity s = getSchoolObjectRepository().findByID(id);
        if (s != null) {
            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
    public SchoolObjectDTO[] getAllSchoolObjects() {
        SchoolObjectEntity[] schoolObjectEntities = getSchoolObjectRepository().findAll();
        log.info("School objects found: {}", schoolObjectEntities.length);
        return SchoolObjectTransformerRMIIIOP.transformSchoolObjectEntitiesToDTO(schoolObjectEntities);
    }

    @Override
    public void addGrade(GradeDTO grade) {
        log.info("Trying to add a new grade ...");
        GradeEntity g = findById(grade);
        if (g == null) {
            GradeEntity gradeEntity = GradeTransformerRMIIIOP.transformGradeFromDTO(grade);
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
    public void updateGrade(GradeDTO grade) {
        log.info("Trying to update grade ...");
        StudentSchoolObject id = getIDFromGrade(grade);
        if (id != null) {
            GradeEntity g = getGradeRepository().findByID(id);
            if (g != null) {
                GradeTransformerRMIIIOP.fillGradeEntityFromDTO(grade, g);
                getGradeRepository().update(g);
            } else {
                log.info("Grade to update does not exists!");
            }
        }
    }

    @Override
    public GradeDTO[] getCatalogForStudent(String student) {
        GradeEntity[] grades = getGradeRepository().getCatalogForStudent(student);
        return GradeTransformerRMIIIOP.transformGradeEntitiesToDTO(grades);

    }

    @Override
    public GradeDTO[] getCatalogForSchoolObject(String schoolObject) {
        GradeEntity[] grades = getGradeRepository().getCatalogForSchoolObject(schoolObject);
        return GradeTransformerRMIIIOP.transformGradeEntitiesToDTO(grades);

    }

    private GradeEntity findById(GradeDTO grade) {
        StudentSchoolObject id = getIDFromGrade(grade);
        if (id == null) {
            return null;
        }
        return getGradeRepository().findByID(id);
    }

    private StudentSchoolObject getIDFromGrade(GradeDTO grade) {
        StudentEntity student = getStudentRepository().findByID(grade.getStudent().getName());
        SchoolObjectEntity schoolObject = getSchoolObjectRepository().findByID(grade.getSchoolObject().getName());
        if (student == null || schoolObject == null) {
            return null;
        }
        StudentSchoolObject id = new StudentSchoolObject();
        id.setSchoolObject(schoolObject);
        id.setStudent(student);
        return id;
    }
}
