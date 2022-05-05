package com.wsmt.middleware.students.utils;

import com.wsmt.middleware.students.service.abstracts.ExecIdlInte;
import com.wsmt.middleware.students.service.abstracts.Grade;
import com.wsmt.middleware.students.service.abstracts.SchoolObject;
import com.wsmt.middleware.students.service.abstracts.Student;

import java.util.Random;

public class ClientUtilsIDL {
    public static void deletingData(ExecIdlInte proxy) {
        deleteStudent(proxy, "maria");
        deleteSchoolObject(proxy, "romana");
    }

    private static void deleteStudent(ExecIdlInte proxy, final String student) {
        System.out.println("Deleting student " + student);
        proxy.deleteStudent(student);
    }

    private static void deleteSchoolObject(ExecIdlInte proxy, final String schoolObject) {
        System.out.println("Deleting school object " + schoolObject);
        proxy.deleteSchoolObject(schoolObject);
    }

    public static void updatingData(ExecIdlInte proxy) {
        System.out.println("Updating students ...");
        updateStudents(proxy);
        System.out.println("Updating school objects ...");
        updateSchoolObjects(proxy);
        System.out.println("Updating grades ...");
        updateGrades(proxy);
    }

    public static void creatingData(ExecIdlInte proxy) {
        System.out.println("Creating students ...");
        addStudents(proxy);
        System.out.println("Creating school objects ...");
        addSchoolObjects(proxy);
        System.out.println("Creating grades ...");
        addGrades(proxy);
    }

    public static void gettingData(ExecIdlInte proxy) {
        getStudents(proxy);
        System.out.println("Getting school objects ...");
        getSchoolObjects(proxy);
        System.out.println("Getting catalogs ...");
        getCatalogs(proxy);
    }

    private static void addStudents(ExecIdlInte proxy) {
        proxy.addStudent(new Student("alex", "244"));
        proxy.addStudent(new Student("maria", "244"));
    }

    private static void addSchoolObjects(ExecIdlInte proxy) {
        proxy.addSchoolObject(new SchoolObject("mate", "Ionescu"));
        proxy.addSchoolObject(new SchoolObject("romana", "Pantea"));
        proxy.addSchoolObject(new SchoolObject("info", "Gligor"));
        proxy.addSchoolObject(new SchoolObject("fizica", "Galis"));
    }

    private static void addGrades(ExecIdlInte proxy) {
        createGradeForStudentAndSchoolObject(proxy, "alex", "mate", 10);
        createGradeForStudentAndSchoolObject(proxy, "alex", "romana", 7);
        createGradeForStudentAndSchoolObject(proxy, "alex", "info", 10);
        createGradeForStudentAndSchoolObject(proxy, "alex", "fizica", 5);
        createGradeForStudentAndSchoolObject(proxy, "maria", "mate", 9);
        createGradeForStudentAndSchoolObject(proxy, "maria", "fizica", 10);
        createGradeForStudentAndSchoolObject(proxy, "maria", "info", 8);
        createGradeForStudentAndSchoolObject(proxy, "maria", "romana", 8);
    }

    private static void updateGrades(ExecIdlInte proxy) {
        updateGrade(proxy, "alex", "romana");
        updateGrade(proxy, "alex", "fizica");
        updateGrade(proxy, "maria", "romana");
        updateGrade(proxy, "maria", "info");
    }

    private static void updateGrade(ExecIdlInte proxy, final String student, final String schoolObject) {
        System.out.println("Updating grade for student " + student + " and school object: " + schoolObject);
        Grade g = new Grade();
        Student st = new Student(student, "");
        SchoolObject so = new SchoolObject(schoolObject, "");
        g.student = st;
        g.schoolObject = so;
        g.value = new Random().nextInt(11);
        proxy.updateGrade(g);
    }

    private static void updateSchoolObjects(ExecIdlInte proxy) {
        updateSchoolObject(proxy, "romana");
        updateSchoolObject(proxy, "info");
    }

    private static void updateSchoolObject(ExecIdlInte proxy, final String schoolObject) {
        System.out.println("Updating school object " + schoolObject);
        SchoolObject so = new SchoolObject();
        so.name = schoolObject;
        so.teacher = schoolObject + " I.";
        proxy.updateSchoolObject(so);
    }

    private static void updateStudents(ExecIdlInte proxy) {
        updateStudent(proxy, "alex");
        updateStudent(proxy, "maria");
    }

    private static void updateStudent(ExecIdlInte proxy, final String student) {
        System.out.println("Updating student " + student);
        Student st = new Student();
        st.name = student;
        st.group = "245";
        proxy.updateStudent(st);
    }

    private static void createGradeForStudentAndSchoolObject(ExecIdlInte proxy, final String student, final String schoolObject,
                                                             final int gr) {
        Grade grade = new Grade();
        Student st = new Student(student, "");
        SchoolObject so = new SchoolObject(schoolObject, "");
        grade.value = gr;
        grade.student = st;
        grade.schoolObject = so;
        proxy.addGrade(grade);
    }

    private static void getStudents(ExecIdlInte proxy) {
        Student[] students = proxy.getAllStudents();
        for (Student st : students) {
            System.out.println("name: " + st.name + " group: " + st.group);
        }
    }

    private static void getSchoolObjects(ExecIdlInte proxy) {
        SchoolObject[] schoolObjects = proxy.getAllSchoolObjects();
        for (SchoolObject so : schoolObjects) {
            System.out.println("name: " + so.name + " teacher: " + so.teacher);
        }
    }

    private static void getCatalogs(ExecIdlInte proxy) {
        getCatalogsForStudent(proxy, "alex");
        getCatalogsForStudent(proxy, "maria");
        getCatalogsForSchoolObject(proxy, "mate");
        getCatalogsForSchoolObject(proxy, "romana");
        getCatalogsForSchoolObject(proxy, "info");
        getCatalogsForSchoolObject(proxy, "fizica");
    }

    private static void getCatalogsForStudent(ExecIdlInte proxy, final String student) {
        System.out.println("Getting catalog for student " + student);
        Grade[] grades = proxy.getCatalogForStudent(student);
        for (Grade g : grades) {
            System.out.println("student: " + g.student.name + " group: " + g.student.group + " school object: " + g.schoolObject.name + " grade: " + g.value);
        }
    }

    private static void getCatalogsForSchoolObject(ExecIdlInte proxy, final String schoolObject) {
        System.out.println("Getting catalog for school object " + schoolObject);
        Grade[] grades = proxy.getCatalogForSchoolObject(schoolObject);
        for (Grade g : grades) {
            System.out.println("student: " + g.student.name + " school object: " + g.schoolObject.name + " grade: " + g.value);
        }
    }

}
