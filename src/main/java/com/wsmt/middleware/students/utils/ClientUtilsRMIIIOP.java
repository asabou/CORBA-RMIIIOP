package com.wsmt.middleware.students.utils;


import com.wsmt.middleware.students.service.impl.ExecRmiInte;
import com.wsmt.middleware.students.service.model.GradeDTO;
import com.wsmt.middleware.students.service.model.SchoolObjectDTO;
import com.wsmt.middleware.students.service.model.StudentDTO;

import java.rmi.RemoteException;
import java.util.Random;

public class ClientUtilsRMIIIOP {
    public static void deletingData(ExecRmiInte proxy) throws RemoteException {
        deleteStudent(proxy, "maria");
        deleteSchoolObject(proxy, "romana");
    }

    private static void deleteStudent(ExecRmiInte proxy, final String student) throws RemoteException {
        System.out.println("Deleting student " + student);
        proxy.deleteStudent(student);
    }

    private static void deleteSchoolObject(ExecRmiInte proxy, final String schoolObject) throws RemoteException {
        System.out.println("Deleting school object " + schoolObject);
        proxy.deleteSchoolObject(schoolObject);
    }

    public static void updatingData(ExecRmiInte proxy) throws RemoteException {
        System.out.println("Updating students ...");
        updateStudents(proxy);
        System.out.println("Updating school objects ...");
        updateSchoolObjects(proxy);
        System.out.println("Updating grades ...");
        updateGrades(proxy);
    }

    public static void creatingData(ExecRmiInte proxy) throws RemoteException {
        System.out.println("Creating students ...");
        addStudents(proxy);
        System.out.println("Creating school objects ...");
        addSchoolObjects(proxy);
        System.out.println("Creating grades ...");
        addGrades(proxy);
    }

    public static void gettingData(ExecRmiInte proxy) throws RemoteException {
        getStudents(proxy);
        System.out.println("Getting school objects ...");
        getSchoolObjects(proxy);
        System.out.println("Getting catalogs ...");
        getCatalogs(proxy);
    }

    private static void addStudents(ExecRmiInte proxy) throws RemoteException {
        proxy.addStudent(new StudentDTO("alex", "244"));
        proxy.addStudent(new StudentDTO("maria", "244"));
    }

    private static void addSchoolObjects(ExecRmiInte proxy) throws RemoteException {
        proxy.addSchoolObject(new SchoolObjectDTO("mate", "Ionescu"));
        proxy.addSchoolObject(new SchoolObjectDTO("romana", "Pantea"));
        proxy.addSchoolObject(new SchoolObjectDTO("info", "Gligor"));
        proxy.addSchoolObject(new SchoolObjectDTO("fizica", "Galis"));
    }

    private static void addGrades(ExecRmiInte proxy) throws RemoteException {
        createGradeForStudentAndSchoolObject(proxy, "alex", "mate", 10);
        createGradeForStudentAndSchoolObject(proxy, "alex", "romana", 7);
        createGradeForStudentAndSchoolObject(proxy, "alex", "info", 10);
        createGradeForStudentAndSchoolObject(proxy, "alex", "fizica", 5);
        createGradeForStudentAndSchoolObject(proxy, "maria", "mate", 9);
        createGradeForStudentAndSchoolObject(proxy, "maria", "fizica", 10);
        createGradeForStudentAndSchoolObject(proxy, "maria", "info", 8);
        createGradeForStudentAndSchoolObject(proxy, "maria", "romana", 8);
    }

    private static void updateGrades(ExecRmiInte proxy) throws RemoteException {
        updateGrade(proxy, "alex", "romana");
        updateGrade(proxy, "alex", "fizica");
        updateGrade(proxy, "maria", "romana");
        updateGrade(proxy, "maria", "info");
    }

    private static void updateGrade(ExecRmiInte proxy, final String student, final String schoolObject) throws RemoteException {
        System.out.println("Updating grade for student " + student + " and school object: " + schoolObject);
        GradeDTO g = new GradeDTO();
        StudentDTO st = new StudentDTO(student, "");
        SchoolObjectDTO so = new SchoolObjectDTO(schoolObject, "");
        g.setStudent(st);
        g.setSchoolObject(so);
        g.setValue(new Random().nextInt(11));
        proxy.updateGrade(g);
    }

    private static void updateSchoolObjects(ExecRmiInte proxy) throws RemoteException {
        updateSchoolObject(proxy, "romana");
        updateSchoolObject(proxy, "info");
    }

    private static void updateSchoolObject(ExecRmiInte proxy, final String schoolObject) throws RemoteException {
        System.out.println("Updating school object " + schoolObject);
        SchoolObjectDTO so = new SchoolObjectDTO();
        so.setName(schoolObject);
        so.setTeacher(schoolObject + " I.");
        proxy.updateSchoolObject(so);
    }

    private static void updateStudents(ExecRmiInte proxy) throws RemoteException {
        updateStudent(proxy, "alex");
        updateStudent(proxy, "maria");
    }

    private static void updateStudent(ExecRmiInte proxy, final String student) throws RemoteException {
        System.out.println("Updating student " + student);
        StudentDTO st = new StudentDTO();
        st.setName(student);
        st.setGroup("245");
        proxy.updateStudent(st);
    }

    private static void createGradeForStudentAndSchoolObject(ExecRmiInte proxy, final String student, final String schoolObject,
                                                             final int gr) throws RemoteException {
        GradeDTO grade = new GradeDTO();
        StudentDTO st = new StudentDTO(student, "");
        SchoolObjectDTO so = new SchoolObjectDTO(schoolObject, "");
        grade.setValue(gr);
        grade.setStudent(st);
        grade.setSchoolObject(so);
        proxy.addGrade(grade);
    }

    private static void getStudents(ExecRmiInte proxy) throws RemoteException {
        StudentDTO[] students = proxy.getAllStudents();
        for (StudentDTO st : students) {
            System.out.println("name: " + st.getName() + " group: " + st.getGroup());
        }
    }

    private static void getSchoolObjects(ExecRmiInte proxy) throws RemoteException {
        SchoolObjectDTO[] schoolObjects = proxy.getAllSchoolObjects();
        for (SchoolObjectDTO so : schoolObjects) {
            System.out.println("name: " + so.getName() + " teacher: " + so.getTeacher());
        }
    }

    private static void getCatalogs(ExecRmiInte proxy) throws RemoteException {
        getCatalogsForStudent(proxy, "alex");
        getCatalogsForStudent(proxy, "maria");
        getCatalogsForSchoolObject(proxy, "mate");
        getCatalogsForSchoolObject(proxy, "romana");
        getCatalogsForSchoolObject(proxy, "info");
        getCatalogsForSchoolObject(proxy, "fizica");
    }

    private static void getCatalogsForStudent(ExecRmiInte proxy, final String student) throws RemoteException {
        System.out.println("Getting catalog for student " + student);
        GradeDTO[] grades = proxy.getCatalogForStudent(student);
        for (GradeDTO g : grades) {
            System.out.println("student: " + g.getStudent().getName() + " group: " + g.getStudent().getGroup() + " school object: " + g.getSchoolObject().getName() +
                    " grade: " + g.getValue());
        }
    }

    private static void getCatalogsForSchoolObject(ExecRmiInte proxy, final String schoolObject) throws RemoteException {
        System.out.println("Getting catalog for school object " + schoolObject);
        GradeDTO[] grades = proxy.getCatalogForSchoolObject(schoolObject);
        for (GradeDTO g : grades) {
            System.out.println("student: " + g.getStudent().getName() + " school object: " + g.getSchoolObject().getName() + " grade: " + g.getValue());
        }
    }
}
